package hacking.terminal;

import java.awt.Color;
import java.util.Arrays;

import hacking.util.RotatingArray;
import hacking.util.RotatingArray2D;

/**
 * Class used for storing console data
 * 
 * @author Mike
 */
public final class ConsoleData {
	public int rows;
	public int viewStartRow;
	public int viewLastRow;
	public int viewableRows;
	public int columns;
	public RotatingArray2D<Color> background;
	public RotatingArray2D<Color> foreground;
	public RotatingArray2D<Character> text;
	public RotatingArray2D<Boolean> changed;

	ConsoleData() {
		// create empty console data
	}

	private void ensureCapacity(int width, int height) {
		if (columns >= width && rows >= height)
			return;
		
		int size = height * width;
		
//		char[][] newText = new char[height][width];
//		Color[][] newBackground = new Color[height][width];
//		Color[][] newForeground = new Color[height][width];
//		
//		
//		if (size > 0) {
//			System.arraycopy(text, 0, newText, 0, size);
//			System.arraycopy(foreground, 0, newForeground, 0, size);
//			System.arraycopy(background, 0, newBackground, 0, size);
//		}
//		
//		boolean[][] newChanged = new boolean[height][width];
//		Arrays.fill(newChanged, true);

		text = new RotatingArray2D<Character>(width, height);
		foreground = new RotatingArray2D<Color>(width, height);
		background = new RotatingArray2D<Color>(width, height);
		changed = new RotatingArray2D<Boolean>(width, height);
	}

	void init(int columns, int rows) {
		ensureCapacity(columns, rows*2);
		this.rows = rows*2;
		this.viewableRows = rows;
		this.columns = columns;
		viewStartRow = 0;
		viewLastRow = this.rows - this.rows/2;
	}

	/**
	 * Sets a single character position
	 */
	public void setDataAt(int column, int row, char c, Color fg, Color bg) {
		text.set(c, column, row);
		foreground.set(fg, column, row);
		background.set(bg, column, row);
		changed.set(true, column, row);
	}

	public char getCharAt(int column, int row) {
		return text.get(column, row);
	}

	public Color getForegroundAt(int column, int row) {
		return foreground.get(column, row);
	}

	public Color getBackgroundAt(int column, int row) {
		return background.get(column, row);
	}
	
	public boolean isChangedAt(int column, int row){
		return changed.get(column, row);
	}
	
	public void setDataChangedAt(int column, int row){
		changed.set(true, column, row);
	}
	
	public void dataChangeUpdateAt(int column, int row){
		changed.set(false, column, row);
	}
	
	public int getViewEndRow(){
		return viewStartRow+viewableRows;
	}
	
	@Deprecated
	public int getOffset(int column, int row){
		return column + row * columns;
	}

	public void fillArea(char c, Color fg, Color bg, int column,
			int row, int width, int height) {
		for (int q = Math.max(0, row); q < Math.min(row + height, rows); q++) {
			for (int p = Math.max(0, column); p < Math.min(column + width,
					columns); p++) {
				setDataAt(p, q, c, fg, bg);
			}
		}
	}

	public void addRow(Character c, Color fg, Color bg){
		text.addRow(' ');
		changed.addRow(true);
		foreground.addRow(fg);
		background.addRow(bg);
	}
}