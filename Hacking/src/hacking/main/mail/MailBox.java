package hacking.main.mail;

import java.util.*;

import javax.swing.tree.TreeNode;

public class MailBox implements TreeNode{
    private String address;
    private ArrayList<Mail> box;

    public MailBox(String address){
	this.address = address;
	box = new ArrayList<Mail>();
    }

    public String getAddress(){
	return address;
    }
    
    public void send(Mail e, MailBox to){
	to.getBox().add(e);
	e.setParent(to);
    }

    public ArrayList<Mail> getBox(){
	return box;
    }

    public String getContents(){
	String mail = "";
	for(Mail m : box){
	    mail += "  " + m + "\n";
	}
	return mail;
    }

    @Override
    public TreeNode getChildAt(int childIndex){
	return box.get(childIndex);
    }

    @Override
    public int getChildCount(){
	return box.size();
    }

    @Override
    public TreeNode getParent(){
	return null;
    }

    @Override
    public int getIndex(TreeNode node){
	return box.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren(){
	return true;
    }

    @Override
    public boolean isLeaf(){
	return false;
    }

    @Override
    public Enumeration<Mail> children(){
	return Collections.enumeration(box);
    }

}
