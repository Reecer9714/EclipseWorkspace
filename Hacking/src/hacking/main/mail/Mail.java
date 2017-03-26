package hacking.main.mail;

import hacking.main.Game;
import hacking.main.files.File;

public class Mail{
    private String to;
    private String from;
    private Date sent;
    private String subject;
    private String body;
    private File attach;
    
    public Mail(String to, String from, Date sent, String subject, String body){
	this.to = to;
	this.from = from;
	this.subject = subject;
	this.body = body;
	this.sent = sent;
    }
    
    public void open(){
	Game.messageOut(getContents());
    }
    
    public String getContents(){
	String r = String.format("To: %s\nFrom: %s\n Subject: %s\n Content: %s\n", to, from, subject, body);
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
    
    @Override
    public String toString(){
	return sent + ": " + subject;
    }
    
}
