package hacking.terminal.commands;

import java.util.ArrayList;

import hacking.terminal.ConsolePanel;

public interface Command{
	void run(ConsolePanel panel, ArrayList<String> args);
	
	String help();
}
