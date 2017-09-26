package hacking.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;
import javax.swing.UIManager;

import hacking.main.computers.*;
import hacking.main.files.TextFile;
import hacking.main.mail.*;

public class GUIGame{
	private static Random ran = new Random();
	protected static GUIGame _this;
	private ReaperOS os;
	
	private NPCComputer myComputer;
	private Computer connectedComp;
	
	private TextFile ips;
	public final boolean GUI = true;
	private HashMap<String, Computer> comps = new HashMap<String, Computer>();
	private NPC player;
	
	public static void main(String args[]){
		// UIManager.put("DesktopPane[Enabled].backgroundPainter");
		
		try{
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception ex){
			Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		/* Start the Game */
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
		player = new NPC(this, "anonymous");
		myComputer = new NPCComputer(this, player, ranIP());
		myComputer.setAccess(true);
		comps.put("127.0.0.1", myComputer);
		connectedComp = myComputer;
		
		// Upgrades
		Upgrades upgrades = new Upgrades();
		upgrades.items.get(Upgrades.CPU).add(upgrades.new Upgrade("Stock", 50, 50));
		upgrades.items.get(Upgrades.CPU).add(upgrades.new Upgrade("AMD", 300, 100));
		upgrades.items.get(Upgrades.CPU).add(upgrades.new Upgrade("Intel", 700, 500));
		
		upgrades.items.get(Upgrades.RAM).add(upgrades.new Upgrade("Stock", 50, 128));
		upgrades.items.get(Upgrades.RAM).add(upgrades.new Upgrade("HyperX", 300, 512));
		upgrades.items.get(Upgrades.RAM).add(upgrades.new Upgrade("Corsair", 700, 1024));
		
		upgrades.items.get(Upgrades.HDD).add(upgrades.new Upgrade("Stock", 50, 50));
		upgrades.items.get(Upgrades.HDD).add(upgrades.new Upgrade("WesternDigital", 300, 100));
		upgrades.items.get(Upgrades.HDD).add(upgrades.new Upgrade("Seagate", 700, 500));
		
		ips = new TextFile(this, "listedips");
		// Lookup server//
		Computer lookup = new Computer(this, "1.2.3.4");
		comps.put("1.2.3.4", lookup);
		lookup.getHome().getFolder("Documents").addFile(ips);
		
		// add 10 computers with unique ip's
		for(int i = 0; i < 10; i++){
			NPCComputer c = new NPCComputer(this, player, ranIP());
			while(comps.containsKey(c.getIp())){
				c.setIp(ranIP());
			}
			comps.put(c.getIp(), c);
			ips.addLine(c.getIp());
		}
		
	}
	
	public void init(){
		NPC player = myComputer.getOwner();
		Mail mail1 = new Mail(this, player.getMailBox().getAddress(), "creator@game.com", new Date(3, 18, 97),
				"Welcome", "Thanks for Playing this game");
		MailBox.send(mail1, player.getMailBox());
		
		Timer timer = new Timer(0, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("You have Mail!");
				Mail mail = new Mail(GUIGame._this, player.getMailBox().getAddress(), "tom.skillman@h3x.com",
						new Date(3, 18, 97), "Work",
						"Hi I heard you were looking for some work\n" + "Check out this lookup server on 1.2.3.4\n"
								+ "It keeps track of some noteworthy servers you might want to check out");
				MailBox.send(mail, player.getMailBox());
			}
		});
		timer.setInitialDelay(1000 * 10);
		timer.setRepeats(false);
		timer.start();
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
	
	public Random getRan(){
		return ran;
	}
	
	public NPCComputer getMyComputer(){
		return myComputer;
	}
	
	public HashMap<String, Computer> getComps(){
		return comps;
	}
	
	public ReaperOS getOS(){
		return os;
	}
}
