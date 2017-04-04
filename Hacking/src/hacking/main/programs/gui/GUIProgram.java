package hacking.main.programs.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import hacking.main.ReaperOS;

public class GUIProgram extends JInternalFrame{
    
    private JButton icon;
    protected JPanel panel;
    private boolean open;
    
    public GUIProgram(ReaperOS os, String title, int width, int height){
	super(title, true, true, true, true);
	Dimension d = new Dimension(width, height);
	setPreferredSize(d);
	setMinimumSize(d);
	setSize(d);
	
	setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
        	close();
            }
        });
	//Menu Button
	icon = new JButton(title);
	icon.addMouseListener(new MouseAdapter(){
	    @Override
	    public void mouseClicked(MouseEvent e){
		open = !open;
		if(open){
		    open();
		    icon.setBackground(new Color(242, 230, 170));
		}else{
		    close();
		    icon.setBackground(new Color(238, 238, 238));
		}
	    }
	});

	os.getMenubar().add(icon);
	os.getDesktop().add(this);
	//open();
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
