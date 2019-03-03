package hacking.terminal;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * Class implementing a Swing-based text console
 * 
 * Principles: - provides a fixed number of rows and columns, but can be resized
 * - each cell can have its own foreground and background colour - The main font
 * determines the grid size
 * 
 * @author Mike Anderson
 * 
 */
public class JConsole extends JComponent implements HierarchyListener{
	private static final long serialVersionUID = 3571518591759968333L;
	
	private static final int DEFAULT_BLINKRATE = 550;
	private static final boolean DEFAULT_BLINK_ON = true;
	private static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
	private static final Color DEFAULT_BACKGROUND = Color.BLACK;
	
	private ConsoleData data = new ConsoleData();
	
	private boolean cursorVisible = false;
	private boolean cursorBlinkOn = true;
	private boolean cursorInverted = true;
	
	private int cursorX = 0;
	private int cursorY = 0;
	private Color currentForeground = DEFAULT_FOREGROUND;
	private Color currentBackground = DEFAULT_BACKGROUND;
	
	private Timer blinkTimer;
	
	private Image offscreenBuffer;
	private Graphics2D offscreenGraphics;

	private int charWidth = 9;
	private int charHeight = 16;
	private BufferedImage glyphSprite;
    private BufferedImage[] glyphs;
	private boolean invalidScreen = false;
	
	public JConsole(int columns, int rows){
		init(columns, rows);
		if(DEFAULT_BLINK_ON){
			setCursorBlink(true);
		}
	}
		
