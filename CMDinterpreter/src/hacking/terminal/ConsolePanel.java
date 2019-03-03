package hacking.terminal;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

import hacking.Game;
import hacking.files.Folder;
import hacking.files.programs.Program;
import hacking.util.RotatingArray;

public class ConsolePanel extends JConsole{
	
	private static final long serialVersionUID = 1723891506715150947L;
	private Game game;
	private ConsoleControl cc;
	private Folder currentDir;
	private Program currentProgram;

	private int minCursorPos;
	private int maxCursorPos;
	public static String TAB = "   ";
	public static Color PATH_COLOR = new Color(244,66,66);
	public static Color FOLDER_COLOR = new Color(66,134,244);
	public static Color PROGRAM_COLOR = new Color(171,244,139);
	
	public RotatingArray<String> commandBuffer;
	public int bufferSize = 50;
	
	private StringStream inputStream;
	
	public ConsolePanel(Game game, int c, int r){
		super(c, r);
		setCursorVisible(true);
		this.game = game;
		cc = new ConsoleControl(game, this);
		commandBuffer = new RotatingArray<String>(bufferSize);
		inputStream = new StringStream();
		currentDir = game.getFileSystem().getHome();
		minCursorPos = 0;
		maxCursorPos = 0;
		setupKeybindings();
		//addMouseWheelListener(new ScrollInput());	
	}
	
	//TODO: Move to ConsoleControl or other class and change to a capture and send system.
	private void setupKeybindings(){
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inMap = getInputMap(condition);
		ActionMap actMap = getActionMap();
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
		actMap.put("Left", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				cc.getInput().onLeft();
			}
		});
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
		actMap.put("Right", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				cc.getInput().onRight();
			}
		});
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
		actMap.put("Down", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("Down");
				cc.getInput().onDown();
			}
		});
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
		actMap.put("Up", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("UP");
				cc.getInput().onUp();
			}
		});
		
		//Cycle files
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Tab");
		actMap.put("Tab", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				//Nothing Yet
				System.out.println("TAB");
				cc.getInput().onTab();
			}
		});
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		actMap.put("Enter", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				cc.getInput().onEnter();
			}
		});
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Backspace");
		actMap.put("Backspace", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				cc.getInput().onBack();
			}
		});
		
		for(char c = 'A'; c <= 'Z'; c++){
			// upper case
			KeyStroke capital = KeyStroke.getKeyStroke("typed " + c);
			inMap.put(capital, Character.toString(c));
			actMap.put(Character.toString(c), new LetterAction(c));
			
			// lower case
			KeyStroke little = KeyStroke.getKeyStroke("typed " + Character.toLowerCase(c));
			inMap.put(little, Character.toString(Character.toLowerCase(c)));
			actMap.put(Character.toString(Character.toLowerCase(c)), new LetterAction(Character.toLowerCase(c)));
		}
		
		int[][] characters = new int[][]{ { '?', '%', '~', '|' }, { ' ', ',', '-', '.', '/' },
				{ ';', '=', '[', '\\', ']' }, { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' },
				{ '*', '+', ',', '-', '.', '/', '&', '*', '\"', '<', '>', '{', '}', '`', '\'' },
				{ '@', ':', '^', '$', '!', '(', '#', '+', ')', '_' } };
		
		for(int[] range : characters){
			for(int i = 0; i < range.length; i++){
				char charForKey = (char)range[i];
				KeyStroke keyboardKey = KeyStroke.getKeyStroke(charForKey);
				inMap.put(keyboardKey, charForKey);
				actMap.put(charForKey, new LetterAction(charForKey));
			}
		}
	}
	
	private class LetterAction extends AbstractAction{
		
		private static final long serialVersionUID = 1L;
		private char theLetter;
		
		public LetterAction(char letter){
			theLetter = letter;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			cc.getInput().onKey(theLetter);
		}
	}
	
	private class ScrollInput implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e){
			int notches = e.getWheelRotation();
			setViewStartRow(getViewStartRow()+notches);
			setScreenInvalid();
			repaint();
		}	
	}
	
	// Start up
	public void BootUp(){
		showPrompt();
	}
	
	int getCursorIndex(){
		return getCursorX() + getCursorY() * getColumns();
	}
	
	public void showPrompt(){
		String prompt = getPrompt();
		fg(PATH_COLOR).write(prompt).rs();
		
		JTabbedPane pane = game.getFrame().getTabbedPane();
		pane.setTitleAt(pane.getSelectedIndex(), prompt);
		
		minCursorPos = getCursorIndex();
		System.out.println(minCursorPos);
	}
	
	private String getPrompt(){
		String s;
		String path = currentDir.getPath();
		
		s = (path + ">");
		
		return s;
	}
	
	public void newLine(){
		moveCursor(0, 1);
		setCursorX(0);
		minCursorPos = 0;
		maxCursorPos = 0;
	}
	
	@Deprecated
	public String getInputString(){
		int minX = minCursorPos % getColumns();
		int Y = getCursorY();
		String in = "";
		for(int x = minCursorPos; x < maxCursorPos; x++){
			in += getCharAt(x, Y);
		}
		return in;
	}
	
	public StringStream getInputStream(){
		return inputStream;
	}
	
	public void clearScreen(){
		super.clearScreen();
	}
	
	private void loading(){
		writeln("Loading...");
		for(int i = 0; i <= 100; i++){
			// Thread.sleep(100);
			setCursorX(0);
			write(i + "%");
			moveCursor(3, 0);
		}
	}
	
	private void loadingBar(){
		writeln("Loading...");
		for(int i = 0; i <= 100; i++){
			try{
				int width = (i + 1) / 4;
				Thread.sleep(100);
				setCursorX(0);
				write("[");
				moveCursor(26, 0);
				write("]");
				setCursorX(0);
				moveCursor(width + 1, 0);
				write("#");
				setCursorX(0);
				moveCursor(28, 0);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void setCursorPosition(int x, int y){
		setCursorPos(x, y);
	}
	
	public void moveCursor(int dx, int dy){
		setCursorPos(getCursorX() + dx, getCursorY() + dy);
		if(getCursorIndex() > maxCursorPos) maxCursorPos = getCursorIndex()+1;
	}
	
	public void setCursorX(int x){
		setCursorPos(x, getCursorY());
	}
	
	public void setCursorY(int y){
		setCursorPos(getCursorX(), y);
	}
	
	public JConsole writeTab(String string, int tabs){
		while(tabs > 0){
			string = TAB + string;
			tabs--;
		}
		return write(string);
	}
	
	public Game getGame(){
		return game;
	}

	public ConsoleControl getControl(){
		return cc;
	}

	public Folder getCurrentDir(){
		return currentDir;
	}

	public void setCurrentDir(Folder currentDir){
		this.currentDir = currentDir;
	}

	public Program getCurrentProgram(){
		return currentProgram;
	}

	public void setCurrentProgram(Program currentProgram){
		this.currentProgram = currentProgram;
		if(currentProgram == null) showPrompt();
	}

	public RotatingArray<String> getCommandBuffer(){
		return commandBuffer;
	}

	public int getMinCursorPos(){
		return minCursorPos;
	}
	
	public int getMaxCursorPos(){
		return maxCursorPos;
	}

	void setMaxCursorPos(int i){
		maxCursorPos = i;
	}
}
