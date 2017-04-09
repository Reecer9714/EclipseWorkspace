package hacking.main.programs.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.tree.TreePath;

import hacking.main.*;
import hacking.main.files.File;

public class FileBrowser extends GUIProgram{

    public FileBrowser(ReaperOS os, ImageIcon icon, int width, int height){
	super(os, "FileBrowser", icon, width, height);
	JTree tree = new JTree(os.getGame().getMyComputer().getFileRoot());
	tree.setRootVisible(false);
	tree.addMouseListener(new MouseAdapter(){
	    public void mousePressed(MouseEvent e){
		int selRow = tree.getRowForLocation(e.getX(), e.getY());
		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
		if(selRow != -1 && e.getClickCount() == 2 && selPath != null){
		    File selectedFile = (File)selPath.getLastPathComponent();
		    if(selectedFile.isLeaf()){
			selectedFile.open();
		    }
		}
	    }
	});
	getContentPane().add(tree, BorderLayout.CENTER);
	
	JMenuBar menuBar_1 = new JMenuBar();
	setJMenuBar(menuBar_1);
	
	JMenu mnFile = new JMenu("File");
	menuBar_1.add(mnFile);
	
	JMenuItem mntmNewFile = new JMenuItem("New File");
	mnFile.add(mntmNewFile);
	
	JMenuItem mntmNewFolder = new JMenuItem("New Folder");
	mnFile.add(mntmNewFolder);
    }

}
