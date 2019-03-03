package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.*;
import hacking.terminal.ConsolePanel;

public class ChangeDir implements Command{

	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		FileSystem fs = panel.getGame().getFileSystem();
		if(args.size() > 0){
			changeDir(panel, fs, args.get(0));
			return;
		}else{
			panel.setCurrentDir(fs.getParentDir());
		}
	}
	
	private void changeDir(ConsolePanel panel, FileSystem fs, String path){
		if(path.equals(" ")){
			panel.setCurrentDir(fs.getParentDir());
			return;
		}
		
		Folder cd = panel.getCurrentDir();
		if(path.equals("..") && cd != fs.getParentDir()){
			panel.setCurrentDir(cd.getParent());
			return;
		}
		
		File f = fs.getFileFromPath(cd, path);
		if(f != null && f instanceof Folder){
			panel.setCurrentDir((Folder)f);
			return;
		}
		panel.writeTab("Path did not specify a folder\n",1);
		return;
	}

	@Override
	public String help(){
		return "cd [foldername] - changes current directory to requested folder\n"+
				ConsolePanel.TAB+ConsolePanel.TAB+"when no folder is provided directory is changed to root";
	}
	
}
