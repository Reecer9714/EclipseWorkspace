package hacking.main.computers;

import java.util.Vector;

import javax.swing.tree.TreeNode;

import hacking.main.GUIGame;
import hacking.main.files.Folder;
import hacking.main.files.TextFile;
import hacking.main.mail.Mail;
import hacking.main.mail.MailBox;

public class Computer{
    private GUIGame game;
    private String name;
    private String ip;
    private double cpu;// cpu speed
    private double ram;// ram amount
    private double hdd;// drive amount
    private double net;// net speed
    private MailBox box;
    private Folder C;
    private Folder home;
    private TextFile log;
    private Folder currentDir;

    public Computer(GUIGame g, String n, String s){
	// create a better random creation
	this(g, n, s, Math.random() + 0.5, Math.random() + 0.5, Math.random() + 0.5, Math.random() + 0.5);
    }

    public Computer(GUIGame g, String n, String s, double cp, double rm, double hd, double nt){
	setName(n);
	ip = s;
	setRam(rm);
	setCpu(cp);
	setHdd(hd);
	setNet(nt);

	C = new Folder(g, "C:");
	home = new Folder(g, "Home");
	log = new TextFile(g, "log");

	C.addFolder(home);
	C.addFile(log);

	home.addFolder("Programs");
	home.addFolder("Documents");
	// home.addFile(log);
	currentDir = home;

	box = new MailBox(g, name + "@yougle.com");
    }

    public void changeDir(String[] command){
	if(command.length >= 2){
	    if(command[1].equals("..") && currentDir != C){
		currentDir = currentDir.getParent();
	    }else{
		if(currentDir.hasFile(command[1]))
		    currentDir = (Folder)currentDir.getFile(command[1]);
		else game.getOS().getTerminal().messageOut("Directory " + command[1] + " could not be found");
	    }
	}else{
	    currentDir = home;
	}
    }

    public void changeDir(Folder f){
	if(currentDir.hasFile(f)){
	    currentDir = f;
	}else game.getOS().getTerminal().messageOut("Directory " + f.getName() + " could not be found");
    }

    public TreeNode getFileRoot(){
	return C;
    }

    public TextFile getLog(){
	return log;
    }

    public String getIp(){
	return ip;
    }

    public void setIp(String ip){
	this.ip = ip;
    }

    public Folder getDir(){
	return currentDir;
    }

    public String getName(){
	return name;
    }

    public void setName(String name){
	this.name = name;
    }

    public double getCpu(){
	return cpu;
    }

    public void setCpu(double cpu){
	this.cpu = cpu;
    }
    
    public double getRam(){
	return ram;
    }

    public void setRam(double ram){
        this.ram = ram;
    }

    public double getHdd(){
	return hdd;
    }

    public void setHdd(double hdd){
	this.hdd = hdd;
    }

    public double getNet(){
	return net;
    }

    public void setNet(double net){
	this.net = net;
    }

    public String toString(){
	return name 
		+ ":\n IP: " + ip 
		+ "\n CPU: " + cpu 
		+ "\n RAM: " + ram
		+ "\n HDD: " + hdd
		+ "\n NET: " + net;
    }

    public Folder getC(){
	return C;
    }

    public Folder getHome(){
	return home;
    }

    public MailBox getMailBox(){
	return box;
    }

    public Vector<Mail> getMail(){
	return box.getBox();
    }

    public Folder getCurrentDir(){
	return currentDir;
    }

}