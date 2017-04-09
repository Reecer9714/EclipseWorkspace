package hacking.main;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import hacking.main.programs.gui.*;

public class ReaperOS extends JFrame{

    private static final long serialVersionUID = 1L;
    private GUIGame game;
    private JDesktopPane desktop;
    private JMenu startmenu;
    private JMenuBar menubar;
    private Terminal terminal;
    private WebBrowser webbrowser;
    private FileBrowser filebrowser;
    private TextEditor texteditor;
    private MailBrowser mailbox;
    public ImageIcon readMail;
    public ImageIcon unreadMail;
    /**
     * Creates new form ReaperOS
     * icons https://www.iconfinder.com/iconsets/onebit
     */
    public ReaperOS(GUIGame game){
	this.game = game;
	initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents(){
	setFocusable(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	setMinimumSize(new Dimension(720, 640));
	setPreferredSize(new Dimension(1080, 720));
	
	readMail = createImageIcon("/readMail.png");
	unreadMail = createImageIcon("/unreadMail.png");

	desktop = new JDesktopPane();
	menubar = new JMenuBar();
	startmenu = new JMenu();

	getContentPane().add(desktop, BorderLayout.CENTER);
	setJMenuBar(menubar);
	startmenu.setText("Start");
	menubar.add(startmenu);

	// Setup Programs
	terminal = new Terminal(this, createImageIcon("/terminal.png"), (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	webbrowser = new WebBrowser(this, createImageIcon("/webbrowser.png"), (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	filebrowser = new FileBrowser(this, createImageIcon("/filebrowser.png"), (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	texteditor = new TextEditor(this, createImageIcon("/texteditor.png"), (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	mailbox = new MailBrowser(this, createImageIcon("/mailbox.png"), (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));

	pack();
    }
    
    //Helper
    public ImageIcon createImageIcon(String path){
	URL imgURL = getClass().getResource(path);
	if(imgURL != null){
	    return new ImageIcon(imgURL);
	}else{
	    System.err.println("Couldn't find file: " + path);
	    return null;
	}
    }

    //Gets and Sets
    public JMenuBar getMenubar(){
	return menubar;
    }

    public JDesktopPane getDesktop(){
	return desktop;
    }

    public JMenu getStartmenu(){
	return startmenu;
    }

    public GUIGame getGame(){
	return game;
    }
    
    public Terminal getTerminal(){
        return terminal;
    }

    public WebBrowser getWebbrowser(){
        return webbrowser;
    }

    public FileBrowser getFilebrowser(){
        return filebrowser;
    }

    public TextEditor getTexteditor(){
        return texteditor;
    }

    public MailBrowser getMailbox(){
        return mailbox;
    }

}
