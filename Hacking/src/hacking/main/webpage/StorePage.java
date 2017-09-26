package hacking.main.webpage;

import javafx.scene.web.WebEngine;

public class StorePage extends WebPage{

	//List of items
	
	public StorePage(){
		super("oldegg.com", "store", "OldEgg", "The Shopping place for all your electronic needs");
		// TODO Auto-generated constructor stub
	}
	
	public class StoreInterface extends WebInterface{
		public StoreInterface(WebEngine e){
			super(e);
		}

		public void print(String s){
			System.out.println("StorePage: " + s);
		}
	}

	public WebInterface getInterface(WebEngine e){
		return new StoreInterface(e);
	}
	
}
