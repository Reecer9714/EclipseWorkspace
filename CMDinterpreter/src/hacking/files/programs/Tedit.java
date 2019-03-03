package hacking.files.programs;

import hacking.Game;
import hacking.files.Folder;

public class Tedit extends Program{
	
	public Tedit(Game g){
		super(g, "Tedit");
	}
	
	public Tedit(Game g, Folder f){
		super(g, "Tedit", f);
	}

	@Override
	public void proccessInput(String in){
		// TODO Auto-generated method stub
		
	}
	
}
