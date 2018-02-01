package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import hacking.main.ReaperOS;
import hacking.main.computers.Upgrades;
import hacking.main.computers.Upgrades.Upgrade;

public class Store extends GUIProgram{
    private JLabel moneyAmount;

    public Store(ReaperOS os, ImageIcon icon, int width, int height){
	super(os, "Computer Store", icon, width, height);
	
	JPanel northPanel = new JPanel();
	northPanel.setBackground(new Color(128, 128, 128));
	getFrame().getContentPane().add(northPanel, BorderLayout.NORTH);
	
	JLabel storeName = new JLabel("OldEgg");
	storeName.setBackground(SystemColor.textInactiveText);
	storeName.setForeground(new Color(255, 140, 0));
	storeName.setFont(new Font("Terminator Two", Font.PLAIN, 18));
	storeName.setHorizontalAlignment(SwingConstants.CENTER);
	northPanel.add(storeName);
	
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	tabbedPane.setBackground(Color.GRAY);
	getFrame().getContentPane().add(tabbedPane, BorderLayout.CENTER);
	
	JList<Upgrade> cpuTab = new JList<Upgrade>(Upgrades.items.get(Upgrades.CPU));
	tabbedPane.addTab("CPUs", null, cpuTab, null);
	
	JList<Upgrade> ramTab = new JList<Upgrade>(Upgrades.items.get(Upgrades.RAM));
	tabbedPane.addTab("Ram", null, ramTab, null);
	
	JList<Upgrade> hddTab = new JList<Upgrade>(Upgrades.items.get(Upgrades.HDD));
	tabbedPane.addTab("HDDs", null, hddTab, null);
	
	JPanel southPanel = new JPanel();
	southPanel.setBorder(new EmptyBorder(5, 5, 0, 10));
	southPanel.setBackground(Color.GRAY);
	southPanel.setLayout(new BorderLayout(0, 0));
	getFrame().getContentPane().add(southPanel, BorderLayout.SOUTH);
	
	JButton buyButton = new JButton("BUY");
	buyButton.setForeground(new Color(0, 128, 0));
	buyButton.setFont(new Font("Segoe UI Black", Font.PLAIN, 12));
	buyButton.setBackground(Color.GRAY);
	buyButton.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e){
		@SuppressWarnings("unchecked")
		JList<Upgrade> selectedTab = (JList<Upgrade>)tabbedPane.getSelectedComponent();
		Upgrade selectedUpgrade = selectedTab.getSelectedValue();
		if(selectedUpgrade == null) return;//Make sure something is selected
		
		// TODO: subtract from money
		//os.getGame().modifyMoney(-selectedUpgrade.getPrice()) check if going negative;
		//moneyAmount.setText("$"+os.getGame().getMoney());
		
		switch(tabbedPane.getSelectedIndex()){
		    case Upgrades.CPU: os.getGame().getMyComputer().setCpu(selectedUpgrade.getPerformance());
		    	break;
		    case Upgrades.RAM: os.getGame().getMyComputer().setRam(selectedUpgrade.getPerformance());
		    	break;
		    case Upgrades.HDD: os.getGame().getMyComputer().setHdd(selectedUpgrade.getPerformance());
		    	break;
		    default: break;
		}
	    }
	});
	southPanel.add(buyButton, BorderLayout.CENTER);
	
	moneyAmount = new JLabel("$500");
	moneyAmount.setFont(new Font("Segoe UI", Font.BOLD, 12));
	moneyAmount.setForeground(new Color(0, 128, 0));
	southPanel.add(moneyAmount, BorderLayout.EAST);
    }

}
