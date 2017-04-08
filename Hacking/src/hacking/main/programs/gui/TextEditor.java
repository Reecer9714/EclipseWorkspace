package hacking.main.programs.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import hacking.main.GUIGame;
import hacking.main.ReaperOS;
import hacking.main.files.TextFile;

public class TextEditor extends GUIProgram{

    private JTextArea textArea;
    private ImageIcon icon;

    public TextEditor(ReaperOS os, int width, int height){
	super(os, "TextEditor", width, height);
	JMenuBar menuBar_1 = new JMenuBar();
	setJMenuBar(menuBar_1);
	
	JMenu mnFile = new JMenu("File");
	menuBar_1.add(mnFile);
	
	JMenuItem mntmSave = new JMenuItem("Save");
	mnFile.add(mntmSave);
	
	JMenuItem mntmLoad = new JMenuItem("Load");
	mnFile.add(mntmLoad);
	
	JScrollPane scrollPane = new JScrollPane();
	getContentPane().add(scrollPane, BorderLayout.CENTER);
	
	textArea = new JTextArea();
	scrollPane.setViewportView(textArea);
	
	icon = new ImageIcon(GUIGame.class.getResource("/texteditor.png"), "Desktop Icon");
    }
    
    public void load(TextFile file){
	textArea.setText(file.getContents());
    }
    
    public void save(){
	//TODO: create new TextFile and put in Folder
    }

    @Override
    public ImageIcon getIcon(){
	return icon;
    }

}
