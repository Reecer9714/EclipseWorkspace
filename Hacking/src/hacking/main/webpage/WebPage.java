package hacking.main.webpage;

import java.net.URL;

import hacking.main.GUIGame;
import javafx.scene.web.WebEngine;

public abstract class WebPage{
	
	private String html;
	private String css;
	private String url;
	private String title;
	private String desc;
	
	public WebPage(String url, String filename, String title, String desc){
		this.url = "https://www." + url;
		this.title = title;
		this.desc = desc;
		URL htmlFile = GUIGame.class.getResource("/webpages/" + filename + ".html");
		URL cssFile = GUIGame.class.getResource("/webpages/" + filename + ".css");
	
		if(htmlFile == null){
			html = null;
		}else{
			html = htmlFile.toString();
		}
		if(cssFile == null){
			css = null;
		}else{
			css = cssFile.toString();
		}
	}
	
	//JavaScript interface object
	public abstract class WebInterface{
		protected WebEngine webEngine;
		public WebInterface(WebEngine e){
			this.webEngine = e;
		}
	};
	
	public abstract WebInterface getInterface(WebEngine e);
	
	public String getBlurb(){
		return "<a href="+this.url+">"+this.title+"</a>"+
				"<p>"+this.desc+"</p>";
	}

	public String getHtml(){
		return html;
	}

	public void setHtml(String html){
		this.html = html;
	}

	public String getCss(){
		return css;
	}

	public void setCss(String css){
		this.css = css;
	}

	public String getUrl(){
		return url;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getTitle(){
		return title;
	}

	public String getDesc(){
		return desc;
	}
}
