package hacking.util;

import java.util.Iterator;

public class RotatingArray<T> implements Iterable<T>{
	private T[] array;
	private int startOffset;
	private int size;
	private int count;
	
	@SuppressWarnings("unchecked")
	public RotatingArray(int size){
		this.size = size;
		array = (T[]) new Object[size];
		startOffset = 0;
		count = 0;
	}
	
	public void rotate(int i){
		startOffset = (startOffset + 1) % size;
	}
	
	public T get(int i){
		return array[(i + startOffset) % size];
	}
	
	public void add(T t){
		array[(count + startOffset) % size] = t;
		count++;
		if(count > size) rotate(1);
	}
	
	public int size(){
		return size;
	}

	@Override
	public Iterator<T> iterator(){
		return new RotatingIterator<T>();
	}
	
	private class RotatingIterator<H> implements Iterator<T>{
		private int position = startOffset;
		
		@Override
		public boolean hasNext(){
			if(position < (array.length) + startOffset){
				return true;
			}
			return false;
		}

		@Override
		public T next(){
			if(this.hasNext()){
				T t = array[(position + array.length) % array.length];
				position++;
				return t;
			}
			return null;
		}
	}
	
	public static void main(String[] args){
		RotatingArray<Integer> grid = new RotatingArray<Integer>(5);
		grid.add(1);
		grid.add(2);
		grid.add(3);
		grid.add(4);
		grid.add(5);
		grid.add(6);
		
//		grid.add(2, 1);
//		grid.add(3, 2);
//		grid.add(4, 3);
//		grid.add(5, 4);
		
		
		for(Integer i: grid){
			System.out.println(i);
		}
	}

}
