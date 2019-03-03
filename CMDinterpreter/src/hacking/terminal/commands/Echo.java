package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.terminal.ConsolePanel;

public class Echo implements Command{
	
	@Override
	public void run(ConsolePanel panel, ArrayList<String> args){
		if(args.size() > 0){
			panel.writeln(args.get(0));
		}
	}
	
	@Override
	public String help(){
		return "echo (string) - prints string out to console";
	}
	
}
