package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.files.*;
import hacking.files.programs.Program;
import hacking.terminal.ConsolePanel;

public class List implements Command{

	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		if(panel.getCurrentDir().getChildCount() <= 0) return;
		
		panel.write(ConsolePanel.TAB);
		
		for(File file : panel.getCurrentDir().getChildren()){
			if(file instanceof Folder){
				panel.fg(ConsolePanel.FOLDER_COLOR).write(file.toString()).rs();
			}else if(file instanceof Program){
				panel.fg(ConsolePanel.PROGRAM_COLOR).write(file.toString()).rs();
			}else{
				panel.write(file.toString());
			}
			panel.write(", ");
		}
		panel.newLine();
	}

	@Override
	public String help(){
		return "ls - lists the files and folders in the current directory";
	}
	
}
