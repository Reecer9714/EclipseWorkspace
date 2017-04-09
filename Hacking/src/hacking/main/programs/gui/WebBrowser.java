package hacking.main.programs.gui;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

import com.sun.javafx.application.PlatformImpl;

import hacking.main.GUIGame;
import hacking.main.ReaperOS;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowser extends GUIProgram{

    private JTextField urlBar;
    private WebView browser;
    private WebEngine webEngine;
    //add back and forth buttons
    //add refresh

    public WebBrowser(ReaperOS os, ImageIcon icon, int width, int height){
	super(os, "WebBrowser", icon, width, height);
	urlBar = new JTextField();
	urlBar.addActionListener(new UrlBarHandler());
	getContentPane().add(urlBar, BorderLayout.NORTH);

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

    public void loadCustomPage(String HTMLString){
	Platform.runLater(new Runnable(){
	    @Override
	    public void run(){
		webEngine.loadContent(HTMLString);
	    }
	});
    }

    public void loadPage(String urlInput){
	Platform.runLater(new Runnable(){
	    @Override
	    public void run(){
		//TODO: Add parsing on input text to allow the omission of http and www.
		webEngine.load(urlInput);
	    }
	});
    }

    private class UrlBarHandler implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
	    if(e.getSource() == urlBar){
		loadPage(urlBar.getText());
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

		ObservableList<Node> children = root.getChildren();
		children.add(browser);

		panel.setScene(scene);
	    }
	});
    }

}
