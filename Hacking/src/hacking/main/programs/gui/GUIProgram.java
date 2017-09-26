package hacking.main.programs.gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import hacking.main.ReaperOS;
import hacking.main.files.Program;

public abstract class GUIProgram extends JInternalFrame{
	private static final long serialVersionUID = -1395930983604961532L;
	protected ReaperOS os;
	private Program exe;
	
	public GUIProgram(ReaperOS os, String title, ImageIcon icon, int width, int height){
		super(title, true, true, true, true);
		exe = new Program(os.getGame(), this, title);
		os.getGame().getMyComputer().getDir().getFolder("Programs").addFile(exe);
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
		
		DesktopButton button = new DesktopButton(this, title, icon);
		os.getDesktop().add(button);
		
		os.getDesktop().add(this);
	}
	
	public void open(){
		this.show();
	}
	
	public void close(){
		this.hide();
	}
	
}
