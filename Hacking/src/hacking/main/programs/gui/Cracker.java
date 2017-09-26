package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import hacking.main.GUIGame;
import hacking.main.ReaperOS;
import hacking.main.mail.*;

public class Cracker extends GUIProgram{
	
	private JTextField fieldIP;
	private JTextField fieldPassword;
	private GUIGame game;
	private JLabel status;
	private JProgressBar progressBar;
	
	public Cracker(ReaperOS os, ImageIcon icon, int width, int height){
		super(os, "Cracker", icon, width, height);
		this.game = os.getGame();
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		getContentPane().add(panelNorth, BorderLayout.NORTH);
		
		JLabel lblIP = new JLabel("IP");
		
		fieldIP = new JTextField();
		fieldIP.setColumns(10);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		
		JButton buttonCracker = new JButton("Crack");
		buttonCracker.addActionListener(new ActionListener(){
			private Timer crackTimer;

			@Override
			public void actionPerformed(ActionEvent e){
				String text = fieldIP.getText();
				progressBar.setValue(0);
				setStatus("Connecting");
				boolean connectStatus;
				if(game.getConnectedComp().getIp().equals(text)){
					connectStatus = true;
				}else{
					connectStatus = os.getTerminal().connect(text);
				}
				if(connectStatus){
					setStatus("Cracking");
					crackTimer = new Timer(100, new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e){
							progressBar.setValue(progressBar.getValue()+1);//Change to dynamic value
							if(progressBar.getValue() >= 100){
								crackTimer.stop();
								fieldPassword.setText(game.getConnectedComp().getPassword());
								setStatus("Complete");
							}
						}
					});
					crackTimer.start();
				}else{
					setStatus("Failed to Connect");
				}
				
			}
		});
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelNorth.add(horizontalStrut);
		panelNorth.add(lblIP);
		panelNorth.add(fieldIP);
		panelNorth.add(horizontalGlue);
		panelNorth.add(buttonCracker);
		
		JPanel panelSouth = new JPanel();
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.X_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelSouth.add(horizontalStrut_1);
		
		JLabel lblPassword = new JLabel("Password");
		panelSouth.add(lblPassword);
		
		fieldPassword = new JTextField();
		fieldPassword.setEditable(false);
		panelSouth.add(fieldPassword);
		fieldPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(os.getTerminal().login(fieldPassword.getText())){
					setStatus("Logged In");
					os.getTerminal().open();
				}else{
					setStatus("Password Failed");
				}
			}
		});
		panelSouth.add(btnLogin);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		progressBar = new JProgressBar();
		panelCenter.add(progressBar, BorderLayout.CENTER);
		
		JPanel panelStatus = new JPanel();
		panelCenter.add(panelStatus, BorderLayout.NORTH);
		panelStatus.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
		panelStatus.add(lblStatus);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelStatus.add(horizontalGlue_1);
		
		status = new JLabel("Waiting");
		panelStatus.add(status);
		
		this.setResizable(false);
	}
	
	@Override
	public void close(){
		super.close();
		progressBar.setValue(0);
		setStatus("Waiting");
		fieldIP.setText("");
		fieldPassword.setText("");
	}
	
	private void setStatus(String s){
		status.setText(s);
	}
	
	private String getStatus(){
		return status.getText();
	}
	
//	public String crack(String ip){
//		
//	}
	
}
