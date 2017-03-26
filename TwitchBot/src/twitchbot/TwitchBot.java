package twitchbot;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import json.Json;
import json.JsonObject;

public class TwitchBot extends PircBot{
    public static View view;
    public static String API = "https://api.twitch.tv/kraken";
    public static String accessToken = "zzklno04z2uf4nal9d42sufpg9jh62";
    public static String oauth = "oauth:n15a78xur761z93olai1ftcvukeis3";
    public static String client_id = "9numr7mgtyswmpi41tdw5sfcyrzkqw3";
    public static String defualtChannel = "#n3rdfusion";
    public String currentChannel = defualtChannel;
    public String currentTitle;
    public String currentGame;
    public User[] viewers = new User[10];
    Timer updateTimer;
    JsonObject jsonObj;

    public TwitchBot(){
	this.setName("Reecer9714");
	setVerbose(true);
	updateTimer = new Timer();
	updateTimer.scheduleAtFixedRate(new TimerTask(){
	    @Override
	    public void run(){
		try{
		    update();
		}
		catch(IOException e){
		    e.printStackTrace();
		}
	    }
	}, 30 * 1000, 30 * 1000);

    }

    // Handles /commands from bot user
    public void handleInput(String s){
	String[] command = s.split(" ");
	String extra = "";
	command[0] = command[0].toLowerCase();
	switch(command[0]){
	    case "/help":
		messageOut("/join <channelname> - joins inputed channel");
	    break;
	    case "/join":
		join("#" + command[1]);
		if(command[1].charAt(0) == '#'){
		    currentChannel = command[1].substring(1);
		}else{
		    currentChannel = command[1];
		}
	    break;
	    case "/game":
		if(command.length > 1){
		    extra = command[1];
		    if(command.length > 2){
			int i = 2;
			while(i < command.length){
			    extra += command[i] + " ";
			    i++;
			}
		    }
		    jsonObj.set("game", extra);
		}else{
		    messageOut("Current Game: " + currentGame);
		}
	    break;
	    case "/title":
		if(command.length > 1){
		    extra = command[1];
		    if(command.length > 2){
			int i = 2;
			while(i < command.length){
			    extra += command[i] + " ";
			    i++;
			}
		    }
		    jsonObj.set("status", extra);
		}else{
		    messageOut("Current Title: " + currentTitle);
		}
	    break;
	    default:
		messageOut("Command not recognized use [help] for a list of commands");
	}
    }

    // https://api.twitch.tv/kraken/oauth2/authorize?response_type=code&client_id=9numr7mgtyswmpi41tdw5sfcyrzkqw3&redirect_uri=https://www.twitch.tv/reecer9714&scope=channel_editor
    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message){
	// Could add blacklisting words here
	// Or highlighting words

	messageOut(sender + ": " + message);

	// Handle !commands from clients
	// if(message.equalsIgnoreCase("!game")){
	// sendMessage(channel, "The current game is "+currentGame);
	// view.getChat().append("Reecer9714(BOT): The current game is
	// "+currentGame+"\n");
	// }
    }

    public void messageOut(String s){
	view.getChat().append(s + "\n");
    }

    public void onConnect(){
	view.getChat().append("joined " + currentChannel + "\n");
    }

    public void update() throws IOException{
	URL url = new URL(API + "/channels/" + currentChannel.substring(0) + "?client_id=" + client_id);
	log(API + "/channels/" + currentChannel + "?client_id=" + client_id);
	URLConnection conn = url.openConnection();
	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String inputLine = br.readLine();
	br.close();
	jsonObj = Json.parse(inputLine).asObject();
	currentGame = jsonObj.get("game").toString();
	currentTitle = jsonObj.get("status").toString();

	if(view != null){
	    view.setTitle(currentTitle + " Playing" + currentGame);
	    if(!viewers.equals(getUsers(currentChannel))){
		viewers = getUsers(currentChannel);
		view.getViewer().setText("");
		for(User u : viewers){
		    view.getViewer().append(u.getNick() + "\n");
		}
	    }
	}
    }

    public static void main(String[] args) throws Exception{
	TwitchBot bot = new TwitchBot();
	view = new View(bot);
	view.setVisible(true);
	bot.connect("irc.twitch.tv", 6667, "oauth:n15a78xur761z93olai1ftcvukeis3");
	bot.join(defualtChannel);
    }

    public void join(String s){
	joinChannel(s);
	try{
	    update();
	}
	catch(IOException e){
	    e.printStackTrace();
	}
	// currentChannel = s;
	// view.setTitle(currentChannel + " Playing" + currentGame);
    }

    public String getGame(){
	return currentGame;
    }

    public String getTitle(){
	return currentTitle;
    }

}
