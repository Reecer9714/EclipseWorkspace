package discordbot.commands;

import discordbot.Bot;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class HelpCommand implements Command{
    private final String HELP = "!help - provides a list of commands and their uses";
    
    @Override
    public boolean called(String[] args, MessageReceivedEvent event){
	return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event){
	// TODO Auto-generated method stub
	for (Command c : Bot.getCommands().values()) {
	    String out = "";
	    out += c.help()+ "\n";
	    event.getTextChannel().sendMessage(out);
	}
    }

    @Override
    public String help(){
	return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event){
	// TODO Auto-generated method stub

    }

}
