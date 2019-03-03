package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.*;
import hacking.terminal.ConsolePanel;

public class Move implements Command{
	
	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		if(args.size() > 1){
			FileSystem fs = panel.getGame().getFileSystem();
			File org = fs.getFileFromPath(panel.getCurrentDir(), args.get(0));
			File loc = fs.getFileFromPath(panel.getCurrentDir(), args.get(1));
			
			if(org == null){
				panel.writeTab("File " + args.get(0) + " could not be found\n",1);
				return;
			}
			if(loc != null && loc instanceof Folder){
				Folder f = (Folder)loc;
				org.getParent().removeFile(org);
				f.addFile(org);
				return;
			}
			panel.writeTab("New path location could not be found\n", 1);
			return;
		}
		panel.writeTab("Usage: mv (filepath) (newpath)\n", 1);
	}
	
	@Override
	public String help(){
		return "mv (filepath) (newpath) - moves the given file to new location";
	}
	
}
