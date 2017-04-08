package hacking.main.programs.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import hacking.main.*;

public class FileBrowser extends GUIProgram{

    private ImageIcon icon;

    public FileBrowser(ReaperOS os, int width, int height){
	super(os, "FileBrowser", width, height);
	JTree tree = new JTree(os.getGame().getMyComputer().getFileRoot());
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

    @Override
    public ImageIcon getIcon(){
	return icon;
    }

}
