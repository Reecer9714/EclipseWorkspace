package hacking.main.files;

import hacking.main.GUIGame;

public class TextFile extends File{
    private String body;

    public TextFile(GUIGame g, String n){
	super(g, n);
	this.ext = ".txt";
	body = "";
    }

    public TextFile(GUIGame g, String n, Folder f){
	super(g, n + ".txt", f);
	body = "";
    }

    @Override
    public void open(){
	game.getOS().getTexteditor().open();
	game.getOS().getTexteditor().load(this);
	//game.messageOut(getContents());
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
	return body;
    }
}
