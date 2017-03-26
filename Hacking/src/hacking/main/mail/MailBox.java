package hacking.main.mail;

import java.util.ArrayList;

import javax.swing.tree.TreeNode;

public class MailBox{
    private String address;
    private ArrayList<Mail> box;

    public MailBox(String address){
	this.address = address;
	box = new ArrayList<Mail>();
    }

    public String getAddress(){
	return address;
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

}