	/**
	 * Utility class to handle the cursor blink animations
	 */
	private class TimerAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(cursorBlinkOn && isShowing()){
				cursorInverted = !cursorInverted;
				data.setDataChangedAt(cursorX, cursorY);
				repaint();
			}else{
				stopBlinking();
			}
		}
	}
	
	private void stopBlinking(){
		if(blinkTimer != null){
			blinkTimer.stop();
			cursorInverted = true;
		}
	}
	
	private void startBlinking(){
		getTimer().start();
	}
	
	protected void setCursorBlink(boolean blink){
		if(blink){
			cursorBlinkOn = true;
			startBlinking();
		}else{
			cursorBlinkOn = false;
			stopBlinking();
		}
	}
	
	protected void setBlinkDelay(int millis){
		getTimer().setDelay(millis);
	}
	
	private Timer getTimer(){
		if(blinkTimer == null){
			blinkTimer = new Timer(DEFAULT_BLINKRATE, new TimerAction());
			blinkTimer.setRepeats(true);
			if(cursorBlinkOn){
				startBlinking();
			}
		}
		return blinkTimer;
	}
	
	@Override
	public void addNotify(){
		super.addNotify();
		addHierarchyListener(this);
	}
	
	@Override
	public void removeNotify(){
		removeHierarchyListener(this);
		super.removeNotify();
	}
	
	public void setRows(int rows){
		resize(this.data.columns, rows);
	}
	
	public void setCursorVisible(boolean visible){
		cursorVisible = visible;
	}
	
	public int getRows(){
		return data.rows;
	}
	
	public void setColumns(int columns){
		resize(columns, this.data.rows);
	}
	
	public int getColumns(){
		return data.columns;
	}
	
	/**
	 * Initialises the console to a specified size
	 */
	protected void init(int columns, int rows){
		data.init(columns, rows);
		data.background.fillArray(DEFAULT_BACKGROUND);
		data.foreground.fillArray(DEFAULT_FOREGROUND);
		data.text.fillArray(' ');
		data.changed.fillArray(true);
		
		glyphs = new BufferedImage[256];
		loadGlyphs();
		
		//setSize(new Dimension(columns * fontWidth, rows * fontHeight));
		setPreferredSize(new Dimension(columns * charWidth, rows * charHeight));
	}
	
	private void loadGlyphs() {
        try {
            glyphSprite = ImageIO.read(JConsole.class.getResource("cp437.png"));
        } catch (IOException e) {
            System.err.println("loadGlyphs(): " + e.getMessage());
        }

        for (int i = 0; i < 256; i++) {
            int sx = (i % 32) * charWidth + 8;
            int sy = (i / 32) * charHeight + 8;

            glyphs[i] = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
            glyphs[i].getGraphics().drawImage(glyphSprite, 0, 0, charWidth, charHeight, sx, sy, sx + charWidth, sy + charHeight, null);
        }
    }
	
	protected void clear(){
		clearArea(0, 0, data.columns, data.rows);
	}
	
	protected  void resetCursor(){
		cursorX = 0;
		cursorY = 0;
		repaint();
	}
	
	protected void clearScreen(){
		clear();
		setViewStartRow(0);
		resetCursor();
	}
	
	private void clearArea(int column, int row, int width, int height){
		data.fillArea(' ', currentForeground, currentBackground, column, row, width, height);
		repaint();
	}
	
	@Override
	public void paint(Graphics graphics){
		//Graphics2D g = graphics;
		
		if(offscreenBuffer == null){
			offscreenBuffer = createImage(data.columns * charWidth, data.viewableRows * charHeight);
			offscreenGraphics = (Graphics2D)offscreenBuffer.getGraphics();
		}
		
		int curX = getCursorX();
		int curY = getCursorY();
		
		long start = System.currentTimeMillis();
		//Could be optimized to only loop through changed data instead of checking all
		for (int y = data.viewStartRow; y < data.getViewEndRow(); y++) {
			for (int x = 0; x < data.columns; x++) {
            	if(!isScreenInvalid() && !data.isChangedAt(x, y)) continue;
            	
            	Color fg = data.getForegroundAt(x,y);
				Color bg = data.getBackgroundAt(x,y);
				
				// Invert Cursor location
				if((y == curY) && (x == curX)){
					if(cursorVisible && cursorInverted){
						
						Color t = fg;
						fg = bg;
						bg = t;
					}
				}
				
				LookupOp op = setColors(bg, fg);
                BufferedImage img = op.filter(glyphs[data.getCharAt(x, y)], null);
                offscreenGraphics.drawImage(img, x * charWidth, (y-data.viewStartRow) * charHeight, null);
				
				data.dataChangeUpdateAt(x, y-data.viewStartRow);
            }
		}
		long end = System.currentTimeMillis();
		//System.out.println(end-start);
		
		graphics.drawImage(offscreenBuffer, 0, 0, this.getWidth(), this.getHeight(), 0, 0, offscreenBuffer.getWidth(this),
				offscreenBuffer.getHeight(this), this);
		//invalidScreen = false;
	}
	
	private LookupOp setColors(Color bgColor, Color fgColor) {
        byte[] a = new byte[256];
        byte[] r = new byte[256];
        byte[] g = new byte[256];
        byte[] b = new byte[256];

        byte bgr = (byte) (bgColor.getRed());
        byte bgg = (byte) (bgColor.getGreen());
        byte bgb = (byte) (bgColor.getBlue());

        byte fgr = (byte) (fgColor.getRed());
        byte fgg = (byte) (fgColor.getGreen());
        byte fgb = (byte) (fgColor.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = (byte) 255;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = (byte) 255;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        byte[][] table = {r, g, b, a};
        return new LookupOp(new ByteLookupTable(0, table), null);
    }
	
	public void setCursorPos(int column, int row){
		
		data.setDataChangedAt(cursorX, cursorY);
		
		if(!(column < 0) && !(column >= data.columns)) cursorX = column;
		if(!(row < 0)) cursorY = row;
		
		if(row >= data.getViewEndRow()){
			setViewStartRow(getViewStartRow()+1);
			invalidScreen = true;
			repaint();
		}
		
		if(row >= data.rows){
			data.addRow(' ', DEFAULT_FOREGROUND, DEFAULT_BACKGROUND);
			cursorY = data.rows-1;
			invalidScreen = true;
			repaint();
		}		
		
		data.setDataChangedAt(cursorX, cursorY);
		
		repaint();
	}
	
	public int getCursorX(){
		return cursorX;
	}
	
	public int getCursorY(){
		return cursorY;
	}
	
	public void setForeground(Color c){
		currentForeground = c;
	}
	
	public JConsole fg(Color c){
		setForeground(c);
		return this;
	}
	
	public void setBackground(Color c){
		currentBackground = c;
	}
	
	public JConsole bg(Color c){
		setBackground(c);
		return this;
	}
	
	public JConsole rs(){
		setForeground(DEFAULT_FOREGROUND);
		setBackground(DEFAULT_BACKGROUND);
		return this;
	}
	
	public Color getForeground(){
		return currentForeground;
	}
	
	public Color getBackground(){
		return currentBackground;
	}
	
	public char getCharAt(int column, int row){
		return data.getCharAt(column, row);
	}
	
	public Color getForegroundAt(int column, int row){
		return data.getForegroundAt(column, row);
	}
	
	public Color getBackgroundAt(int column, int row){
		return data.getBackgroundAt(column, row);
	}
	
	/**
	 * Redirects System.out to this console by calling System.setOut
	 */
	public void captureStdOut(){
		PrintStream ps = new PrintStream(System.out){
			public void println(String x){
				writeln(x);
			}
		};
		
		System.setOut(ps);
	}
	
	public JConsole write(char c){
		data.setDataAt(cursorX, cursorY, c, currentForeground, currentBackground);
		moveCursor(c);
		return this;
	}
	
	public JConsole insert(char c){
		data.setDataAt(cursorX, cursorY, c, currentForeground, currentBackground);
		return this;
	}
	
	private void moveCursor(char c){
		data.setDataChangedAt(cursorX, cursorY);
		switch(c){
			case '\n':
				setCursorPos(cursorX, cursorY+1);
				cursorX = 0;
				break;
			default:
				cursorX++;
				if(cursorX >= data.columns){
					cursorX = 0;
					setCursorPos(cursorX, cursorY+1);
				}
				break;
		}
		data.setDataChangedAt(cursorX, cursorY);
	}
	
	public JConsole writeln(String line){
		write(line);
		moveCursor('\n');
		return this;
	}
	
	public JConsole write(String string, Color foreGround, Color backGround){
		Color foreTemp = currentForeground;
		Color backTemp = currentBackground;
		setForeground(foreGround);
		setBackground(backGround);
		write(string);
		setForeground(foreTemp);
		setBackground(backTemp);
		return this;
	}
	
	public JConsole write(String string, Color foreGround){
		Color foreTemp = currentForeground;
		setForeground(foreGround);
		write(string);
		setForeground(foreTemp);
		return this;
	}
	
	public JConsole write(String string){
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			if(c == '\n'){
				writeln(" ");
			}else{
				write(c);
			}
		}
		repaint();
		return this;
	}
	
	public void fillArea(char c, Color fg, Color bg, int column, int row, int width, int height){
		data.fillArea(c, fg, bg, column, row, width, height);
		repaint();
	}
	
	@Override
	public void hierarchyChanged(HierarchyEvent e){
		if((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0){
			if(isShowing()){
				startBlinking();
			}else{
				stopBlinking();
			}
		}
	}
	
	public void setViewStartRow(int n){
		n = Math.max(0, Math.min(data.viewLastRow, n));
		data.viewStartRow = n;
	}
	
	public int getViewStartRow(){
		return data.viewStartRow;
	}
	
	public boolean isScreenInvalid(){
		return invalidScreen;
	}

	public void setScreenInvalid(){
		this.invalidScreen = true;
	}
	
}
