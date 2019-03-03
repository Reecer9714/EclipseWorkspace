package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.*;
import hacking.terminal.ConsolePanel;

public class MkDir implements Command{
	
	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		FileSystem fs = panel.getGame().getFileSystem();
		Folder addIn = panel.getCurrentDir();
		if(args.size() > 1){
			File temp = fs.getFileFromPath(panel.getCurrentDir(), args.get(1));
			if(temp instanceof Folder){
				addIn = (Folder)temp;
			}else{
				panel.writeTab("Path did not specify a folder\n", 1);
			}
		}
		
		if(args.size() > 0){
			if(!addIn.hasFile(args.get(0))){
				Folder toAdd = new Folder(panel.getGame(), args.get(0), addIn);
				addIn.addFolder(toAdd);
				return;
			}
			panel.writeTab("Folder already exists with name " + args.get(0)+'\n', 1);
			return;
		}
		panel.writeln("Usage: mkdir (filename)");
	}
	
	@Override
	public String help(){
		return "mkdir (foldername) [path] - creates a folder with the given name at the optional path";
	}
	
}
