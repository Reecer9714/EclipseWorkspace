package hacking.main;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import hacking.main.programs.Program;
import hacking.main.programs.Terminal;

public class ReaperOS extends JFrame{

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form ReaperOS
     */
    public ReaperOS(){
	initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * https://docs.oracle.com/javase/tutorial/uiswing/components/internalframe.
     * html
     */
    private void initComponents(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	setMinimumSize(new Dimension(720, 640));
	setPreferredSize(new Dimension(1080, 720));

	desktop = new JDesktopPane();
	menubar = new JMenuBar();
	startmenu = new JMenu();
	terminal = new Terminal(this, (int)(desktop.getWidth() * 0.5),  (int)(desktop.getHeight() * 0.5));

	// desktop.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	// desktop.setLayout(new FlowLayout());

	getContentPane().add(desktop, BorderLayout.CENTER);

	setJMenuBar(menubar);
	startmenu.setText("Start");
	menubar.add(startmenu);

	pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
	/* Set the Nimbus look and feel */
	// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
	// code (optional) ">
	/*
	 * If Nimbus (introduced in Java SE 6) is not available, stay with the
	 * default look and feel. For details see
	 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
	 * html
	 */
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
		new ReaperOS().setVisible(true);
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
}
