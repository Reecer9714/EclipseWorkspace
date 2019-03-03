package hacking.main.programs.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import hacking.main.ReaperOS;
import hacking.main.files.Program;

public abstract class GUIProgram{
	protected ReaperOS os;
	protected Program program;
	private JInternalFrame pane;
	
	public GUIProgram(ReaperOS os, String title, ImageIcon icon, int width, int height){
		pane = new JInternalFrame(title, true, true, true, true);
		//os.getGame().getMyComputer().getMainDrive().getDir().getFolder("Programs").addFile(p);
		this.os = os;
		Dimension d = new Dimension(width, height);
		pane.setPreferredSize(d);
		pane.setMinimumSize(d);
		pane.setSize(d);
		
		pane.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		pane.addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameClosing(InternalFrameEvent e){
				close();
			}
		});
		
		DesktopButton button = new DesktopButton(this, title, icon);
		os.getDesktop().add(button);
		
		os.getDesktop().add(pane);
	}
	
	public JInternalFrame getFrame(){
		return pane;
	}
	
	public void open(){
		pane.show();
	}
	
	public void close(){
		pane.hide();
	}
	
	public void run(){
		open();
	}
}
