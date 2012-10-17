package visual.gui;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import visual.data.Node;
import visual.gui.graph.DefaultGraphPanel;
import visual.main.VisualHDD;

/**
 * Main program frame
 * @author Peter Pretorius
 */
public class ProgramWindow extends JFrame {
	private MenuBar menuBar;
	private JPanel partitionPanel;
	private JPanel graphPanel;
	private static boolean scanning; //keeps track of whether or not a scan is currently in progress
	private static boolean canceled = false;
	private JButton infoPanel;
	private int scanPartition = 0;
	private Node currentNode;
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
	 * Also constructs the partition panel based on the partitions found, and instantiates the DefaultGraphPanel
	 */
	private void init(File[] partitions)
	{
		menuBar = new MenuBar();
		setJMenuBar(menuBar);
		menuBar.addComponentListener(new ComponentListener() {		
			
			public void componentShown(ComponentEvent e) {}			
			public void componentResized(ComponentEvent e) {}			
			public void componentMoved(ComponentEvent e) {}			
			public void componentHidden(ComponentEvent e) {					
				if(scanning!=true && canceled==false){					
					((InformationBar) infoPanel).update();
					((InformationBar) infoPanel).actionPerformed();
					((DefaultGraphPanel) graphPanel).update(currentNode);
				}				
			}
		});
		
		graphPanel = new DefaultGraphPanel(partitions, currentNode);
		add(graphPanel, BorderLayout.CENTER);
		((DefaultGraphPanel) graphPanel).update(currentNode);
		
		infoPanel = new InformationBar();
		add(infoPanel, BorderLayout.NORTH);
		
		partitionPanel = new PartitionSelectionPanel(partitions);
		partitionPanel.addComponentListener(new ComponentListener() {		
			
			public void componentShown(ComponentEvent e) {}			
			public void componentResized(ComponentEvent e) {}			
			public void componentMoved(ComponentEvent e) {}			
			public void componentHidden(ComponentEvent e) {				
				if(scanning!=true){					
					((InformationBar) infoPanel).update();
					((DefaultGraphPanel) graphPanel).update(currentNode);
				}
			}
		});
		
		add(partitionPanel, BorderLayout.WEST);		
			
		scanning = false;
	}
	
	public void setScanStatus(boolean b) {
		if (scanning = b == true)
		{			
			scanning = true;			
		}
		
		else if(scanning = b == false && canceled == true)
		{
			scanning = false;
			((InformationBar) infoPanel).update();
			((DefaultGraphPanel) graphPanel).update(currentNode);			
		}
		
		else
		{			
			scanning = false;			
			currentNode = ((InformationBar) infoPanel).update();
			((DefaultGraphPanel) graphPanel).update(currentNode);
		}
	}
	
	public void setCanceled(boolean value){		
		canceled = value;
		
		if(canceled==true)			
			((InformationBar) infoPanel).update();		
	}
	
	public void setCurrentNode(Node node){
		currentNode = node;
	}
	
	public static boolean isScanning()
	{
		return scanning;
	}

	public static boolean getCanceled() {		
		return canceled;
	}
	
}