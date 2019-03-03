package hacking.files;

import java.util.ArrayList;

import hacking.Game;
import hacking.terminal.ConsolePanel;

public abstract class File implements Cloneable{
	private String name;
	protected Folder parent;
	protected String ext;
	protected Game game;
	protected ArrayList<File> children;
	
	public File(Game g, String n){
		this(g, n, null, "");
	}
	
	public File(Game g, String n, Folder f){
		this(g, n, null, "");
	}
	
	public File(Game g, String n, Folder f, String ext){
		this.name = n;
		this.parent = f;
		this.game = g;
		this.ext = ext;
		children = new ArrayList<File>();
	}
	
	// public abstract void open(ConsolePanel p);
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setParent(Folder f){
		this.parent = f;
	}
	
	public String getExt(){
		return ext;
	}
	
	public String toString(){
		return name + ext;
	}
	
	public File clone() throws CloneNotSupportedException{
		File f = (File)super.clone();
		f.children = new ArrayList<File>();
		return f;
	}
	
	// Tree Stuff
	public File getChildAt(int childIndex){
		return null;
	}
	
	public int getChildCount(){
		return children.size();
	}
	
	public Folder getParent(){
		return parent;
	}
	
	public boolean hasChild(File f){
		return children.contains(f);
	}
	
	public int getIndex(File f){
		if(parent != null){ return parent.getChildren().indexOf(f); }
		return -1;// maybe null
	}
	
	public boolean getAllowsChildren(){
		return false;
	}
	
	public boolean isLeaf(){
		return true;
	}
	
	public ArrayList<File> getChildren(){
		return children;
	}
	
}
