package hacking.main;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.tree.TreePath;

import hacking.main.files.File;
import hacking.main.mail.Mail;

public class Test extends JPanel{
    private static final long serialVersionUID = 1L;

    private static JTextArea cmdArea;
    private static JTextField cmdField;

    private static JProgressBar cpuUse;
    private static JProgressBar hddUse;
    private static JProgressBar netUse;

    private static JLabel cpuUseLbl;
    private static JLabel hddUseLbl;
    private static JLabel netUseLbl;
    
    public static ImageIcon readMail;
    public static ImageIcon unreadMail;

    private Set<AWTKeyStroke> set = new HashSet<AWTKeyStroke>();

    private JList<Mail> maillist;

    @SuppressWarnings("serial")
    public Test(Game game){
	
	readMail = createImageIcon("/readMail.png","Icon to represent an email that has been read");
	unreadMail = createImageIcon("/unreadMail.png", "Icon to represent an email that has not been read");
	// getContentPane().add(new Panel());
	this.setLayout(new BorderLayout(0, 0));

	JSplitPane splitPane = new JSplitPane();
	splitPane.setForeground(Color.GRAY);
	splitPane.setBackground(Color.DARK_GRAY);
	splitPane.setResizeWeight(1);
	this.add(splitPane);

	JPanel leftSide = new JPanel();
	splitPane.setLeftComponent(leftSide);
	leftSide.setLayout(new BorderLayout(0, 0));
	leftSide.setMinimumSize(new Dimension((int)(Game.width * 0.5), Game.height));
	// splitPane.setDividerLocation(0.5);

	JScrollPane scrollPane = new JScrollPane();
	leftSide.add(scrollPane, BorderLayout.CENTER);

	cmdArea = new JTextArea();
	cmdArea.setMinimumSize(new Dimension(4, 30));
	cmdArea.setMargin(new Insets(3, 3, 3, 3));
	scrollPane.setViewportView(cmdArea);
	cmdArea.setFont(new Font("Lucida Console", Font.PLAIN, 15));
	cmdArea.setEditable(false);
	cmdArea.setBackground(Color.BLACK);
	cmdArea.setForeground(Color.WHITE);

	cmdField = new JTextField();
	// Tabbed now does not change focus
	cmdField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
	cmdField.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
	cmdField.setMargin(new Insets(4, 4, 4, 4));
	cmdField.setMinimumSize(new Dimension(6, 25));
	cmdField.setFont(new Font("Lucida Console", Font.PLAIN, 13));
	cmdField.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		Game.handleInput(cmdField.getText());
		if(Game.connectedComp.getIp().equals(Game.myComputer.getIp())){
		    Game.messageOut("<" + Game.myComputer.getDir().getPath() + ">" + game.getLastCommand());
		}else{
		    Game.messageOut(
			    "<[" + Game.connectedComp.getIp() + "]:" + Game.connectedComp.getDir().getPath() + "> ");
		}
		// Game.messageOut(
		// "<" + Game.getConnected().getIp() + "-" +
		// Game.getConnected().getDir().getPath() + "> ");
		cmdField.setText("");
	    }
	});
	cmdField.setBackground(Color.BLACK);
	cmdField.setForeground(Color.WHITE);
	leftSide.add(cmdField, BorderLayout.SOUTH);

	JSplitPane rightSide = new JSplitPane();
	splitPane.setRightComponent(rightSide);
	rightSide.setOrientation(JSplitPane.VERTICAL_SPLIT);
	rightSide.setMinimumSize(new Dimension((int)(Game.width * 0.4), Game.height));

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
	rightSide.setRightComponent(tabbedPane);

	JPanel programs = new JPanel();
	tabbedPane.addTab("Running", null, programs, null);

	maillist = new JList<Mail>(Game.myComputer.getMail());
	maillist.setModel(Game.getMailModel());
	maillist.addMouseListener(new MouseAdapter(){
	    public void mousePressed(MouseEvent e){
		int selRow = maillist.locationToIndex(e.getPoint());
		if(maillist.getCellBounds(selRow, selRow).contains(e.getPoint()) && e.getClickCount() == 2){
		    Mail selectedFile = (Mail)maillist.getSelectedValue();
		    selectedFile.open();
		    maillist.repaint();
		}
	    }
	});
	maillist.setCellRenderer(new DefaultListCellRenderer(){
	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
		    boolean cellHasFocus){
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if(((Mail)value).isRead()){
		    setIcon(Test.readMail);
		}else{
		    setIcon(Test.unreadMail);
		}
		return c;
	    }
	});
	/*
	 * JTree Method maillist = new JTree(Game.getMailModel());
	 * maillist.setRootVisible(false); maillist.setCellRenderer(new
	 * DefaultTreeCellRenderer(){ private Icon readIcon =
	 * UIManager.getIcon("FileChooser.newFolderIcon"); private Icon
	 * unreadIcon = UIManager.getIcon("FileChooser.upFolderIcon");
	 * @Override public Component getTreeCellRendererComponent(JTree tree,
	 * Object value, boolean selected, boolean expanded, boolean isLeaf, int
	 * row, boolean focused){ Component c =
	 * super.getTreeCellRendererComponent(tree, value, selected, expanded,
	 * isLeaf, row, focused); if(((Mail)value).isRead()) setIcon(readIcon);
	 * else setIcon(unreadIcon); return c; } });
	 * maillist.addMouseListener(new MouseAdapter(){ public void
	 * mousePressed(MouseEvent e){ int selRow =
	 * maillist.getRowForLocation(e.getX(), e.getY()); TreePath selPath =
	 * maillist.getPathForLocation(e.getX(), e.getY()); if(selRow != -1 &&
	 * e.getClickCount() == 2 && selPath != null){ Mail selectedFile =
	 * (Mail)selPath.getLastPathComponent(); if(selectedFile.isLeaf()){
	 * selectedFile.open(); } } } });
	 */
	tabbedPane.addTab("Mail", null, maillist, null);

	JTree files = game.getTree();
	files.setRootVisible(false);
	files.addMouseListener(new MouseAdapter(){
	    public void mousePressed(MouseEvent e){
		int selRow = files.getRowForLocation(e.getX(), e.getY());
		TreePath selPath = files.getPathForLocation(e.getX(), e.getY());
		if(selRow != -1 && e.getClickCount() == 2 && selPath != null){
		    File selectedFile = (File)selPath.getLastPathComponent();
		    if(selectedFile.isLeaf()){
			selectedFile.open();
		    }
		}
	    }
	});
	tabbedPane.addTab("Files", null, files, null);

	JPanel resources = new JPanel();
	rightSide.setLeftComponent(resources);
	resources.setLayout(new GridLayout(6, 1, 0, 0));

	cpuUseLbl = new JLabel("CPU Use");
	resources.add(cpuUseLbl);

	cpuUse = new JProgressBar();
	resources.add(cpuUse);

	hddUseLbl = new JLabel("HDD Use");
	resources.add(hddUseLbl);

	hddUse = new JProgressBar();
	resources.add(hddUse);

	netUseLbl = new JLabel("NET Use");
	resources.add(netUseLbl);

	netUse = new JProgressBar();
	resources.add(netUse);

	JMenuBar menuBar = new JMenuBar();
	this.add(menuBar, BorderLayout.NORTH);

	JMenu menu = new JMenu("Start");
	menuBar.add(menu);

	JMenuItem mntmNewMenuItem = new JMenuItem("Shutdown");
	mntmNewMenuItem.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		System.exit(0);
	    }
	});
	mntmNewMenuItem
		.setIcon(new ImageIcon(Test.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
	menu.add(mntmNewMenuItem);

	JSeparator separator = new JSeparator();
	menuBar.add(separator);
    }

    protected ImageIcon createImageIcon(String path, String description){
	java.net.URL imgURL = getClass().getResource(path);
	if(imgURL != null){
	    return new ImageIcon(imgURL, description);
	}else{
	    System.err.println("Couldn't find file: " + path);
	    return null;
	}
    }

    public JProgressBar getCpuProgress(){
	return cpuUse;
    }

    public JProgressBar getHddProgress(){
	return hddUse;
    }

    public JProgressBar getNetProgress(){
	return netUse;
    }

    public JLabel getCpuLbl(){
	return cpuUseLbl;
    }

    public JLabel getHddLbl(){
	return hddUseLbl;
    }

    public JLabel getNetLbl(){
	return netUseLbl;
    }

    public JTextArea getConsole(){
	return cmdArea;
    }

    public JTextField getInputField(){
	return cmdField;
    }

    public JList<Mail> getMaillist(){
	return maillist;
    }

}
