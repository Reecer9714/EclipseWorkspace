package hacking.main.computers;

import hacking.main.GUIGame;
import hacking.main.files.Folder;
import hacking.main.files.TextFile;

public class HardDrive extends Folder{
	private Folder home;
	private TextFile log;
	private Folder currentDir;
	
	public HardDrive(GUIGame g, String driveName){
		super(g,driveName+":");
		home = new Folder(g, "Home");
		log = new TextFile(g, "log");
		
		addFolder(home);
		addFile(log);
		
		home.addFolder("Programs");
		home.addFolder("Documents");
		
		setDir(home);
	}
	
	public boolean changeDir(String filename){
		if(!filename.equals(" ")){
			if(filename.equals("..") && currentDir != this){
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

	public Folder getDir(){
		return currentDir;
	}

	public void setDir(Folder currentDir){
		this.currentDir = currentDir;
	}

	public TextFile getLog(){
		return log;
	}

	public Folder getHome(){
		return home;
	}

	public Folder getCurrentDir(){
		return currentDir;
	}
}
