package hacking.main.internet;

import hacking.main.GUIGame;

public class IP{
	public String ip;
	
	public IP(String ip){
		this.ip = ip;
	}
	
	public static IP generateLocalIP(){
		String s = "";
		int first = GUIGame.getRan().nextInt(3);
		switch(first){
			case 0:
				s += "10.";
				s += GUIGame.getRan().nextInt(256) + ".";
				s += GUIGame.getRan().nextInt(256) + ".";
				break;
			case 1:
				s += "172.";
				s += (GUIGame.getRan().nextInt(16) + 16) + ".";
				s += GUIGame.getRan().nextInt(256) + ".";
				break;
			case 2:
				s += "192.168.";
				s += GUIGame.getRan().nextInt(256) + ".";
				break;
		}
		
		s+= "1";
		
		return new IP(s);
	}
	
	public static IP generatePublicIP(){
		String s = "";
		int first;
		do{
			first = GUIGame.getRan().nextInt(191)+1;
		}while(first == 10);
		
		int second;
		do{
			second = GUIGame.getRan().nextInt(256);
		}while(first == 172 && second == 16);
		
		s+= first + "." + second + ".";
		s+= GUIGame.getRan().nextInt(256) + ".";
		s+= GUIGame.getRan().nextInt(256);
		
		return new IP(s);
	}
	
	public String toString(){
		return ip;
	}
	
	// public static String ranIP(){
	// return (ran.nextInt(190) + 1) + "." + (ran.nextInt(254) + 1) + "." +
	// (ran.nextInt(254) + 1) + "."
	// + (ran.nextInt(254) + 1);
	// }
}
