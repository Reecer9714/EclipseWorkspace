package hacking.util;

import java.util.Arrays;
import java.util.Iterator;

public class RotatingArray2D<T> implements Iterable<T>{
	private T[] array;
	private int startYOffset;
	private int width;
	private int height;
	private int count;
	
	@SuppressWarnings("unchecked")
	public RotatingArray2D(int width, int height){
		this.width = width;
		this.height = height;
		array = (T[]) new Object[width * height];
		startYOffset = 0;
		count = 0;
	}
	
	private int getOffset(int column, int row){
		return column + row * width;
	}
	
	public void rotate(int i){
		startYOffset = (startYOffset + i) % height;
	}
	
	public T get(int column, int row){
		return array[getOffset(column, (row + startYOffset) % height)];
	}
	
	public void add(T[] row){
		for(int x = 0; x < row.length; x++){
			array[getOffset(x, (startYOffset) % height)] = row[x];
		}		
		rotate(1);
	}
	
	@SuppressWarnings("unchecked")
	public void addRow(T init){
		T[] toAdd = (T[]) new Object[width];
		Arrays.fill(toAdd, init);
		add(toAdd);
	}
	
	public void set(T t, int column, int row){
		array[getOffset(column, (row + startYOffset) % height)] = t;
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	public int getStartY(){
		return startYOffset;
	}
	
	public int getEndY(){
		return startYOffset + height;
	}
	
	public void fillArray(T t){
		Arrays.fill(array, t);
	}
	
	public void copyArray(T[] t){
		System.arraycopy(array, 0, t, 0, t.length);
	}

	@Override
	public Iterator<T> iterator(){
		return new RotatingIterator<T>();
	}
	
	private class RotatingIterator<H> implements Iterator<T>{
		private int positionY = startYOffset;
		private int positionX = 0;
		
		@Override
		public boolean hasNext(){
			if(positionY < (array.length) + startYOffset){
				return true;
			}
			return false;
		}

		@Override
		public T next(){
			if(this.hasNext()){
				T t = array[getOffset(positionX, (positionY + array.length) % array.length)];
				positionX++;
				if(positionX >= width){
					positionX = 0;
					positionY++;
				}
				return t;
			}
			return null;
		}
	}
	
	public static void main(String[] args){
		RotatingArray2D<Integer> grid = new RotatingArray2D<Integer>(5,5);
		grid.fillArray(0);
		grid.set(1, 0, 0);
		grid.set(2, 1, 1);
		grid.set(3, 2, 2);
		grid.set(4, 3, 3);
		grid.set(5, 4, 4);
		//grid.set(6, 0, 0);
//		grid.addRow(0);
//		grid.addRow(0);
//		grid.addRow(0);
		
		for(int y = 0; y < grid.height(); y++){
			for(int x = 0; x < grid.width(); x++){
				System.out.print(grid.get(x, y));
			}
			System.out.println();
		}
	}

}
