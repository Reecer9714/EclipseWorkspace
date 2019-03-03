package hacking.files;

import hacking.Game;
import hacking.terminal.ConsolePanel;

public class Folder extends File{
	//private Folder parent;
	// private HashMap<String, File> children = new HashMap<String, File>();
	
	public Folder(Game g, String s){
		this(g, s, null);
	}
	
	public Folder(Game g, String s, Folder par){
		super(g, s, par);
	}
	
	public void setParent(Folder f){
		parent = f;
	}
	
	public File getFile(String s){
		for(File file : children){
			if(file.getName().equals(s)){ return file; }
		}
		return null;
	}
	
	public Folder getFolder(String s){
		return (Folder)this.getFile(s);
	}
	
	public void addFile(File f){
		if(!this.hasFile(f)){
			children.add(f);
			f.setParent(this);
		}
	}
	
	public void removeFile(File f){
		if(this.hasFile(f)){
			this.getChildren().remove(f);
		}
	}
	
	public void addFolder(String[] command){
		if(command.length < 2){
			//game.getOS().getTerminal().messageOut("Usage: dir [folder]");
			return;
		}
		this.addFolder(command[1]);
	}
	
	public Folder addFolder(String s){
		Folder f = new Folder(game, s, this);
		this.addFolder(f);
		return f;
	}
	
	public Folder addFolder(Folder f){
		if(!this.hasFile(f)){
			children.add(f);
			f.setParent(this);
		}
		return f;
	}
	
	public boolean hasFile(File f){
		return this.hasFile(f.getName());
	}
	
	public boolean hasFile(String s){
		for(File file : children){
			if(file.getName().equals(s)){ return true; }
		}
		return false;
	}
	
	public boolean hasFolder(String s){
		return hasFile(s) && getFile(s) instanceof Folder;
	}
	
	public boolean hasFolder(Folder f){
		return hasFolder(f.getName());
	}
	
	public String getPath(){
		if(parent == null) return getName()+":";
		return getParent().getPath() + "\\" + getName();
	}
	
	@Override
	public boolean getAllowsChildren(){
		return true;
	}
	
	@Override
	public boolean isLeaf(){
		return false;
	}
	
	public String printContents(){
		String files = "   ";
		
		for(File file : children){
			files += file.toString() + ", ";
		}
		
		return files;
	}
	
}
