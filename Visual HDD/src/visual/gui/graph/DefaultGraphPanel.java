package visual.gui.graph;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefaultGraphPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//while scanning, an image is displayed that lets the user know that a scan is in progress
	private boolean scanning;
	
	/**
	 * Placeholder label
	 */
	private JLabel placeHolder;
	
	public DefaultGraphPanel()
	{
		scanning = false;
		
		placeHolder = new JLabel("Please select a partition on the left to start scanning");
		add(placeHolder);
	}
	
	public void showScanningAnimation()
	{
		scanning = true;
		placeHolder.setText("Scan in progress");
		repaint();
	}
	

	public void paintComponent(Graphics g)
	{
		//super.paintComponents(g);

	}

}
