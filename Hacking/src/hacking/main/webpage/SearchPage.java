package hacking.main.webpage;

import java.util.ArrayList;

import javafx.scene.web.WebEngine;

public class SearchPage extends WebPage{
	
	//List of webpages
	private ArrayList<WebPage> pages = new ArrayList<WebPage>();
	
	public SearchPage(){
		super("snooper.com", "search", "Snooper", "Snooping the web since 1986");
		pages.add(new StorePage());
		//pages.add(new SearchPage());
	}
	
	public class SearchInterface extends WebInterface{
		public SearchInterface(WebEngine e){
			super(e);
		}
		
		public void search(String term){
			if(term.equals("")) return;
			this.webEngine.executeScript("document.getElementById('results').innerHTML = ''");
			System.out.println("Searching for " + term);
			for(WebPage page: pages){
				if(page.getTitle().contains(term) || page.getDesc().contains(term)){
					System.out.println(page.getBlurb());
					this.webEngine.executeScript("document.getElementById('results').innerHTML += '" + page.getBlurb() +"'");
				}
			}
		}
	}

	@Override
	public WebInterface getInterface(WebEngine e){
		return new SearchInterface(e);
	}
	
}
