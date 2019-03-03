package hacking.files.programs;

import hacking.Game;
import hacking.files.Folder;
import hacking.files.TextFile;
import hacking.terminal.ConsolePanel;

public class AutoCommandMacro extends TextFile{

	public AutoCommandMacro(Game g, String n){
		super(g, n, null);
		this.ext = ".acm";
	}
	
	public AutoCommandMacro(Game g, String n, Folder f){
		super(g, n, f);
		this.ext = ".acm";
	}

	public void start(ConsolePanel p){
		String[] lines = getBody().split("\n");
		
		for(String line: lines){
			//Proccess each line
			p.getControl().proccessInput(line);
		}
	}
	
}
