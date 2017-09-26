package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import hacking.main.ReaperOS;
import javax.swing.GroupLayout.Alignment;

public class Designer extends JFrame{
	
	private JPanel contentPane;
	private JDesktopPane desktopPane;
	private int width = 600;
	private int height = 400;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		try{
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception ex){
			Logger.getLogger(ReaperOS.class.getName()).log(Level.SEVERE, null, ex);
		}
		
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
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		SetupInternalFrame();
		
		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Designer.class.getResource("/icons/Reaper.png")));
		menuBar.add(label);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		menuBar.add(horizontalStrut);
		
		JMenu mnNewMenu = new JMenu("Start");
		mnNewMenu.setFont(new Font("Data Control", Font.BOLD, 14));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmShutdown = new JMenuItem("ShutDown");
		mnNewMenu.add(mntmShutdown);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JMenu mnNewMenu_1 = new JMenu("");
		mnNewMenu_1.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu_1.setIcon(new ImageIcon(Designer.class.getResource("/icons/wifiicon.png")));
		menuBar.add(mnNewMenu_1);
		
		JPanel panel = new JPanel();
		mnNewMenu_1.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"OpenWifi", "NeighborsWifi", "Virus Infected", "NSA Van"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list, BorderLayout.NORTH);
		
		JButton btnDisconnect = new JButton("Disconnect");
		panel.add(btnDisconnect, BorderLayout.SOUTH);
		
		JMenu mnSound = new JMenu("");
		mnSound.setIcon(new ImageIcon(
				Designer.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaMuteDisabled.png")));
		mnSound.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mnSound);
		
		JSlider volumeSlider = new JSlider();
		volumeSlider.setPreferredSize(new Dimension(20, 200));
		volumeSlider.setOrientation(SwingConstants.VERTICAL);
		mnSound.add(volumeSlider);
	}

	public void SetupInternalFrame(){
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame", true, true, true, true);
		internalFrame.setBounds(42, 40, 379, 178);
		desktopPane.add(internalFrame);
		SetupProgramGUI(internalFrame);
		//BEGIN
		
		internalFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		internalFrame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("IP");
		
		textField = new JTextField();
		lblNewLabel.setLabelFor(textField);
		textField.setColumns(10);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		
		JButton btnNewButton_1 = new JButton("Crack");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		panel.add(lblNewLabel);
		panel.add(textField);
		panel.add(horizontalGlue);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		internalFrame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		panel_1.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		panel_1.add(btnLogin);
		
		JPanel panel1 = new JPanel();
		internalFrame.getContentPane().add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new BorderLayout(0, 0));
		
		JProgressBar progressBar = new JProgressBar();
		panel1.add(progressBar, BorderLayout.CENTER);
		
		JPanel panel_11 = new JPanel();
		panel1.add(panel_11, BorderLayout.NORTH);
		panel_11.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
		panel_11.add(lblStatus);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_11.add(horizontalGlue_1);
		
		JLabel lblCracking = new JLabel("Cracking...");
		panel_11.add(lblCracking);
		
		//END
		internalFrame.show();
	}

	private void SetupProgramGUI(JInternalFrame internalFrame){
		
	}
}
