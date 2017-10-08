package hacking.main.computers;

import javax.swing.tree.TreeNode;

import hacking.main.GUIGame;
import hacking.main.files.Folder;
import hacking.main.files.TextFile;
import hacking.main.programs.gui.Terminal;

public class Computer{
	private String ip;
	private Folder C;//
	private Folder home;//
	private TextFile log;//
	private Folder currentDir;//
	private String password;
	private String possibleChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private boolean access;
	
	public Computer(GUIGame g, String ip){
		// create a better random creation
		this(g, ip, "");
		String pw = "";
		int length = (g.getRan().nextInt(9)) + 4;
		for(int i = 0; i < length; i++){
			pw += possibleChar.charAt(g.getRan().nextInt(possibleChar.length()));
		}
		this.password = pw;
	}
	
	public Computer(GUIGame g, String ip, String pw){
		this.password = pw;
		this.ip = ip;
		this.access = false;
		
		C = new Folder(g, "C:");
		home = new Folder(g, "Home");
		log = new TextFile(g, "log");
		
		C.addFolder(home);
		C.addFile(log);
		
		home.addFolder("Programs");
		home.addFolder("Documents");
		// home.addFile(log);
		currentDir = home;
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
		getLog().addLine(s);
	}
	
	public boolean changeDir(String filename){
		if(!filename.equals(" ")){
			if(filename.equals("..") && currentDir != C){
				currentDir = currentDir.getParent();
				return true;
			}else{
				Folder f = currentDir.getFolder(filename);
				if(f != null){
					return changeDir(f);
				}
				return false;
			}
		}else{
			currentDir = home;
			return true;
		}
	}
	
	public boolean changeDir(Folder f){
		if(currentDir.hasFile(f)){
			currentDir = f;
			return true;
		}
		return false;
	}
	
	// Get/Set
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
	
	public String toString(){
		return "IP: " + ip + "\n PW: " + password;
	}
	
	public Folder getC(){
		return C;
	}
	
	public Folder getHome(){
		return home;
	}
	
	public Folder getCurrentDir(){
		return currentDir;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public boolean isAccess(){
		return access;
	}
	
	public void setAccess(boolean access){
		this.access = access;
	}
	
}