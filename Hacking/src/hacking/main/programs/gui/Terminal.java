package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import hacking.main.GUIGame;
import hacking.main.ReaperOS;
import hacking.main.files.File;
import hacking.main.files.Program;
import hacking.main.internet.IP;

public class Terminal extends GUIProgram{
	private GUIGame game;
	private JTextArea cmdArea;
	private JTextField cmdField;
	private Program runningProgram;
	
	private static LinkedList<String> lastCommands = new LinkedList<String>();
	private Set<AWTKeyStroke> set = new HashSet<AWTKeyStroke>();
	private static int commandIndex = 0;
	private int minCursor;
	
	public Terminal(ReaperOS os, ImageIcon icon, int width, int height){
		super(os, "Terminal", icon, width, height);
		this.game = os.getGame();
		JScrollPane scrollPane = new JScrollPane();
		getFrame().getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		cmdArea = new JTextArea();
		cmdArea.setText("ReaperOS Terminal\nVersion 6.3.2017\n\n");
		showPrompt();
		updateMinCursor();
		cmdArea.setMinimumSize(new Dimension(width, (int)(height * 0.9)));
		scrollPane.setViewportView(cmdArea);
//		cmdArea.setFont(new Font("Fixedsys", Font.PLAIN, 12));
		cmdArea.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		cmdArea.setBackground(Color.BLACK);
		cmdArea.setForeground(Color.LIGHT_GRAY);
		cmdArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"none");
		cmdArea.addKeyListener(new KeyAdapter(){
			private final String BACK_SPACE_KEY_BINDING = getKeyBinding(
					cmdArea.getInputMap(), "BACK_SPACE");
			
			public void keyPressed(KeyEvent evt) {
	            int keyCode = evt.getKeyCode();
	            if (keyCode == KeyEvent.VK_BACK_SPACE) {
	            	int cursorPosition = cmdArea.getCaretPosition();
	                if (cursorPosition <= minCursor) {
	                    disableBackspaceKey();
	                } else {
	                    enableBackspaceKey();
	                }
	            }else if(keyCode == KeyEvent.VK_ENTER){
	            	disable();
	            	String command = extractCommand();
	            	System.out.println("Command: " + command);
	            	handleInput(command);
	            	messageOut("");
	            	showPrompt();
	            	updateMinCursor();
	            	enable();
	            }
	        }
			
			private String getKeyBinding(InputMap inputMap, String name) {
	            return (String) inputMap.get(KeyStroke.getKeyStroke(name));
	        }
			
			private void disableBackspaceKey() {
				cmdArea.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"),
	                    "none");
	        }

	        private void enableBackspaceKey() {
	        	cmdArea.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"),
	            		BACK_SPACE_KEY_BINDING);
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
		updateMinCursor();
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
	
	private void updateMinCursor(){
		this.minCursor = cmdArea.getCaretPosition();
	}
	
	private String extractCommand(){
		String terminalText = cmdArea.getText();
		int index = terminalText.lastIndexOf(getPrompt())+getPrompt().length();
		if (index < 0 || index >= terminalText.length())
            return "";
        else
            return terminalText.substring(index);
	}
	
	public String getPrompt(){
		String s;
		String path = game.getMyComputer().getMainDrive().getDir().getPath();
		if(game.getConnectedComp().getPublicIp().equals(game.getMyComputer().getPublicIp())){
			s = (path + ">");
		}else{
			if(game.getConnectedComp().isAccess()){
				s = ("[" + game.getConnectedComp().getPublicIp() + "]:" + path + ">");
			}else{
				s = ("#[" + game.getConnectedComp().getPublicIp() + "]#:" + path + ">");
			}
		}
		return s;
	}
	
	public void showPrompt(){
		cmdArea.append(getPrompt());
	}
	
	public void clearScreen(){
		cmdArea.setText("");
		updateMinCursor();
	}
	
	public void handleInput(String s){
		addLastCommand(s);
		String[] command = s.split(" ");
		command[0] = command[0].toLowerCase();
		
		// if runningProgram send input to it
		if(command[0].equals("")) return;
		messageOut("");
		switch(command[0]){
			case "exit":
				this.close();
				break;
			case "cd":
				changeDir(command);
				break;
			case "mkdir":
				//TODO: change just like changeDir
				game.getConnectedComp().getMainDrive().getDir().addFolder(command);
				break;
			case "ls":
				messageOut(game.getConnectedComp().getMainDrive().getDir().getContents());
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
			case "cls":
			case "clear":
				clearScreen();
				break;
			default:
				messageOut("  Command not recognized use [help] for a list of commands");
		}
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
			if(game.getConnectedComp().getMainDrive().getDir().hasFile(line[1])){
				game.getConnectedComp().getMainDrive().getDir().getFile(line[1]).open();
			}else{
				messageOut("   Could not find file " + line[1]);
			}
		}else{
			messageOut("  Usage: open [file]");
		}
	}
	
	public void run(String[] line){
		if(line.length >= 2){
			if(game.getConnectedComp().getMainDrive().getDir().hasFile(line[1])){
				File f = game.getConnectedComp().getMainDrive().getDir().getFile(line[1]);
				if(f instanceof Program){
					((Program)f).run();
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
	
	public boolean connect(IP ip){
		if(game.getInternet().containsIP(ip)){
			game.setConnectedComp(game.getInternet().getComputer(ip));
			game.getMyComputer().writeToLog("Connected to computer on " + ip);
			game.getConnectedComp().writeToLog(game.getMyComputer().getPublicIp() + " connected to localhost");
			game.getConnectedComp().onConnect(this);
			return true;
		}else{
			return false;
		}
	}
	
	private void connect(String[] line){
		if(line.length >= 2){
			if(connect(new IP(line[1]))){
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
				//should be in computer on connect: messageOut("You will need to use the login command to have full remote access");
			}else{
				messageOut("   Incorrect Password please try again");
			}
		}else{
			messageOut("  Usage: login (password)");
		}
	}
	
	public void disconnect(){
		if(game.getConnectedComp().equals(game.getMyComputer())){
			messageOut("  Not connected to external computer");
			return;
		}
		game.getConnectedComp().setAccess(false);
		game.setConnectedComp(game.getMyComputer());
		messageOut("  Disconnected");
	}
	
	private void changeDir(String[] command){
		if(command.length > 1){
			if(!game.getConnectedComp().getMainDrive().changeDir(command[1])){
				messageOut("   " + command[1] + " could not be found");
			}
		}else{
			game.getConnectedComp().getMainDrive().changeDir(" ");
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
				if(game.getConnectedComp().getMainDrive().getCurrentDir().getChildren().size() == 0) return;
				ArrayList<File> children = game.getConnectedComp().getMainDrive().getCurrentDir().getChildren();
				
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
