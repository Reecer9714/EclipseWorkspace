package hacking.main.files;

import java.util.ArrayList;

import javax.swing.tree.TreeNode;

import hacking.main.Game;

public class Folder extends File{
    private Folder parent;
    private ArrayList<File> childs = new ArrayList<File>();
    // private HashMap<String, File> children = new HashMap<String, File>();

    public Folder(String s){
	this(s, null);
    }

    public Folder(String s, Folder par){
	super(s);
	this.parent = par;
    }

    public void setParent(Folder f){
	this.parent = f;
    }
    
    @Override
    public void open(){
	Game.getMyComputer().changeDir(this);
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
	}else Game.messageOut("There is already a file named " + f.getName());
    }

    public void addFolder(String[] command){
	if(command.length < 2){
	    Game.messageOut("Usage: dir [folder]");
	    return;
	}
	this.addFolder(command[1]);
    }

    public void addFolder(String s){
	Folder f = new Folder(s, this);
	this.addFolder(f);
    }

    public void addFolder(Folder f){
	if(!this.hasFile(f)){
	    childs.add(f);
	    f.setParent(this);
	}else Game.messageOut("There is already a folder named " + f.getName());
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
	return childs.get(childIndex);// TODO:
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
	return childs.indexOf(node);// TODO:
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
	String files = "\t";

	for(File file : childs){
	    files += file.getName() + ", ";
	}

	return files;
    }

}
