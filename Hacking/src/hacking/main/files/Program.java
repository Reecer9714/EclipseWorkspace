package hacking.main.files;

import hacking.main.GUIGame;
import hacking.main.programs.gui.GUIProgram;

public class Program extends File{
	private double version;
	private GUIProgram exe;
	
	public Program(GUIGame g, GUIProgram exe, String n){
		super(g, n);
		this.version = 1.0;
		this.exe = exe;
		this.ext = ".exe";
	}
	
	public Program(GUIGame g, String n, Folder f){
		super(g, n, f);
		this.version = 1.0;
		this.exe = null;
		this.ext = ".exe";
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
	
	public void exit(){
		exe.close();
	}
	
	@Override
	public void open(){
		exe.open();
	}

	public GUIProgram getExe(){
		return exe;
	}
	
}
