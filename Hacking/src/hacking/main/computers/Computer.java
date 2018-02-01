package hacking.main.computers;

import javax.swing.tree.TreeNode;

import hacking.main.GUIGame;
import hacking.main.internet.IP;
import hacking.main.programs.gui.Terminal;

public class Computer{
	private HardDrive C;
	private IP publicIp;
	private String password;
	private String possibleChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private boolean access;
	
	public Computer(GUIGame g, IP ip){
		// create a better random creation
		this(g, ip, "");
		String pw = "";
		int length = (g.getRan().nextInt(9)) + 4;
		for(int i = 0; i < length; i++){
			pw += possibleChar.charAt(g.getRan().nextInt(possibleChar.length()));
		}
		this.password = pw;
	}
	
	public Computer(GUIGame g, IP ip, String pw){
		this.password = pw;
		this.publicIp = ip;
		this.access = false;
		
		C = new HardDrive(g, "C");
	}
	
	// Events
	public void onConnect(Terminal t){
		// t.messageOut("You will need to use the login command to have full
		// remote access");
	}
	
	public boolean login(String s){
		if(s.equals(password)){
			setAccess(true);
			return true;
		}
		return false;
	}
	
	// Helpers
	public void writeToLog(String s){
		C.getLog().addLine(s);
	}
	
	// Get/Set
	public TreeNode getFileRoot(){
		return C;
	}
	
	public IP getPublicIp(){
		return publicIp;
	}
	
	public void setPublicIp(IP ip){
		this.publicIp = ip;
	}
	
	public String toString(){
		return "IP: " + publicIp + "\n PW: " + password;
	}
	
	public boolean isAccess(){
		return access;
	}
	
	public void setAccess(boolean access){
		this.access = access;
	}

	public HardDrive getMainDrive(){
		return C;
	}

	public void setMainDrive(HardDrive drive){
		C = drive;
	}

	public String getPassword(){
		return password;
	}
	
}