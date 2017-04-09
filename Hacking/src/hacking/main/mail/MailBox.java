package hacking.main.mail;

import java.util.Vector;

import hacking.main.GUIGame;

public class MailBox{
    private GUIGame game;
    private String address;
    private Vector<Mail> box;

    public MailBox(GUIGame g, String address){
	this.game = g;
	this.address = address;
	box = new Vector<Mail>();
    }

    public String getAddress(){
	return address;
    }

    public static void send(Mail e, MailBox to){
	to.onRecieve(e);
    }

    public void onRecieve(Mail e){
	box.add(e);
	e.setParent(this);
	
	if(address.equals(game.getMyComputer().getMailBox().getAddress()))
	    game.getOS().getMailbox().getModel().addElement(e);
    }

    public Vector<Mail> getBox(){
	return box;
    }

    public String getContents(){
	String mail = "";
	for(Mail m : box){
	    mail += "  " + m + "\n";
	}
	return mail;
    }

}
