package discordbot;

import java.util.ArrayList;
import java.util.HashMap;

import discordbot.commands.*;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class Bot{
    private static final String TOKEN = "MjM5NTk1NDk2MTI0MDU1NTU0.Cu3TqA.oV3vK8B-QRyZjZ_yXvk8XchuUSQ";
    //https://discordapp.com/oauth2/authorize?client_id=239595496124055554&scope=bot&permissions=8

    private static JDA jda;
    private static HashMap<String, Command> commands = new HashMap<String, Command>();

    public static HashMap<String, Command> getCommands(){
	return commands;
    }

    public static void main(String[] args){
	try{
	    jda = new JDABuilder().addListener(new BotListener()).setBotToken(TOKEN).buildBlocking();
	    jda.setAutoReconnect(true);
	}
	catch(Exception e){
	    e.printStackTrace();
	}

	commands.put("help", new HelpCommand());
	commands.put("ping", new PingCommand());
    }

    public static void handleCommand(String message, MessageReceivedEvent event){
	ArrayList<String> strings = new ArrayList<String>();
	String cut = message.replace("!", "");
	String[] split = cut.split(" ");

	for(String s : split){
	    strings.add(s);
	}
	String command = strings.get(0);
	String[] args = new String[strings.size() - 1];
	strings.subList(1, strings.size()).toArray(args);
	
	if(command != null)
	    commands.get(command).action(args, event);
    }

}
