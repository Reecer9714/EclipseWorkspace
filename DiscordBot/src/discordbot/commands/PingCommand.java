package discordbot.commands;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class PingCommand implements Command{
    private final String HELP = "!ping - test connection to server or bot";
    
    
    @Override
    public boolean called(String[] args, MessageReceivedEvent event){
	return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event){
	event.getTextChannel().sendMessage("PONG!");
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
