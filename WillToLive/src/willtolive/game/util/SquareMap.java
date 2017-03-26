package willtolive.game.util;

import java.util.ArrayList;

public class SquareMap<V> extends ArrayList<V>{
	private static final long serialVersionUID = 4116672760409327675L;
	private V[][] grid;
    private int size;
    @SuppressWarnings("unchecked")
	public SquareMap(int size) {
    	this.size = size;
        grid = (V[][]) new Object[size][size];
    }

    public void set(Integer row, Integer col, V value) {
        grid[row][col] = value;
    }
    public V get(Integer row, Integer col) {
        return grid[row][col];
    }
    public int getSize(){
    	return size;
    }

}