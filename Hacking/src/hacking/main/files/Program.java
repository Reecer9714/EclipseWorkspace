package hacking.main.files;

import hacking.main.GUIGame;
import hacking.main.programs.gui.GUIProgram;

public abstract class Program extends File{
	private double version;
	protected GUIProgram guiProgram;
	
	//TODO: Figure out if these constructors are even necessary
	public Program(GUIGame g, String n){
		this(g, n, null, g.getMyComputer().getMainDrive().getDir().getFolder("Programs"));
	}
	
	public Program(GUIGame g, String n, GUIProgram p){
		this(g, n, p, g.getMyComputer().getMainDrive().getDir().getFolder("Programs"));
	}
	
	public Program(GUIGame g, String n, Folder f){
		this(g, n, null, f);
	}
	
	public Program(GUIGame g, String n, GUIProgram p, Folder f){
		super(g, n, f);
		this.version = 1.0;
		this.ext = ".exe";
		this.guiProgram = p;
		f.addFile(this);
	}
	
	public double getVersion(){
		return version;
	}
	
	public void upgradeVersion(){
		this.version += 0.1;
	}
	
	protected void setVersion(double v){
		this.version = v;
	}
	
	//Open GUI
	@Override
	public void open(){
		if(guiProgram != null){
			//open the program
			guiProgram.run();
		}else{
			//open terminal
			//tell terminal this program is running and hand over control
			//run program
			run();
		}
	}
	
	//Used for taking over terminal control
	public abstract void run();
	
}
