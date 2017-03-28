package hacking.main.mail;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import hacking.main.Game;
import hacking.main.files.File;

public class Mail{
    private String to;
    private String from;
    private Date sent;
    private String subject;
    private String body;
    private File attach;
    private MailBox parent;
    private boolean read;
    
    public Mail(String to, String from, Date sent, String subject, String body){
	this.to = to;
	this.from = from;
	this.subject = subject;
	this.body = body;
	this.sent = sent;
	this.setRead(false);
    }
    
    public void setParent(MailBox parent){
	this.parent = parent;
    }
    
    public MailBox getMailBox(){
	return parent;
    }
    
    public void open(){
	this.read = true;
	Game.messageOut(getContents());
    }
    
    public String getContents(){
	String r = String.format("To: %s\nFrom: %s\n Subject: %s\n Content: \n%s\n\t", to, from, subject, body);
	if(hasAttach()) r+= "Attached: " + attach + " (use [download])";
	return r;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public File getAttach(){
        return attach;
    }

    public void setAttach(File attach){
        this.attach = attach;
    }
    
    public boolean hasAttach(){
	if(attach != null) return true;
	return false;
    }

    public String getTo(){
        return to;
    }

    public String getFrom(){
        return from;
    }

    public String getSubject(){
        return subject;
    }

    public Date getSent(){
        return sent;
    }

    public void setSent(Date sent){
        this.sent = sent;
    }
    
    public boolean isRead(){
	return read;
    }

    public void setRead(boolean read){
	this.read = read;
    }

    @Override
    public String toString(){
	return subject + ": " + from;
    }

}
