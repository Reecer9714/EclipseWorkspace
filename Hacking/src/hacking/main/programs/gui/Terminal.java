package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import hacking.main.GUIGame;
import hacking.main.ReaperOS;
import hacking.main.files.File;
import hacking.main.files.Program;

public class Terminal extends GUIProgram{
	private GUIGame game;
	private JTextArea cmdArea;
	private JTextField cmdField;
	private Program runningProgram;
	
	private static LinkedList<String> lastCommands = new LinkedList<String>();
	private Set<AWTKeyStroke> set = new HashSet<AWTKeyStroke>();
	private static int commandIndex = 0;
	
	public Terminal(ReaperOS os, ImageIcon icon, int width, int height){
		super(os, "Terminal", icon, width, height);
		this.game = os.getGame();
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		cmdArea = new JTextArea();
		cmdArea.setText("ReaperOS Terminal\nVersion 6.3.2017\n\n");
		showPrompt();
		cmdArea.setMinimumSize(new Dimension(width, (int)(height * 0.9)));
		scrollPane.setViewportView(cmdArea);
//		cmdArea.setFont(new Font("Fixedsys", Font.PLAIN, 12));
		cmdArea.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		cmdArea.setBackground(Color.BLACK);
		cmdArea.setForeground(Color.WHITE);
		cmdArea.addKeyListener(new KeyAdapter(){
			private final String BACK_SPACE_KEY_BINDING = getKeyBinding(cmdArea.getInputMap(), "BACK_SPACE");
			private boolean isKeysDisabled;
			private int minCursorPosition = cmdArea.getText().length();
			
			public void keyPressed(KeyEvent evt){
				int keyCode = evt.getKeyCode();
				if(keyCode == KeyEvent.VK_BACK_SPACE){
					int cursorPosition = cmdArea.getCaretPosition();
					if(cursorPosition == minCursorPosition && !isKeysDisabled){
						disableBackspaceKey();
					}else if(cursorPosition > minCursorPosition && isKeysDisabled){
						enableBackspaceKey();
					}
				}else if(keyCode == KeyEvent.VK_ENTER){
					disable();
					String command = getCommand();
					handleInput(command);
					//messageOut("");
					enable();
				}
			}
			
			public void keyReleased(KeyEvent evt){
				int keyCode = evt.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER){
					String terminalText = cmdArea.getText();
					if(terminalText.endsWith("\n")){
						cmdArea.setText(terminalText.substring(0, terminalText.length() - 1));
					}
					cmdArea.setCaretPosition(cmdArea.getText().length());
					minCursorPosition = cmdArea.getText().length();
				}
			}
			
			private String getKeyBinding(InputMap inputMap, String name){
				return (String)inputMap.get(KeyStroke.getKeyStroke(name));
			}
			
			private void disableBackspaceKey(){
				isKeysDisabled = true;
				cmdArea.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
			}
			
			private void enableBackspaceKey(){
				isKeysDisabled = false;
				cmdArea.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), BACK_SPACE_KEY_BINDING);
			}
		});
		
		String[] keystrokeNames = { "UP", "DOWN", "LEFT", "RIGHT", "HOME" };
		for(int i = 0; i < keystrokeNames.length; ++i)
			cmdArea.getInputMap().put(KeyStroke.getKeyStroke(keystrokeNames[i]), "none");
		
		cmdField = new JTextField();
		cmdArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
		cmdArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
		cmdField.setMinimumSize(new Dimension(width, (int)(height * 0.1)));
		cmdField.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		cmdField.setBackground(Color.BLACK);
		cmdField.setForeground(Color.WHITE);
		
		setupInput();
		// pack();
	}
	
	@Override
	public void open(){
		super.open();
		enable();
		cmdArea.setText("ReaperOS Terminal\nVersion 6.3.2017\n\n");
		showPrompt();
	}
	
	@Override
	public void close(){
		super.close();
		game.setConnectedComp(game.getMyComputer());
		cmdField.setText("");
		cmdArea.setText("");
	}
	
	public void enable(){
		cmdArea.setEditable(true);
	}
	
	public void disable(){
		cmdArea.setEditable(false);
	}
	
	private String getCommand(){
		String terminalText = cmdArea.getText();
		if(terminalText.endsWith("\n")) terminalText = terminalText.substring(0, terminalText.length() - 1);
		int lastPromptIndex = terminalText.lastIndexOf('>') + 1;
		if(lastPromptIndex < 0 || lastPromptIndex >= terminalText.length())
			return "";
		else return terminalText.substring(lastPromptIndex);
	}
	
	public String getPrompt(){
		String s;
		if(game.getConnectedComp().getIp().equals(game.getMyComputer().getIp())){
			s = (game.getMyComputer().getDir().getPath() + ">");
		}else{
			if(game.getConnectedComp().isAccess()){
				s = ("[" + game.getConnectedComp().getIp() + "]:" + game.getConnectedComp().getDir().getPath() + ">");
			}else{
				s = ("#[" + game.getConnectedComp().getIp() + "]#:" + game.getConnectedComp().getDir().getPath() + ">");
			}
		}
		return s;
	}
	
	public void showPrompt(){
		cmdArea.append(getPrompt());
	}
	
	public void handleInput(String s){
		addLastCommand(s);
		String[] command = s.split(" ");
		command[0] = command[0].toLowerCase();
		
		// if runningProgram send input to it
		messageOut("");
		switch(command[0]){
			case "":
				break;
			case "exit":
				this.close();
				break;
			case "cd":
				changeDir(command);
				break;
			case "mkdir":
				//TODO: change just like changeDir
				game.getConnectedComp().getDir().addFolder(command);
				break;
			case "ls":
				messageOut(game.getConnectedComp().getDir().getContents());
				break;
			/*
			 * bounce (ip) linked list run (program) new (txt)
			 */
			case "open":
				open(command);
				break;
			case "run":
				run(command);
			case "connect":
				connect(command);
				break;
			case "disconnect":
				disconnect();
				break;
			case "info":
				messageOut(game.getConnectedComp().toString());
				break;
			case "help":
				helpMessage();
				break;
			case "login":
				login(command);
				break;
			default:
				messageOut("  Command not recognized use [help] for a list of commands");
		}
		showPrompt();
	}
	
	public boolean accessCheck(){
		if(!game.getConnectedComp().isAccess()){
			messageOut("   Access Denied: Please Login");
			return false;
		}
		return true;
	}
	
	public void messageOut(String s){
		// System.out.println(s);
		cmdArea.append(s + "\n");
	}
	
	public void helpMessage(){
		messageOut(" Commands:\n" + "    exit - exits the game\n" + "    info - displays computer info\n"
				+ "    cd [folder]- changes current directory to folder\n"
				+ "    dir [folder]- creates a new folder in current directory\n"
				+ "    ls - list all folders and files in current directory\n" + "    help - displays commands\n"
				+ "    open [file] - opens the given file\n" + "    connect [ip] - try connecting to ip\n"
				+ "    disconnect - disconnect from connected computer");
	}
	
	public void open(String[] line){
		if(!accessCheck()) return;
		if(line.length >= 2){
			if(game.getConnectedComp().getDir().hasFile(line[1])){
				game.getConnectedComp().getDir().getFile(line[1]).open();
			}else{
				messageOut("   Could not find file " + line[1]);
			}
		}else{
			messageOut("  Usage: open [file]");
		}
	}
	
	public void run(String[] line){
		if(line.length >= 2){
			if(game.getConnectedComp().getDir().hasFile(line[1])){
				File f = game.getConnectedComp().getDir().getFile(line[1]);
				if(f instanceof Program && ((Program)f).getExe() != null){
					
				}else{
					messageOut("this file is not runnable");
				}
				// messageOut(connectedComp.getDir().getFile(line[1]).toString());
			}else{
				messageOut("   Could not find file " + line[1]);
			}
		}else{
			messageOut("  Usage: open (file)");
		}
	}
	
	public boolean connect(String ip){
		if(game.getComps().containsKey(ip)){
			game.setConnectedComp(game.getComps().get(ip));
			game.getMyComputer().writeToLog("Connected to computer on " + ip);
			game.getConnectedComp().writeToLog(game.getMyComputer().getIp() + " connected to localhost");
			game.getConnectedComp().onConnect(this);
			return true;
		}else{
			return false;
		}
	}
	
	private void connect(String[] line){
		if(line.length >= 2){
			if(connect(line[1])){
				messageOut("   Connected to computer on " + line[1]);
			}else{
				messageOut("   Could not find computer on " + line[1]);
			}
		}else{
			messageOut("  Usage: connect (ip)");
		}
	}
	
	public boolean login(String pw){
		if(game.getConnectedComp().login(pw)){
			return true;
		}else{
			return false;
		}
	}
	
	private void login(String[] line){
		if(line.length >= 2){
			if(login(line[1])){
				messageOut("   Succesfully Logged In");
				messageOut("You will need to use the login command to have full remote access");
			}else{
				messageOut("   Incorrect Password please try again");
			}
		}else{
			messageOut("  Usage: login (password)");
		}
	}
	
	public void disconnect(){
		game.getConnectedComp().setAccess(false);
		game.setConnectedComp(game.getMyComputer());
		messageOut("  Disconnected");
	}
	
	private void changeDir(String[] command){
		if(command.length > 1){
			if(!game.getConnectedComp().changeDir(command[1])){
				messageOut("   " + command[1] + " could not be found");
			}
		}else{
			game.getConnectedComp().changeDir(" ");
		}
	}
	
	private void addLastCommand(String s){
		lastCommands.addFirst(s);
		if(lastCommands.size() > 20){
			lastCommands.removeLast();
		}
	}
	
	public String getLastCommand(){
		return lastCommands.peek();
	}
	
	// Change to cmdArea
	private void setupInput(){
		InputMap im = cmdArea.getInputMap(JTextField.WHEN_FOCUSED);
		
		// Buttons
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "previousCommand");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "nextCommand");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "cycleFile");
		
		// Action
		cmdArea.getActionMap().put("previousCommand", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(lastCommands.size() > 0){
					commandIndex = (commandIndex + 1) % lastCommands.size();
					cmdArea.setText(cmdArea.getText().substring(0, cmdArea.getText().lastIndexOf(">") + 1));
					cmdArea.append(lastCommands.get(commandIndex));
				}
			}
		});
		
		cmdArea.getActionMap().put("nextCommand", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(lastCommands.size() > 0){
					commandIndex = (commandIndex + lastCommands.size() - 1) % lastCommands.size();
					cmdArea.setText(cmdArea.getText().substring(0, cmdArea.getText().lastIndexOf(">") + 1));
					cmdArea.append(lastCommands.get(commandIndex));
				}
			}
		});
		
		// Override Tab
		cmdArea.getActionMap().put("cycleFile", new AbstractAction(){
			private int fileIndex = 0;
			
			@Override
			public void actionPerformed(ActionEvent e){
				if(game.getConnectedComp().getCurrentDir().getChildren().size() == 0) return;
				ArrayList<File> children = game.getConnectedComp().getCurrentDir().getChildren();
				
				fileIndex = (fileIndex + 1) % children.size();
				int lastIndex = (fileIndex + children.size() - 1) % children.size();
				String fileName = children.get(fileIndex).getName();
				String lastName = children.get(lastIndex).getName();
				String line = cmdArea.getText().substring(cmdArea.getText().lastIndexOf(">") + 1, cmdArea.getText().length());
				cmdArea.setText(cmdArea.getText().substring(0, cmdArea.getText().lastIndexOf(">") + 1));
				line = replaceLast(line, lastName, "");
				line += fileName;
				cmdArea.append(line);
			}
			
			public String replaceLast(String text, String regex, String replacement) {
		        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
		    }
		});
	}
	
}
