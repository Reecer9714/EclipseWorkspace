package hacking.files.programs;

import java.util.ArrayList;

import hacking.Game;
import hacking.files.Folder;
import hacking.terminal.ConsolePanel;

public class TestPro extends Program{
	
	public TestPro(Game g){
		super(g, "TestPro");
	}
	
	public TestPro(Game g, Folder f){
		super(g, "TestPro", f);
	}
	
	@Override
	public void start(ConsolePanel p, ArrayList<String> args) throws InterruptedException{
		super.start(p, args);
		if(args.size() <= 0) return;
		for(String s: args){
			System.out.print(s + ", ");
		}
	}
	
	@Override
	public void proccessInput(String in){
		//System.out.println(in);
		panel.write("You entered: " + in);
	}

	@Override
	public void run(){
		try{
			panel.writeln("Starting...");
			Thread.sleep(3000);
			//panel.write("Enter your Name:");
			String name = getPrompt("Enter your Name:");
			panel.writeln("Your name is " + name);
			panel.writeln("Ending...");
			exit(panel, t);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
}
