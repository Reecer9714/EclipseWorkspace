package hacking.main.programs.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javafx.embed.swing.JFXPanel;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;

public class Designer extends JFrame{

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args){
	EventQueue.invokeLater(new Runnable(){
	    public void run(){
		try{
		    Designer frame = new Designer();
		    frame.setVisible(true);
		}
		catch(Exception e){
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public Designer(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 757, 517);
	contentPane = new JPanel();
	contentPane.setBorder(null);
	contentPane.setLayout(new BorderLayout(0, 0));
	setContentPane(contentPane);

	JDesktopPane desktopPane = new JDesktopPane();
	desktopPane.setBackground(Color.LIGHT_GRAY);
	contentPane.add(desktopPane, BorderLayout.CENTER);

	JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
	internalFrame.setBounds(42, 28, 379, 296);
	desktopPane.add(internalFrame);
	internalFrame.getContentPane().setLayout(new BorderLayout(0, 0));
	
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	tabbedPane.setBackground(Color.GRAY);
	internalFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	
	JList list_1 = new JList();
	tabbedPane.addTab("CPUs", null, list_1, null);
	
	JList list_2 = new JList();
	tabbedPane.addTab("Ram", null, list_2, null);
	
	JList list_3 = new JList();
	tabbedPane.addTab("HDDs", null, list_3, null);
	
	JList list = new JList();
	internalFrame.getContentPane().add(list, BorderLayout.EAST);
	
	JPanel panel = new JPanel();
	panel.setBorder(new EmptyBorder(5, 5, 0, 10));
	panel.setBackground(Color.GRAY);
	internalFrame.getContentPane().add(panel, BorderLayout.SOUTH);
	panel.setLayout(new BorderLayout(0, 0));
	
	JButton btnNewButton_1 = new JButton("BUY");
	panel.add(btnNewButton_1, BorderLayout.CENTER);
	btnNewButton_1.setForeground(new Color(0, 128, 0));
	btnNewButton_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
	btnNewButton_1.setBackground(Color.GRAY);
	
	JLabel lblNewLabel_1 = new JLabel("$500");
	lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
	lblNewLabel_1.setForeground(new Color(0, 128, 0));
	panel.add(lblNewLabel_1, BorderLayout.EAST);
	
	JPanel panel_1 = new JPanel();
	panel_1.setBackground(new Color(128, 128, 128));
	internalFrame.getContentPane().add(panel_1, BorderLayout.NORTH);
	
	JLabel lblOldegg = new JLabel("OldEgg");
	panel_1.add(lblOldegg);
	lblOldegg.setBackground(SystemColor.textInactiveText);
	lblOldegg.setForeground(new Color(255, 140, 0));
	lblOldegg.setFont(new Font("Terminator Two", Font.PLAIN, 18));
	lblOldegg.setHorizontalAlignment(SwingConstants.CENTER);
	internalFrame.setVisible(true);

	JMenuBar menuBar = new JMenuBar();
	contentPane.add(menuBar, BorderLayout.SOUTH);

	JMenu mnNewMenu = new JMenu("Start");
	menuBar.add(mnNewMenu);

	JButton btnNewButton = new JButton("");
	btnNewButton.setIcon(new ImageIcon(Designer.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")));
	btnNewButton.setToolTipText("Terminal");
	menuBar.add(btnNewButton);

	Component horizontalGlue = Box.createHorizontalGlue();
	menuBar.add(horizontalGlue);

	JMenu mnSound = new JMenu("");
	mnSound.setIcon(new ImageIcon(
		Designer.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaMuteDisabled.png")));
	mnSound.setHorizontalAlignment(SwingConstants.LEFT);
	menuBar.add(mnSound);

	JLabel lblNewLabel = new JLabel("Sound");
	mnSound.add(lblNewLabel);

	JSlider volumeSlider = new JSlider();
	volumeSlider.setPreferredSize(new Dimension(20, 200));
	volumeSlider.setOrientation(SwingConstants.VERTICAL);
	mnSound.add(volumeSlider);
    }
}
