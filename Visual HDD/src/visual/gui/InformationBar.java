package visual.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import visual.data.Node;
import visual.main.VisualHDD;

public class InformationBar extends JButton implements ActionListener{	
	
	private static final long serialVersionUID = 1L;
	private static String text = "Please select a partition you wish to scan";
	private static int scanPartition = -1;
	private static String scanDrive;
	
	public InformationBar(){
		
		Dimension d = new Dimension(400, 100);	
		
		this.setText(text);
		
		this.setFocusable(false);
		this.setEnabled(false);	
		this.setOpaque(true);	
		
		this.setPreferredSize(d);
			
		this.setForeground(Color.blue);
		this.addActionListener(this);
			
		this.setVisible(true);		
	}
	
	public static void notify(String drive, int partition){
		scanPartition = partition;
		scanDrive = drive;
		text = "Click here to scan " + scanDrive;		
	}
	
	public Node update(){
		if(text.startsWith("Scan of")==true){
			if(ProgramWindow.isScanning()==false && ProgramWindow.getCanceled()==false){
				JOptionPane.showMessageDialog(new JFrame(), "Partition Scan Complete", "Complete", JOptionPane.INFORMATION_MESSAGE);
				text = "Scan Complete, Click Again to Rescan or Select a New Partition";
				this.setText(text);	
				this.setBackground(Color.green);
				this.setEnabled(true);
			}
			
			else if(ProgramWindow.isScanning()==false && ProgramWindow.getCanceled()==true){
				JOptionPane.showMessageDialog(new JFrame(), "Partition Scan Complete", "Complete", JOptionPane.INFORMATION_MESSAGE);
				text = "Scan Canceled, Click Again to Rescan or Select a New Partition";
				this.setText(text);	
				this.setBackground(Color.red);
				this.setEnabled(true);
			}
		}		
				
		else if(text != "Please select a partition you wish to scan"){
			this.setText(text);		
			this.setEnabled(true);
			this.setBackground(Color.green);
		}
		return VisualHDD.getTree(scanPartition).retrieveNode(scanDrive);
	}
	
	public static int getPartition(){
		return scanPartition;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		text = "Scan of "+ scanDrive + " in progress";
		this.setText(text);	
		this.setBackground(Color.yellow);
		this.setEnabled(false);
		
		VisualHDD.getInstance().scan(scanPartition);		
	}
	
	public void actionPerformed() {
		
		text = "Scan of "+ scanDrive + " in progress";
		this.setText(text);	
		this.setBackground(Color.yellow);
		this.setEnabled(false);
		
		VisualHDD.getInstance().scan(scanPartition);		
	}
}