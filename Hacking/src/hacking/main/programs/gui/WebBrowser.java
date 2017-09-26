package hacking.main.programs.gui;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

import hacking.main.ReaperOS;
import hacking.main.webpage.*;
import hacking.main.webpage.WebPage.WebInterface;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebBrowser extends GUIProgram{
	
	private JTextField urlBar;
	private WebView browser;
	private WebEngine webEngine;
	private WebPage currentCustom;
	private HashMap<String,WebPage> pages;
	private final String Error404 = "<p> </p><h1 style=\"text-align: center;\">ERROR 404</h1><p> </p>"
			+ "<h2 style=\"text-align: center;\">Page Not Found</h2><p style=\"text-align: center;\">"
			+ "Sorry, but the page you were trying to view does not exist.</p>";
	// add back and forth buttons
	// add refresh
	// add tabs
	
	public WebBrowser(ReaperOS os, ImageIcon icon, int width, int height){
		super(os, "WebBrowser", icon, width, height);
		urlBar = new JTextField();
		urlBar.addActionListener(new UrlBarHandler());
		getContentPane().add(urlBar, BorderLayout.NORTH);
		
		pages = new HashMap<String,WebPage>();
		pages.put("https://www.oldegg.com", new StorePage());
		pages.put("https://www.snooper.com", new SearchPage());
		
		JFXPanel panel = new JFXPanel();
		createScene(panel, os);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(final ComponentEvent e){
				super.componentResized(e);
				browser.setMinSize(panel.getWidth(), panel.getHeight());
			}
		});
	}
	
	@Override
	public void close(){
		super.close();
		loadPage("http://www.google.com");// start page
	}
	
	public void loadCustomPage(String url){
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				
				System.out.println("Loading " + url);
				
				currentCustom = pages.get(url);
				String html = currentCustom.getHtml();
				String css = currentCustom.getCss();
                
				if(html != null){
					webEngine.load(html);
				}else{
					webEngine.loadContent(Error404);
				}
				if(css != null){
					webEngine.setUserStyleSheetLocation(css);
				}
				
//				JSObject win = (JSObject) webEngine.executeScript("window");
//              win.setMember("app", new JavaInterface());
			}
		});
	}
	
	public void loadPage(String urlInput){
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				
				if(pages.containsKey(urlInput)){
					loadCustomPage(urlInput);
					return;
				}
				
				System.out.println("Loading " + urlInput);
				currentCustom = null;
				webEngine.load(urlInput);
			}
		});
	}
	
	private class UrlBarHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == urlBar){
				String parsed = urlBar.getText();
				
				if(!parsed.contains("www.")){
					parsed = "www." + parsed;
				}
				if(!parsed.contains("https://")){
					parsed = "https://" + parsed;
				}
				
				loadPage(parsed);
			}
		}
	}
	
	private void createScene(JFXPanel panel, ReaperOS os){
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				
				Stage stage = new Stage();
				
				stage.setTitle("Hello Java FX");
				stage.setResizable(true);
				
				Group root = new Group();
				Scene scene = new Scene(root, 80, 20);
				stage.setScene(scene);
				
				// Set up the embedded browser:
				browser = new WebView();
				browser.setPrefSize(panel.getWidth(), panel.getHeight());
				webEngine = browser.getEngine();
				webEngine.load("http://www.google.com");// start page
				webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
		            @Override
		            public void changed(ObservableValue<? extends State> ov, State t, State t1) {
		                if (t1 == State.FAILED) {
		                	System.out.println("Failed to load showing 404");
		                	webEngine.loadContent(Error404);
		                }
		                if(t1 == State.SUCCEEDED && currentCustom != null){
		                	JSObject win = (JSObject) webEngine.executeScript("window");
		                	WebInterface inter = currentCustom.getInterface(webEngine);
		                	if(inter != null) win.setMember("app", currentCustom.getInterface(webEngine));
		                }
		            }
		        });
				
				
				ObservableList<Node> children = root.getChildren();
				children.add(browser);
				
				panel.setScene(scene);
			}
		});
	}
	
}
