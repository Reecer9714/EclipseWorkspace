package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.*;
import hacking.terminal.ConsolePanel;

public class Copy implements Command{

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
				try{
					Folder f = (Folder)loc;
					f.addFile((File)org.clone());
				}
				catch(CloneNotSupportedException e){
					e.printStackTrace();
				}
				return;
			}
			panel.writeTab("New path location could not be found\n", 1);
			return;
		}
		panel.writeTab("Usage: cpy (filepath) (newpath)\n", 1);
	}

	@Override
	public String help(){
		return "cpy (filepath) (newpath) - copies the given file to the new location";
	}
	
}
