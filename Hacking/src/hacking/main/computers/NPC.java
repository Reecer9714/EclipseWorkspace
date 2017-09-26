package hacking.main.computers;

import java.util.Vector;

import hacking.main.GUIGame;
import hacking.main.mail.Mail;
import hacking.main.mail.MailBox;

public class NPC{
	private String name;
	private MailBox box;
	
	public NPC(GUIGame g, String n){
		this.name = n;
		box = new MailBox(g, name + "@yougle.com");
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public MailBox getMailBox(){
		return box;
	}
	
	public Vector<Mail> getMail(){
		return box.getBox();
	}
}
