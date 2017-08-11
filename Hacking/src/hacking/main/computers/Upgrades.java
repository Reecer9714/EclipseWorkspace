package hacking.main.computers;

import java.util.HashMap;
import java.util.Vector;

public class Upgrades{
    public static final int CPU = 0;
    public static final int RAM = 1;
    public static final int HDD = 2;

    public static HashMap<Integer, Vector<Upgrade>> items = new HashMap<Integer, Vector<Upgrade>>();
    private Vector<Upgrade> cpus = new Vector<Upgrade>();
    private Vector<Upgrade> rams = new Vector<Upgrade>();
    private Vector<Upgrade> hdds = new Vector<Upgrade>();

    public Upgrades(){
	items.put(CPU, cpus);
	items.put(RAM, rams);
	items.put(HDD, hdds);
    }

    public class Upgrade{

	private String name;
	private int price;
	private int performance;
	
	/**
	 * @param name
	 * @param price
	 * @param performance
	 */
	public Upgrade(String name, int price, int performance){
	    this.name = name;
	    this.price = price;
	    this.performance = performance;
	}

	public String getName(){
	    return name;
	}

	public int getPrice(){
	    return price;
	}

	public int getPerformance(){
	    return performance;
	}
	
	public String toString(){
	    return name + " - $" + price;
	}
    }
}
