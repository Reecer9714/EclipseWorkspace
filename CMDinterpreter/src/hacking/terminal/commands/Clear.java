package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.terminal.ConsolePanel;

public class Clear implements Command{
	
	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		panel.clearScreen();
	}
	
	@Override
	public String help(){
		// TODO Auto-generated method stub
		return "clear - clears the screen";
	}
	
}
