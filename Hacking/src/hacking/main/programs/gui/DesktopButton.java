package hacking.main.programs.gui;

import java.awt.Font;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import hacking.main.GUIGame;

public class DesktopButton extends JLabel{

    private static int numOfButtons = 0;
    // TODO: add moving to new column when reaching the bottom of desktop
    
    private GUIProgram program;

    public DesktopButton(GUIProgram p, String name){
	super(name + ".exe");
	this.program = p;
	
	setHorizontalAlignment(SwingConstants.CENTER);
	setVerticalAlignment(SwingConstants.BOTTOM);
	setHorizontalTextPosition(SwingConstants.CENTER);
	setVerticalTextPosition(SwingConstants.BOTTOM);
	
	int offset = 32;
	int size = 72;
	int y = size * numOfButtons + offset;
	numOfButtons++;
	setLocation(offset, y);
	setSize(size, size);
	
	this.setFont(new Font("Default", Font.PLAIN, 9));
	
	URL path = GUIGame.class.getResource("/"+name.toLowerCase() + ".png");
	//System.out.println(path);
	setIcon(new ImageIcon(path));

	// addListeners
	addMouseListener(new ClickHandler());
	addMouseMotionListener(new DragHandler());
    }

    private class ClickHandler extends MouseAdapter{
	@Override
	public void mouseClicked(MouseEvent e){
	    if(e.getClickCount() > 1){
		program.open();
	    }
	}
    }

    private class DragHandler extends MouseMotionAdapter{
	@Override
	public void mouseDragged(MouseEvent e){
	    int x = getX() + e.getX();
	    int y = getY() + e.getY();
	    setLocation(x - getWidth() / 2, y - getHeight() / 2);
	}
    }
}
