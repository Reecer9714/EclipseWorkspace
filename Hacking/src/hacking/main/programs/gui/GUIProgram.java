package hacking.main.programs.gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import hacking.main.ReaperOS;

public abstract class GUIProgram extends JInternalFrame{
    protected ReaperOS os;
    private JButton barButton;
    protected JPanel panel;
    private boolean open;

    public GUIProgram(ReaperOS os, String title, ImageIcon icon, int width, int height){
	super(title, true, true, true, true);
	this.os = os;
	Dimension d = new Dimension(width, height);
	setPreferredSize(d);
	setMinimumSize(d);
	setSize(d);

	setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
	addInternalFrameListener(new InternalFrameAdapter(){
	    public void internalFrameClosing(InternalFrameEvent e){
		close();
	    }
	});
	// Menu Button
	/*
	barButton = new JButton(title);
	barButton.addMouseListener(new MouseAdapter(){
	    @Override
	    public void mouseClicked(MouseEvent e){
		open = !open;
		if(open){
		    open();
		    barButton.setBackground(new Color(242, 230, 170));
		}else{
		    close();
		    barButton.setBackground(new Color(238, 238, 238));
		}
	    }
	});
	os.getMenubar().add(barButton);
	*/
	
	DesktopButton button = new DesktopButton(this, title, icon);
	os.getDesktop().add(button);

	os.getDesktop().add(this);
    }

    public void open(){
	open = true;
	this.show();
    }

    public void close(){
	open = false;
	this.hide();
    }

}
