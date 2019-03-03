package hacking;

import java.awt.Insets;

import javax.swing.*;

import hacking.files.FileSystem;
import hacking.terminal.GameFrame;

public class Game{
	
	private static GameFrame frame;
	private static FileSystem fs;
	
	public static void main(String[] args){
		
		UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(10, 300, 20, 20));
		Game game = new Game();
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				System.out.println("Test");
				try{
					Thread.sleep(3000);
				}
				catch(InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "Test");
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				
				frame = new GameFrame(game);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setResizable(true);
				//frame.setSize(1080, 720);
				frame.setTitle("ReaperOS Terminal");
				frame.setVisible(true);
				frame.getTabbedPane().requestFocusInWindow();
				start();
			}
		});
	}
	
	public Game(){
		//Before Swing
		System.out.println("Before");
		fs = new FileSystem(this, "C");
		
		//Start kernal Thread

	}
	
	public static void start(){
		System.out.println("After");
		GameFrame.getTabs()[0].BootUp();	
	}
	
	public GameFrame getFrame(){
		return frame;
	}

	public FileSystem getFileSystem(){
		return fs;
	}
}
