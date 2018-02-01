package hacking.main.files;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import hacking.main.GUIGame;

public abstract class File implements TreeNode{
	private Folder parent;
	private String name;
	protected String ext;
	protected GUIGame game;
	
	public File(GUIGame g, String n){
		this.name = n;
		this.ext = "";
		this.game = g;
	}
	
	public File(GUIGame g, String n, Folder f){
		this.name = n;
		this.parent = f;
		this.game = g;
		this.ext = "";
	}
	
	public abstract void open();
	
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
	
	@Override
	public TreeNode getChildAt(int childIndex){
		return null;
	}
	
	@Override
	public int getChildCount(){
		return 0;
	}
	
	@Override
	public TreeNode getParent(){
		return parent;
	}
	
	@Override
	public int getIndex(TreeNode node){
		return 0;
	}
	
	@Override
	public boolean getAllowsChildren(){
		return false;
	}
	
	@Override
	public boolean isLeaf(){
		return true;
	}
	
	@Override
	public Enumeration<File> children(){
		return null;
	}
	
}
