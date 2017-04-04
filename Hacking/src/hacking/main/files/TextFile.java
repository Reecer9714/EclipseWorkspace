package hacking.main.files;

import hacking.main.Game;

public class TextFile extends File{
    private String body;
    
    public TextFile(String n){
	super(n);
	this.ext = ".txt";
	body = "";
    }

    public TextFile(String n, Folder f){
	super(n + ".txt", f);
	body = "";
    }
    
    @Override
    public void open(){
	Game.messageOut(getContents());
    }

    public String getBody(){
	return body;
    }

    public void setBody(String s){
	body = s;
    }

    public void addLine(String s){
	body += s + "\n";
    }

    public String getContents(){
	return getName() + ":\n" + body;
    }
}
