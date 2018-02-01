package hacking.main.files;

import java.util.ArrayList;

import javax.swing.tree.TreeNode;

import hacking.main.GUIGame;

public class Folder extends File{
	private Folder parent;
	private ArrayList<File> childs = new ArrayList<File>();
	// private HashMap<String, File> children = new HashMap<String, File>();
	
	public Folder(GUIGame g, String s){
		this(g, s, null);
	}
	
	public Folder(GUIGame g, String s, Folder par){
		super(g, s);
		this.parent = par;
	}
	
	public void setParent(Folder f){
		this.parent = f;
	}
	
	@Override
	public void open(){
		game.getMyComputer().getMainDrive().changeDir(this);
	}
	
	public ArrayList<File> getChildren(){
		return childs;
	}
	
	public File getFile(String s){
		for(File file : childs){
			if(file.getName().equals(s)){ return file; }
		}
		return null;
	}
	
	public Folder getFolder(String s){
		return (Folder)this.getFile(s);
	}
	
	public void addFile(File f){
		if(!this.hasFile(f)){
			childs.add(f);
			f.setParent(this);
		}else game.getOS().getTerminal().messageOut("There is already a file named " + f.getName());
	}
	
	public void addFolder(String[] command){
		if(command.length < 2){
			game.getOS().getTerminal().messageOut("Usage: dir [folder]");
			return;
		}
		this.addFolder(command[1]);
	}
	
	public void addFolder(String s){
		Folder f = new Folder(game, s, this);
		this.addFolder(f);
	}
	
	public void addFolder(Folder f){
		if(!this.hasFile(f)){
			childs.add(f);
			f.setParent(this);
		}else game.getOS().getTerminal().messageOut("There is already a folder named " + f.getName());
	}
	
	public boolean hasFile(File f){
		return this.hasFile(f.getName());
	}
	
	public boolean hasFile(String s){
		for(File file : childs){
			if(file.getName().equals(s)){ return true; }
		}
		return false;
	}
	
	public String getPath(){
		if(getName().equals("C:")) return "C:";
		return getParent().getPath() + "/" + getName();
	}
	
	@Override
	public TreeNode getChildAt(int childIndex){
		return childs.get(childIndex);
	}
	
	@Override
	public int getChildCount(){
		return childs.size();
	}
	
	@Override
	public Folder getParent(){
		return parent;
	}
	
	@Override
	public int getIndex(TreeNode node){
		return childs.indexOf(node);
	}
	
	@Override
	public boolean getAllowsChildren(){
		return true;
	}
	
	@Override
	public boolean isLeaf(){
		return false;
	}
	
	public String getContents(){
		String files = "    ";
		
		for(File file : childs){
			files += file.getName() + ", ";
		}
		
		return files;
	}
	
}
