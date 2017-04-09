package hacking.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import hacking.main.computers.Computer;
import hacking.main.files.TextFile;
import hacking.main.mail.*;

public class GUIGame{
    private static Random ran = new Random();
    protected static GUIGame _this;
    private ReaperOS os;
    
    private Computer myComputer;
    private Computer connectedComp;

    private TextFile ips;
    public final boolean GUI = true;
    private HashMap<String, Computer> comps = new HashMap<String, Computer>();

    public static void main(String args[]){
	try{
	    for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
		if("Nimbus".equals(info.getName())){
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
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
		game.os = new ReaperOS(game);
		game.os.setVisible(true);
		game.os.requestFocusInWindow();
		game.init();
	    }
	});
    }

    public GUIGame(){
	_this = this;
	myComputer = new Computer(this, "MyComputer", ranIP());
	comps.put("127.0.0.1", myComputer);

	connectedComp = myComputer;

	ips = new TextFile(this, "listedips");
	// Lookup server//
	Computer lookup = new Computer(this, "Lookup", "1.2.3.4");
	comps.put("1.2.3.4", lookup);
	lookup.getHome().getFolder("Documents").addFile(ips);

	// add 10 computers with unique ip's
	for(int i = 0; i < 10; i++){
	    Computer c = new Computer(this, "Computer" + i, ranIP());
	    while(comps.containsKey(c.getIp())){
		c.setIp(ranIP());
	    }
	    comps.put(c.getIp(), c);
	    ips.addLine(c.getName() + ": " + c.getIp());
	}
	
	
    }
    
    public void init(){
	Mail mail1 = new Mail(this, myComputer.getMailBox().getAddress(), "creator@game.com", new Date(3, 18, 97), "Welcome",
		"Thanks for Playing this game");
	MailBox.send(mail1, myComputer.getMailBox());

	Timer timer = new Timer(0, new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		System.out.println("You have Mail!");
		Mail mail = new Mail(GUIGame._this, myComputer.getMailBox().getAddress(), "tom.skillman@h3x.com", new Date(3, 18, 97),
			"Work",
			"Hi I heard you were looking for some work\n" + "Check out this lookup server on 1.2.3.4\n"
				+ "It keeps track of some noteworthy servers you might want to check out");
		MailBox.send(mail, myComputer.getMailBox());
	    }
	});
	timer.setInitialDelay(1000 * 10);
	timer.setRepeats(false);
	timer.start();
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

    public ReaperOS getOS(){
	return os;
    }
}
