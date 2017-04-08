package hacking.main;

import java.awt.*;

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

	desktop = new JDesktopPane();
	menubar = new JMenuBar();
	startmenu = new JMenu();

	getContentPane().add(desktop, BorderLayout.CENTER);
	setJMenuBar(menubar);
	startmenu.setText("Start");
	menubar.add(startmenu);

	// Setup Programs
	terminal = new Terminal(this, (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	webbrowser = new WebBrowser(this, (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	filebrowser = new FileBrowser(this, (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
	texteditor = new TextEditor(this, (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));

	pack();
    }

    // End of variables declaration
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
    
    public ImageIcon createImageIcon(String path, String description){
	java.net.URL imgURL = getClass().getResource(path);
	if(imgURL != null){
	    return new ImageIcon(imgURL, description);
	}else{
	    System.err.println("Couldn't find file: " + path);
	    return null;
	}
    }
}
