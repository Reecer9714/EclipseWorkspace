package hacking.main.computers;

import java.util.ArrayList;

import javax.swing.tree.TreeNode;

import hacking.main.*;
import hacking.main.files.Folder;
import hacking.main.files.TextFile;
import hacking.main.mail.*;

public class Computer{
    private String name;
    private String ip;
    private double cpu;// cpu speed
    private double hdd;// drive space
    private double net;// net speed
    private MailBox box;
    private Folder C;
    private Folder home;
    private TextFile log;
    private Folder currentDir;

    public Computer(String n, String s){
	// create a better random creation
	this(n, s, Math.random() + 0.5, Math.random() + 0.5, Math.random() + 0.5);
    }

    public Computer(String n, String s, double cp, double hd, double nt){
	setName(n);
	ip = s;
	setCpu(cp);
	setHdd(hd);
	setNet(nt);

	C = new Folder("C:");
	home = new Folder("Home");
	log = new TextFile("log");

	C.addFolder(home);
	C.addFile(log);

	home.addFolder("Programs");
	home.addFolder("Documents");
	// home.addFile(log);
	currentDir = home;
	
	box = new MailBox(name + "@yougle.com");
    }

    public void changeDir(String[] command){
	if(command.length >= 2){
	    if(command[1].equals("..") && currentDir != C){
		currentDir = currentDir.getParent();
	    }else{
		if(currentDir.hasFile(command[1]))
		    currentDir = (Folder)currentDir.getFile(command[1]);
		else Game.messageOut("Directory " + command[1] + " could not be found");
	    }
	}else{
	    currentDir = home;
	}
    }

    public TreeNode getFileRoot(){
	return C;
    }
    public TreeNode getMailRoot(){
	return box;
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
	return name + ":\n IP: " + ip + "\n CPU: " + cpu + "\n HDD: " + hdd + "\n NETWORK: " + net;
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
    
    public ArrayList<Mail> getMail(){
        return box.getBox();
    }

    public Folder getCurrentDir(){
        return currentDir;
    }
}