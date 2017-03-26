package discordbot;

import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter{
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
	if(event.getMessage().getContent().startsWith("!") &&
	   event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId()){
	    Bot.handleCommand(event.getMessage().getContent(), event);
	}
    }
    
    @Override
    public void onReady(ReadyEvent event){
	
    }
}
