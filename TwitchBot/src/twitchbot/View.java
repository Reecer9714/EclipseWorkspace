package twitchbot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private TwitchBot bot;
	/**
	 * Creates new form TwitchBot
	 */
	public View(TwitchBot bot) {
		this.bot = bot;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jSplitPane1 = new JSplitPane();
		chatPanel = new JPanel();
		leftScrollPane = new JScrollPane();
		rightScrollPane = new JScrollPane();
		chatArea = new JTextArea();
		inputLine = new JTextField();
		viewerPanel = new JPanel();
		viewerArea = new JTextArea();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		BorderLayout layout = new BorderLayout();
		getContentPane().setLayout(layout);

		jSplitPane1.setDividerLocation(350);
		jSplitPane1.setResizeWeight(0.75);
		jSplitPane1.setMinimumSize(new Dimension(100, 200));
		jSplitPane1.setPreferredSize(new Dimension(500, 800));

		chatPanel.setLayout(new BorderLayout());

		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		viewerArea.setEditable(false);
		// jTextArea1.setColumns(20);
		// jTextArea1.setRows(5);
		leftScrollPane.setViewportView(chatArea);
		rightScrollPane.setViewportView(viewerArea);
		
		inputLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputLine.getText().charAt(0)=='/'){ 
					bot.handleInput(inputLine.getText());
					getChat().append("Command: "+inputLine.getText()+"\n");
				}else{
					bot.sendMessage(bot.currentChannel,""+inputLine.getText());
					getChat().append("Reecer9714: "+inputLine.getText()+"\n");
				}
				inputLine.setText("");
			}
		});
		
		chatPanel.add(leftScrollPane, BorderLayout.CENTER);
		chatPanel.add(inputLine, BorderLayout.PAGE_END);
		viewerPanel.add(rightScrollPane);

		jSplitPane1.setLeftComponent(chatPanel);

		viewerPanel.setLayout(new GridLayout());
		jSplitPane1.setRightComponent(viewerPanel);

		getContentPane().add(jSplitPane1);

		pack();
	}// </editor-fold>

	public JTextArea getChat() {
		return chatArea;
	}
	
	public JTextArea getViewer() {
		return viewerArea;
	}

	// Variables declaration
	private JPanel chatPanel;
	private JPanel viewerPanel;
	private JScrollPane leftScrollPane;
	private JScrollPane rightScrollPane;
	private JSplitPane jSplitPane1;
	private JTextArea chatArea;
	private JTextArea viewerArea;
	private JTextField inputLine;
	// End of variables declaration
}