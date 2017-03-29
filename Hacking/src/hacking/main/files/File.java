package hacking.main.files;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

public abstract class File implements TreeNode{
    private Folder parent;
    private String name;

    public File(String n){
	this.name = n;
    }
    
    public File(String n, Folder f){
	this.name = n;
	this.parent = f;
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

    public String toString(){
	return name;
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
