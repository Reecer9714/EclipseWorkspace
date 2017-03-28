package hacking.main.mail;

import java.util.Vector;

import hacking.main.Game;

public class MailBox{
    private String address;
    private Vector<Mail> box;

    public MailBox(String address){
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
	Game.getMailModel().addElement(e);
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
