package hacking.main.computers;

import hacking.main.GUIGame;
import hacking.main.files.Folder;
import hacking.main.files.TextFile;

public class HardDrive extends Folder{
	private Folder home;//
	private TextFile log;//
	private Folder currentDir;//
	
	public HardDrive(GUIGame g, String driveName){
		super(g,driveName+":");
		home = new Folder(g, "Home");
		log = new TextFile(g, "log");
		
		addFolder(home);
		addFile(log);
		
		home.addFolder("Programs");
		home.addFolder("Documents");
		
		setCurrentDir(home);
	}

	public Folder getCurrentDir(){
		return currentDir;
	}

	public void setCurrentDir(Folder currentDir){
		this.currentDir = currentDir;
	}
}
