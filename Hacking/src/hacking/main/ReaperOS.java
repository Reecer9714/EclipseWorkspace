package hacking.main;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import hacking.main.programs.gui.Terminal;

public class ReaperOS extends JFrame{

    private static final long serialVersionUID = 1L;
    private GUIGame game;

    /**
     * Creates new form ReaperOS
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
	
	//Setup Programs
	terminal = new Terminal(this, (int)(getWidth() * 0.5),  (int)(getHeight() * 0.5));

	pack();
    }

    public static void main(String args[]){
	try{
	    for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
		if("Nimbus".equals(info.getName())){
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	}
	catch(ClassNotFoundException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(InstantiationException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(IllegalAccessException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch(UnsupportedLookAndFeelException ex){
	    Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
	}

	/* Create and display the form */
	EventQueue.invokeLater(new Runnable(){
	    public void run(){
		GUIGame game = new GUIGame();
		ReaperOS os = new ReaperOS(game);
			 os.setVisible(true);
			 os.requestFocusInWindow();
	    }
	});
    }

    // Variables declaration
    private JDesktopPane desktop;
    private Terminal terminal;
    private JMenu startmenu;
    private JMenuBar menubar;

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
}
