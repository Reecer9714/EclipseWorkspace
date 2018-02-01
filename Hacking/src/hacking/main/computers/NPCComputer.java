package hacking.main.computers;

import hacking.main.GUIGame;
import hacking.main.internet.IP;

public class NPCComputer extends Computer{
	private NPC owner;
	private double cpu;// cpu speed
	private double ram;// ram amount
	private double hdd;// drive amount
	private double net;// net speed
	
	public NPCComputer(GUIGame g, NPC n, IP ip){
		this(g,n,ip,Math.random()*10,Math.random()*10,Math.random()*10,Math.random()*10);
	}

	public NPCComputer(GUIGame g, NPC n, IP ip, double cp, double rm, double hd, double nt){
		super(g, ip);
		this.owner = n;
		setRam(rm);
		setCpu(cp);
		setHdd(hd);
		setNet(nt);
	}
	
	public double getCpu(){
		return cpu;
	}
	
	public void setCpu(double cpu){
		this.cpu = cpu;
	}
	
	public double getRam(){
		return ram;
	}
	
	public void setRam(double ram){
		this.ram = ram;
	}
	
	public double getHdd(){
		return hdd;
	}
	
	public void setHdd(double hdd){
		this.hdd = hdd;
	}
	
	public double getNet(){
		return net;
	}
	
	public void setNet(double net){
		this.net = net;
	}

	public NPC getOwner(){
		return owner;
	}

	public void setOwner(NPC owner){
		this.owner = owner;
	}
	
}
