package visual.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import visual.gui.graph.DefaultGraphPanel;
import visual.main.Status;
import visual.main.VisualHDD;

/**
 * Main program frame
 * @author Peter Pretorius
 */
public class ProgramWindow extends JFrame {
	
	/**
	 * MenuBar for this frame
	 */
	private MenuBar menuBar;
	
	/**
	 * Status bar for this frame
	 */
	private static JStatusBar statusBar;
	
	/**
	 * Partition list panel
	 */
	private JPanel partitionPanel;
	
	/**
	 * Graph panel
	 */
	private JPanel graphPanel;
	
	//keeps track of whether or not a scan is currently in progress
	private boolean scanning;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProgramWindow(File[] partitions)
	{
		super("Visual HDD v" + VisualHDD.VERSION); //frame title
		setSize(800, 600); //fixed frame size
		setResizable(false); //non-resizable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quit on close
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); //center frame
		
		init(partitions);
	}
	
	/**
	 * Initialise frame by adding all sub-components, status bar and menu bar
	 * Also constructs the partition panel based on the partitions found, and instantiates a DefaultGraphPanel as a placeholder
	 */
	private void init(File[] partitions)
	{
		menuBar = new MenuBar(); //initialize menuBar
		setJMenuBar(menuBar); //set menuBar
		
		graphPanel = new DefaultGraphPanel(); //graphPanel is empty for now
		add(graphPanel, BorderLayout.CENTER);
		
		partitionPanel = new PartitionSelectionPanel(partitions);
		add(partitionPanel, BorderLayout.WEST);
		
		statusBar = new JStatusBar(JStatusBar.LEFT_ORIENTATION);
		add(statusBar, BorderLayout.SOUTH);
		updateStatus(Status.Program_ready);
		
		scanning = false;
	}
	
	/**
	 * Updates the current status displayed in the status bar
	 */
	public static void updateStatus(Status s)
	{
		statusBar.setStatus(s.name().replace("_", " "));
	}

	public void setScanStatus(boolean b) {
		if (scanning = b == true)
		{
			updateStatus(Status.Scanning_drive);
			scanning = true;
			((DefaultGraphPanel) graphPanel).showScanningAnimation();
		}
		
		else
		{
			scanning = false;
		}
	}
	
	public boolean isScanning()
	{
		return scanning;
	}
	
}
