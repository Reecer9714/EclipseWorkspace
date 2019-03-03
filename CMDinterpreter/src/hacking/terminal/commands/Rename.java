package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.File;
import hacking.files.FileSystem;
import hacking.terminal.ConsolePanel;

public class Rename implements Command{
	
	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		if(args.size() > 1){
			FileSystem fs = panel.getGame().getFileSystem();
			File f = fs.getFileFromPath(panel.getCurrentDir(), args.get(0));
			if(f != null){
				f.setName(args.get(1));
				return;
			}
			panel.writeTab("Path could not be found\n",1);
			return;
		}
		panel.write("Usage: ren (filename) (newname)\n");
	}
	
	@Override
	public String help(){
		return "ren (filepath) (newname) - renames the give file to the new name";
	}
	
}
