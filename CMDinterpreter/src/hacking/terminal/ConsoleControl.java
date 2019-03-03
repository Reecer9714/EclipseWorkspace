package hacking.terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hacking.Game;
import hacking.terminal.commands.*;

public class ConsoleControl{
	
	private Game game;
	private HashMap<String, Command> commands;
	private ConsolePanel panel;
	private Help help;
	private ConsoleInput input;
	
	public ConsoleControl(Game game, ConsolePanel panel){
		this.game = game;
		this.panel = panel;
		help = new Help();
		commands = new HashMap<String, Command>();
		input = new ConsoleInput();
		
		initCommands();
	}
	
	public void proccessInput(String in){
		String[] split = in.split(" ", 2);
		String cmd = split[0].toLowerCase();
		
		if(cmd.equals("")) return;
		
		ArrayList<String> args = new ArrayList<String>();
		if(split.length > 1){
			Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(split[1]);
			while (m.find())
				args.add(m.group(1).replace("\"", ""));
		}
		//Send to running program
		
		if(cmd.equals("help")){ 
			help.run(panel, args); 
			return;
		}
		
		if(!commands.containsKey(cmd)) return;
		
		commands.get(cmd).run(panel, args);
	}
	
	public void initCommands(){
		commands.put("cd", new ChangeDir());
		commands.put("ls", new List());
		commands.put("mkdir", new MkDir());
		commands.put("echo", new Echo());
		commands.put("ren", new Rename());
		commands.put("clear", new Clear());
		commands.put("mv", new Move());
		commands.put("cpy", new Copy());
		commands.put("run", new Run());
		//More to come here
		
	}
	
	private class Help implements Command{
		@Override
		public void run(ConsolePanel panel, ArrayList<String> args){
			if(args.size() > 0){
				panel.writeTab(commands.get(args.get(0)).help()+'\n',1);
				return;
			}
			panel.writeln(help());
		}
		
		@Override
		public String help(){
			String r = "";
			for(Command c : commands.values()){
				//if(commands.get) continue;
				r += ConsolePanel.TAB + c.help() + '\n';
			}
			
			r += ConsolePanel.TAB + "help (command) - prints out the given commands help info\n";
			return r;
		}
	}
	
	class ConsoleInput extends TerminalAdapter{
		
		public void onEnter(){
	
			if(panel.getCurrentProgram() == null){
				String in = panel.getInputStream().flush();
				panel.getCommandBuffer().add(in);
				panel.newLine();
				proccessInput(in);
				if(panel.getCurrentProgram() == null) panel.showPrompt();
			}
		}
		
		public void onBack(){
			
			int index = panel.getCursorIndex();
			int minDex = panel.getMinCursorPos();
			int maxDex = panel.getMaxCursorPos();
			
			if(index <= minDex) return;
			if(index >= maxDex){
				panel.setMaxCursorPos(index-1);
			}
			
			if(panel.getCursorX() <= 0){
				panel.setCursorX(panel.getColumns()-1);
				panel.moveCursor(0, -1);
				
				while(panel.getCursorX() > 0 && panel.getCharAt(panel.getCursorX(), panel.getCursorY()) == ' '){
					panel.moveCursor(-1, 0);
				}
				
				panel.insert(' ');
				panel.setMaxCursorPos(panel.getCursorIndex());
			}else{		
				panel.moveCursor(-1, 0);
				panel.insert(' ');
			}
			
			panel.getInputStream().remove();
		}
		
		public void onLeft(){
			if(panel.getCursorIndex() > panel.getMinCursorPos())
				panel.moveCursor(-1, 0);
		}
		
		public void onRight(){
			if(panel.getCursorIndex() < panel.getMaxCursorPos())
				panel.moveCursor(1, 0);
		}
		
		public void onKey(char c){
			if(panel.getCurrentProgram() == null || panel.getCurrentProgram().isWaiting()){
				panel.getInputStream().add(c);
				panel.write(c);
			}
			panel.repaint();
		}
		
	}
	
	public ConsoleInput getInput(){
		return input;
	}
	
}
