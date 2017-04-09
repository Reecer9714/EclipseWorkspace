package hacking.main.programs.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import hacking.main.ReaperOS;
import hacking.main.files.TextFile;

public class TextEditor extends GUIProgram{

    private JTextArea textArea;
    private TextFile currentFile;

    public TextEditor(ReaperOS os, ImageIcon icon, int width, int height){
	super(os, "TextEditor", icon, width, height);
	JMenuBar menuBar_1 = new JMenuBar();
	setJMenuBar(menuBar_1);

	JMenu mnFile = new JMenu("File");
	menuBar_1.add(mnFile);

	JMenuItem saveOption = new JMenuItem("Save");
	saveOption.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		save();
	    }
	});
	mnFile.add(saveOption);

	JMenuItem loadOption = new JMenuItem("Load");
	loadOption.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		// TODO; add popup to select file
		// load();
	    }
	});
	mnFile.add(loadOption);

	JScrollPane scrollPane = new JScrollPane();
	getContentPane().add(scrollPane, BorderLayout.CENTER);

	textArea = new JTextArea();
	scrollPane.setViewportView(textArea);

    }

    @Override
    public void close(){
	super.close();
	textArea.setText("");
	currentFile = null;
    }

    public void load(TextFile file){
	textArea.setText(file.getBody());
	currentFile = file;
    }

    public void save(){
	// TODO: create new TextFile and put in Folder
	if(currentFile == null){
	    // TODO: add popup to select where to save file and file name
	    // currentFile = new TextFile(os, title, null);
	}else{
	    currentFile.setBody(textArea.getText());
	}
    }

}
