package hacking.main.programs.gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import hacking.main.ReaperOS;

public class MyComputer extends GUIProgram{
	
	private JLabel lblCpu;
	private JLabel lblRam;
	private JLabel lblHdd;
	private JLabel lblPW;
	
	public MyComputer(ReaperOS os, ImageIcon icon, int width, int height){
		super(os, "MyComputer", icon, width, height);
		this.setMaximumSize(new Dimension(width, height));
		JLabel lblComputername = new JLabel(os.getGame().getMyComputer().getOwner().getName() + ":");
		lblCpu = new JLabel("CPU: " + os.getGame().getMyComputer().getCpu());
		lblRam = new JLabel("RAM: " + os.getGame().getMyComputer().getRam());
		lblHdd = new JLabel("HDD: " + os.getGame().getMyComputer().getHdd());
		lblPW = new JLabel("PW: " + os.getGame().getMyComputer().getPassword());
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblComputername))
						.addGroup(groupLayout.createSequentialGroup().addGap(26)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblRam)
										.addComponent(lblCpu).addComponent(lblHdd).addComponent(lblPW))))
				.addContainerGap(301, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblComputername)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblCpu)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblRam)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblHdd)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblPW)
						.addContainerGap(202, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}
	
	@Override
	public void open(){
		super.open();
		// update Labels
		lblCpu.setText("CPU: " + os.getGame().getMyComputer().getCpu());
		lblRam.setText("RAM: " + os.getGame().getMyComputer().getRam());
		lblHdd.setText("HDD: " + os.getGame().getMyComputer().getHdd());
		lblPW.setText("PW: " + os.getGame().getMyComputer().getPassword());
	}
	
}
