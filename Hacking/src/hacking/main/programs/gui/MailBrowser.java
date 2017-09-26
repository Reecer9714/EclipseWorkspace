package hacking.main.programs.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import hacking.main.ReaperOS;
import hacking.main.mail.Mail;

public class MailBrowser extends GUIProgram{

    private DefaultListModel<Mail> mailListModel;
    private JList<Mail> mailList;

    public MailBrowser(ReaperOS os, ImageIcon icon, int width, int height){
	super(os, "MailBox", icon, width, height);

	JSplitPane splitPane = new JSplitPane();
	splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	getContentPane().add(splitPane, BorderLayout.CENTER);

	JScrollPane northScrollPane = new JScrollPane();
	splitPane.setRightComponent(northScrollPane);

	JTextArea mailText = new JTextArea();
	northScrollPane.setViewportView(mailText);

	JPanel panel = new JPanel();
	northScrollPane.setColumnHeaderView(panel);
	panel.setLayout(new BorderLayout(0, 0));

	JLabel from = new JLabel("From:");
	panel.add(from, BorderLayout.NORTH);

	JLabel subject = new JLabel("Subject:");
	panel.add(subject, BorderLayout.CENTER);

	JButton attach = new JButton("Download Attachment");
	attach.setVisible(false);
	panel.add(attach, BorderLayout.SOUTH);
	// TODO: add event listner to attachment button

	JScrollPane southScrollPane = new JScrollPane();
	splitPane.setLeftComponent(southScrollPane);

	mailListModel = new DefaultListModel<Mail>();
	mailList = new JList<Mail>(os.getGame().getMyComputer().getOwner().getMail());
	mailList.setModel(mailListModel);
	mailList.setVisibleRowCount(5);
	southScrollPane.setViewportView(mailList);

	mailList.addMouseListener(new MouseAdapter(){
	    public void mousePressed(MouseEvent e){
		int selRow = mailList.locationToIndex(e.getPoint());
		if(mailList.getCellBounds(selRow, selRow).contains(e.getPoint()) && e.getClickCount() == 2){
		    Mail selectedFile = (Mail)mailList.getSelectedValue();
		    mailText.setText(selectedFile.getBody());
		    from.setText("From: " + selectedFile.getFrom());
		    subject.setText("Subject: " + selectedFile.getSubject());
		    attach.setVisible(selectedFile.hasAttach());

		    selectedFile.setRead(true);
		    mailList.repaint();
		}
	    }
	});
	mailList.setCellRenderer(new DefaultListCellRenderer(){
	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
		    boolean cellHasFocus){
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if(((Mail)value).isRead()){
		    setIcon(os.readMail);
		}else{
		    setIcon(os.unreadMail);
		}
		return c;
	    }
	});

	/*
	 * maillistData.addListDataListener(new ListDataListener(){
	 * @Override public void intervalAdded(ListDataEvent e){
	 * if(tabbedPane.getSelectedIndex() != 1){ blinkTimer = new Timer(500,
	 * new ActionListener(){ boolean blinkFlag = false;
	 * @Override public void actionPerformed(ActionEvent e){
	 * blink(blinkFlag); blinkFlag = !blinkFlag; } }); blinkTimer.start(); }
	 * } private void blink(boolean blinkFlag){ if(blinkFlag){
	 * tabbedPane.setBackgroundAt(1, new Color(242, 230, 170));//170, 221,
	 * 242 blue }else{ tabbedPane.setBackgroundAt(1, defualtColor); }
	 * tabbedPane.repaint(); }
	 * @Override public void intervalRemoved(ListDataEvent e){}
	 * @Override public void contentsChanged(ListDataEvent e){} });
	 */
    }

    public DefaultListModel<Mail> getModel(){
	return mailListModel;
    }

}
