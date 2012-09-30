package visual.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import visual.main.VisualHDD;

public class PartitionSelectionPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The list of buttons in the panel, one for each found partition
	 */
	private JButton[] partitionButtons;
	
	public PartitionSelectionPanel(File[] partitions)
	{
		setLayout(new GridLayout(partitions.length, 1));
		
		partitionButtons = new JButton[partitions.length];
		
		for (int i = 0; i < partitions.length; i++)
		{
			partitionButtons[i] = new JButton(partitions[i].getAbsolutePath());
			partitionButtons[i].addActionListener(this);
			add(partitionButtons[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < partitionButtons.length; i++)
			if (partitionButtons[i].equals(e.getSource()))
				VisualHDD.getInstance().scan(i);
	}

}
