package hacking.main;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import hacking.main.computers.Computer;
import hacking.main.files.TextFile;
import hacking.main.mail.Date;
import hacking.main.mail.Mail;

public class Game extends JFrame{
    private static final long serialVersionUID = 1L;

    public static boolean RUNNING = true;
    public static Game game;
    private static Scanner scan;
    private static Random ran = new Random();
    private static Test gui;

    static Computer myComputer;
    static Computer connectedComp;
    private static JTree fileTree;
    private static JList<Mail> maillist;
    private static DefaultListModel<Mail> maillistData;
    private static TextFile ips;
    private static HashMap<String, Computer> comps = new HashMap<String, Computer>();

    private static LinkedList<String> lastCommands = new LinkedList<String>();
    private static int commandIndex = 0;
    private static int fileIndex = 0;

    // private static final String TAB = "\t";
    // private static double cpuUse;
    // private static double hddUse;
    // private static double netUse;

    public static void main(String[] args){
	game = new Game();
	game.setVisible(true);
	game.start();
    }

    public static String ranIP(){
	return (ran.nextInt(190) + 1) + "." + (ran.nextInt(254) + 1) + "." + (ran.nextInt(254) + 1) + "."
		+ (ran.nextInt(254) + 1);
    }

    public Game(){
	setTitle("Hacking");
	setSize(650, 700);
	setFocusable(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	myComputer = new Computer("My Computer", ranIP());
	Mail mail1 = new Mail(myComputer.getMailBox().getAddress(), "creator@game.com", new Date(3, 18, 97), "welcome",
		"Thanks for Playing this game");
	myComputer.getMail().add(mail1);
	maillistData = new DefaultListModel<Mail>();
	// maillist = new JList<Mail>(myComputer.getMail().toArray(new
	// Mail[0]));
	maillist = new JList<Mail>();
	maillist.setModel(maillistData);
	updateMaillist();

	connectedComp = myComputer;
	scan = new Scanner(System.in);

	fileTree = new JTree(myComputer.getRoot());
	ips = new TextFile("listedips");
	// Lookup server//
	Computer lookup = new Computer("Lookup", "1.2.3.4");
	comps.put("1.2.3.4", lookup);
	lookup.getHome().getFolder("Documents").addFile(ips);

	// add 10 computers with unique ip's
	for(int i = 0; i < 10; i++){
	    Computer c = new Computer("Computer" + i, ranIP());
	    while(comps.containsKey(c.getIp())){
		c.setIp(ranIP());
	    }
	    comps.put(c.getIp(), c);
	    ips.addLine(c.getName() + ": " + c.getIp());
	}

	gui = new Test(this);
	this.getContentPane().add(gui);
	setupInput();
//	addKeyListener(new Input());
	this.requestFocusInWindow();
    }

    public void start(){
	messageOut("Computer System Initialized...");

	while(RUNNING){
	    if(connectedComp.getIp().equals(myComputer.getIp())){
		messageOut("<" + myComputer.getDir().getPath() + "> ");
	    }else{
		messageOut("<[" + connectedComp.getIp() + "]:" + connectedComp.getDir().getPath() + "> ");
	    }
	    handleInput(scan.nextLine());
	}

    }

    @SuppressWarnings("serial")
    private void setupInput(){
	InputMap im = gui.getInputField().getInputMap(JTextField.WHEN_FOCUSED);

	// Buttons
	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "previousCommand");
	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "nextCommand");
	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "cycleFile");

	// Action
	gui.getInputField().getActionMap().put("previousCommand", new AbstractAction(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("UP");
		if(lastCommands.size() > 0){
		    commandIndex = (commandIndex + 1) % lastCommands.size();
		    gui.getInputField().setText(lastCommands.get(commandIndex));
		}
	    }
	});

	gui.getInputField().getActionMap().put("nextCommand", new AbstractAction(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("DOWN");
		if(lastCommands.size() > 0){
		    commandIndex = (commandIndex + lastCommands.size()-1) % lastCommands.size();
		    gui.getInputField().setText(lastCommands.get(commandIndex));
		}
	    }
	});
	
	//Override Tab
	gui.getInputField().getActionMap().put("cycleFile", new AbstractAction(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("TAB");
		if(connectedComp.getCurrentDir().getChildren().size() == 0) return;
		fileIndex = (fileIndex + 1) % connectedComp.getCurrentDir().getChildren().size();
		int lastIndex = (fileIndex + connectedComp.getCurrentDir().getChildren().size()-1) % connectedComp.getCurrentDir().getChildren().size();
		String fileName = connectedComp.getCurrentDir().getChildren().get(fileIndex).getName();
		String cmd = gui.getInputField().getText().replace(connectedComp.getCurrentDir().getChildren().get(lastIndex).getName(), "");
		if(gui.getInputField().getText().equals("")){
		    gui.getInputField().setText(fileName);
		}else{
		    gui.getInputField().setText(cmd + fileName);
		}
	    }
	});
    }

    public static void messageOut(String s){
	System.out.println(s);
	gui.getConsole().append(s + "\n");
    }

    public static void handleInput(String s){
	System.out.println(game.isFocused());
	addLastCommand(s);
	String[] command = s.split(" ");
	command[0] = command[0].toLowerCase();
	switch(command[0]){
	    case "exit":
		exit();
	    break;
	    case "cd":
		connectedComp.changeDir(command);
	    break;
	    case "dir":
		connectedComp.getDir().addFolder(command);
	    break; // TODO: fix error when not supplying folder nameconn
	    case "ls":
		messageOut(connectedComp.getDir().getContents());
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
		messageOut(connectedComp.toString());
	    break;
	    case "help":
		helpMessage();
	    break;
	    default:
		messageOut("Command not recognized use [help] for a list of commands");
	}
    }

    private static void addLastCommand(String s){
	lastCommands.addFirst(s);
	if(lastCommands.size() > 20){
	    lastCommands.removeLast();
	}
    }

    public static void writeToLog(String s){
	connectedComp.getLog().addLine(s);
    }

    public static void open(String[] line){
	if(line.length >= 2){
	    if(connectedComp.getDir().hasFile(line[1])){
		messageOut(connectedComp.getDir().getFile(line[1]).toString());
	    }else{
		messageOut("Could not find computer on " + line[1]);
	    }
	}else{
	    messageOut("Usage: open (file)");
	}
    }

    public static void connect(String[] line){
	if(line.length >= 2){
	    if(comps.containsKey(line[1])){
		connectedComp = comps.get(line[1]);
		messageOut("Connected to computer on " + line[1]);
		myComputer.getLog().addLine("Connected to computer on " + line[1]);
		writeToLog(myComputer.getIp() + " connected to localhost");
	    }else{
		messageOut("Could not find computer on " + line[1]);
	    }
	}else{
	    messageOut("Usage: connect (ip)");
	}
    }

    public static void disconnect(){
	connectedComp = myComputer;
	messageOut("Disconnected");
    }

    public static void helpMessage(){
	messageOut("  Commands:\n" + "\texit - exits the game\n" + "\tinfo - displays computer info\n"
		+ "\tcd (folder)- changes current directory to folder\n"
		+ "\tdir - creates a new folder in current directory\n"
		+ "\tls - list all folders and files in current directory\n" + "\thelp - displays commands\n"
		+ "\tconnect (ip) - try connecting to ip\n" + "\tdisconnect - disconnect from connected computer");
    }

    public static void exit(){// TODO::Find a way to respond in GUI
	messageOut("Are you sure? [Y/N] ");
	String line = scan.nextLine().toLowerCase();
	if(line.equals("y") || line.equals("yes")){
	    RUNNING = false;
	    System.exit(0);
	}
    }

    public static Computer getConnected(){
	return connectedComp;
    }

    public static HashMap<String, Computer> getComps(){
	return comps;
    }

    public String getLastCommand(){
	return lastCommands.peek();
    }

    public JTree getTree(){
	return fileTree;
    }

    public JList<Mail> getMail(){
	return maillist;
    }

    public void updateMaillist(){
	for(Mail p : myComputer.getMail()){
	    maillistData.addElement(p);
	}
	maillist.setModel(maillistData);
    }

    private class Input extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e){
	    System.out.println("Pressed");
	    if(lastCommands.size() > 0){
		if(e.getID() == KeyEvent.VK_UP){
		    System.out.println("UP");
		    commandIndex = (commandIndex + 1) % lastCommands.size();
		    gui.getInputField().setText(lastCommands.get(commandIndex));
		}
		if(e.getID() == KeyEvent.VK_DOWN){
		    System.out.println("Down");
		    commandIndex = (commandIndex - 1) % lastCommands.size();
		    gui.getInputField().setText(lastCommands.get(commandIndex));
		}
	    }
	}
    }
}