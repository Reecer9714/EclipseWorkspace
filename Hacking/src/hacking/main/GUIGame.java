package hacking.main;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import hacking.main.computers.Computer;
import hacking.main.files.TextFile;

public class GUIGame{
    private static Random ran = new Random();
    private Computer myComputer;
    private Computer connectedComp;

    private TextFile ips;
    public final boolean GUI = true;
    private HashMap<String, Computer> comps = new HashMap<String, Computer>();

    public static void main(String args[]){
	try{
	    for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
		System.out.println(info.getName());
		if("Nimbus".equals(info.getName())){
		    UIManager.setLookAndFeel(info.getClassName());
		    //break;
		}
	    }
	}
	catch(ClassNotFoundException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(InstantiationException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(IllegalAccessException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(UnsupportedLookAndFeelException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}

	/* Create and display the form */
	EventQueue.invokeLater(new Runnable(){
	    public void run(){
		GUIGame game = new GUIGame();
		ReaperOS os = new ReaperOS(game);
		os.setVisible(true);
		os.requestFocusInWindow();
	    }
	});
    }

    public GUIGame(){
	myComputer = new Computer("MyComputer", ranIP());
	comps.put("127.0.0.1", myComputer);

	connectedComp = myComputer;

	ips = new TextFile("listedips");
	// Lookup server//
	Computer lookup = new Computer("Lookup", "1.2.3.4");
	comps.put("1.2.3.4", lookup);
	lookup.getHome().getFolder("Documents").addFile(ips);

	// add 10 computers with unique ip's
	for(int i = 0; i < 10; i++){
	    Computer c = new Computer("Computer" + i, ranIP());
	    while(comps.containsKey(c.getIp())){
		c.setIp(ranIP());
	    }
	    comps.put(c.getIp(), c);
	    ips.addLine(c.getName() + ": " + c.getIp());
	}

    }

    public void writeToLog(String s){
	this.connectedComp.getLog().addLine(s);
    }
    
    public static String ranIP(){
	return (ran.nextInt(190) + 1) + "." + (ran.nextInt(254) + 1) + "." + (ran.nextInt(254) + 1) + "."
		+ (ran.nextInt(254) + 1);
    }

    public Computer getConnectedComp(){
	return connectedComp;
    }

    public void setConnectedComp(Computer connectedComp){
	this.connectedComp = connectedComp;
    }

    public static Random getRan(){
	return ran;
    }

    public Computer getMyComputer(){
	return myComputer;
    }

    public HashMap<String, Computer> getComps(){
	return comps;
    }
}
