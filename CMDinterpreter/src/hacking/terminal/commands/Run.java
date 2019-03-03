package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.File;
import hacking.files.FileSystem;
import hacking.files.programs.Program;
import hacking.terminal.ConsolePanel;

public class Run implements Command{
	ConsolePanel panel;
	
	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		this.panel = panel;
		if(args.size() > 0){
			FileSystem fs = panel.getGame().getFileSystem();
			File f = fs.getFileFromPath(fs.getPrograms(), args.get(0));
			if(f != null && f instanceof Program){
				Program p = (Program)f;
				panel.setCurrentProgram(p);
				try{
					p.start(panel, new ArrayList<String>(args.subList(1, args.size())));
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public String help(){
		return "run (filepath) - NOT IMPLEMENTED";
	}
	
}
