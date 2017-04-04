package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import hacking.main.GUIGame;
import hacking.main.ReaperOS;

public class Terminal extends GUIProgram{
    private GUIGame game;
    private JTextArea cmdArea;
    private JTextField cmdField;

    private static LinkedList<String> lastCommands = new LinkedList<String>();
    private Set<AWTKeyStroke> set = new HashSet<AWTKeyStroke>();
    private static int commandIndex = 0;
    private static int fileIndex = 0;

    public Terminal(ReaperOS os, int width, int height){
	super(os, "Terminal", width, height);
	this.game = os.getGame();
	JScrollPane scrollPane = new JScrollPane();
	getContentPane().add(scrollPane, BorderLayout.CENTER);

	cmdArea = new JTextArea();
	cmdArea.setMinimumSize(new Dimension(width, (int)(height * 0.9)));
	// cmdArea.setMargin(new Insets(3, 3, 3, 3));
	scrollPane.setViewportView(cmdArea);
	cmdArea.setFont(new Font("Lucida Console", Font.PLAIN, 12));
	cmdArea.setEditable(false);
	cmdArea.setBackground(Color.BLACK);
	cmdArea.setForeground(Color.WHITE);

	cmdField = new JTextField();
	// Tabbed now does not change focus
	cmdField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
	cmdField.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
	// cmdField.setMargin(new Insets(4, 4, 4, 4));
	cmdField.setMinimumSize(new Dimension(width, (int)(height * 0.1)));
	cmdField.setFont(new Font("Lucida Console", Font.PLAIN, 12));
	cmdField.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		cmdArea.append(cmdField.getText() + "\n");
		handleInput(cmdField.getText());
		cmdField.setText("");
	    }
	});
	cmdField.setBackground(Color.BLACK);
	cmdField.setForeground(Color.WHITE);
	getContentPane().add(cmdField, BorderLayout.SOUTH);
	
	setupInput();
//	pack();
    }
    
    @Override
    public void open(){
	super.open();
	messageOut(game.getMyComputer().getDir().getPath() + "> ");
    }
    
    @Override
    public void close(){
	super.close();
	game.setConnectedComp(game.getMyComputer());
	cmdField.setText("");
	cmdArea.setText("");
    }

    public void handleInput(String s){
	addLastCommand(s);
	String[] command = s.split(" ");
	command[0] = command[0].toLowerCase();

	switch(command[0]){
	    case "exit":
		exit();
	    break;
	    case "cd":
		game.getConnectedComp().changeDir(command);
	    break;
	    case "dir":
		game.getConnectedComp().getDir().addFolder(command);
	    break; // TODO: fix error when not supplying folder nameconn
	    case "ls":
		messageOut(game.getConnectedComp().getDir().getContents());
	    break;
	    /*
	     * bounce (ip) linked list run (program) new (txt)
	     */
	    case "open":// TODO: add to help command
		open(command);
	    break;
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
	    default:
		messageOut("Command not recognized use [help] for a list of commands");
	}
	messageOut("");
	if(game.getConnectedComp().getIp().equals(game.getMyComputer().getIp())){
	    messageOut(game.getMyComputer().getDir().getPath() + ">" + getLastCommand());
	}else{
	    messageOut(
		    "[" + game.getConnectedComp().getIp() + "]:" + game.getConnectedComp().getDir().getPath() + "> ");
	}
    }

    public void messageOut(String s){
	System.out.println(s);
	cmdArea.append(s + "\n");
    }

    public void helpMessage(){
	messageOut("  Commands:\n" + "\texit - exits the game\n" + "\tinfo - displays computer info\n"
		+ "\tcd (folder)- changes current directory to folder\n"
		+ "\tdir - creates a new folder in current directory\n"
		+ "\tls - list all folders and files in current directory\n" + "\thelp - displays commands\n"
		+ "\tconnect (ip) - try connecting to ip\n" + "\tdisconnect - disconnect from connected computer");
    }

    public void open(String[] line){
	if(line.length >= 2){
	    if(game.getConnectedComp().getDir().hasFile(line[1])){
		game.getConnectedComp().getDir().getFile(line[1]).open();
		// messageOut(connectedComp.getDir().getFile(line[1]).toString());
	    }else{
		messageOut("Could not find file " + line[1]);
	    }
	}else{
	    messageOut("Usage: open (file)");
	}
    }

    public void connect(String[] line){
	if(line.length >= 2){
	    if(game.getComps().containsKey(line[1])){
		game.setConnectedComp(game.getComps().get(line[1]));
		messageOut("Connected to computer on " + line[1]);
		game.getMyComputer().getLog().addLine("Connected to computer on " + line[1]);
		game.writeToLog(game.getMyComputer().getIp() + " connected to localhost");
	    }else{
		messageOut("Could not find computer on " + line[1]);
	    }
	}else{
	    messageOut("Usage: connect (ip)");
	}
    }

    public void disconnect(){
	game.setConnectedComp(game.getMyComputer());
	messageOut("Disconnected");
    }

    public void exit(){// TODO::Find a way to respond in GUI
	// messageOut("Are you sure? Type exit again to quit");
	messageOut("Just hit the close button");
	// prompted = true;
	// if(prompted){
	// close();
	// }
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

    private void setupInput(){
	InputMap im = cmdField.getInputMap(JTextField.WHEN_FOCUSED);

	// Buttons
	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "previousCommand");
	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "nextCommand");
	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "cycleFile");

	// Action
	cmdField.getActionMap().put("previousCommand", new AbstractAction(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("UP");
		if(lastCommands.size() > 0){
		    commandIndex = (commandIndex + 1) % lastCommands.size();
		    cmdField.setText(lastCommands.get(commandIndex));
		}
	    }
	});

	cmdField.getActionMap().put("nextCommand", new AbstractAction(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("DOWN");
		if(lastCommands.size() > 0){
		    commandIndex = (commandIndex + lastCommands.size() - 1) % lastCommands.size();
		    cmdField.setText(lastCommands.get(commandIndex));
		}
	    }
	});

	// Override Tab
	cmdField.getActionMap().put("cycleFile", new AbstractAction(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("TAB");
		if(game.getConnectedComp().getCurrentDir().getChildren().size() == 0) return;
		fileIndex = (fileIndex + 1) % game.getConnectedComp().getCurrentDir().getChildren().size();
		int lastIndex = (fileIndex + game.getConnectedComp().getCurrentDir().getChildren().size() - 1)
			% game.getConnectedComp().getCurrentDir().getChildren().size();
		String fileName = game.getConnectedComp().getCurrentDir().getChildren().get(fileIndex).getName();
		String cmd = cmdField.getText()
			.replace(game.getConnectedComp().getCurrentDir().getChildren().get(lastIndex).getName(), "");
		if(cmdField.getText().equals("")){
		    cmdField.setText(fileName);
		}else{
		    cmdField.setText(cmd + fileName);
		}
	    }
	});
    }

}
