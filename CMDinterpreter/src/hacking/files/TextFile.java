package hacking.files;

import hacking.Game;
import hacking.terminal.ConsolePanel;

public class TextFile extends File{
	private String body;
	
	public TextFile(Game g, String n){
		super(g, n, null, ".txt");
		body = "";
	}
	
	public TextFile(Game g, String n, Folder f){
		super(g, n, f, ".txt");
		body = "";
	}
	
	public String getBody(){
		return body;
	}
	
	public void setBody(String s){
		body = s;
	}
	
	public void addLine(String s){
		body += s + '\n';
	}
	
	public String getContents(){
		return body;
	}
	
}
