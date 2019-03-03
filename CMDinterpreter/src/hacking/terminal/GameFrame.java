package hacking.terminal;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import hacking.Game;

public class GameFrame extends JFrame{
	
	private JTabbedPane tabbedPane;
	private static ConsolePanel[] tabs;
	private int maxTabs = 5;
	private Game game;
	private static Set<AWTKeyStroke> set = new HashSet<AWTKeyStroke>();
	
	public GameFrame(Game game){
		this.game = game;
		tabs = new ConsolePanel[maxTabs];
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//Remove current tab until one left then quit
				if(tabbedPane.getTabCount() > 1){
					tabbedPane.remove(tabbedPane.getSelectedIndex());
				}else{
					//properly disconnect
					Disconnect();
				}
				
				
			}
		});
		mnFile.add(mntmDisconnect);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue_2);
		
		JButton button = new JButton("New Tab");
		button.setFocusable(false);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				addConsoleTab();
			}
		});
		menuBar.add(button);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFocusable(false);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		//Add Initial Tab
		ConsolePanel newPanel = new ConsolePanel(game, 90,32);
		newPanel.setCursorBlink(false);
		newPanel.setCursorVisible(true);
		tabbedPane.addTab("New Tab", newPanel);
		tabs[0] = newPanel;
		newPanel.requestFocusInWindow();
		
		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
		setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
		
		pack();
	}
	
	private void addConsoleTab(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				int tabCount = tabbedPane.getTabCount();
				if(tabCount < maxTabs){
					ConsolePanel newPanel = new ConsolePanel(game, 90,32);
					newPanel.setCursorBlink(false);
					newPanel.setCursorVisible(true);
					tabbedPane.addTab("New Tab", newPanel);
					tabs[tabCount] = newPanel;
					tabbedPane.setSelectedIndex(tabCount);
					newPanel.BootUp();
				}
			}
		});
		
	}
	
	public JTabbedPane getTabbedPane(){
		return tabbedPane;
	}

	public static ConsolePanel[] getTabs(){
		return tabs;
	}

	public Game getGame(){
		return game;
	}

	private void Disconnect(){
		//Currently nothing needs proper disconnection
		System.exit(0);
	}
	
}
