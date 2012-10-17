package visual.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.ImageIcon;
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
	private JButton backButton;
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
		/*
		 * Initialise and set menu bar for this frame
		 */
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
				}				
			}
		});
		
		/*
		 * Initialize the graphPanel and center it in the frame
		 */
		graphPanel = new DefaultGraphPanel(partitions, currentNode);
		add(graphPanel, BorderLayout.CENTER);
		((DefaultGraphPanel) graphPanel).update(currentNode);
		
		/*
		 * The northern-most components are kept in an innder panel with no layout. This allowed for precise component positioning for
		 * the panel
		 */
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(640, 100));
		northPanel.setLayout(null);
		
		backButton = new JButton(new ImageIcon("back.gif"));
		backButton.setBounds(0, 0, 95, 95);
		
		infoPanel = new InformationBar();
		infoPanel.setBounds(100, 0, 680, 100);
		
		/*
		 * Add components to North panel
		 */
		northPanel.add(infoPanel);
		northPanel.add(backButton);
		
		add(northPanel, BorderLayout.NORTH);
		
		/*
		 * Initialise the partition button panel and sets up appropriate listeners for it
		 */
		partitionPanel = new PartitionSelectionPanel(partitions);
		partitionPanel.addComponentListener(new ComponentListener() {		
			
			public void componentShown(ComponentEvent e) {}			
			public void componentResized(ComponentEvent e) {}			
			public void componentMoved(ComponentEvent e) {}			
			public void componentHidden(ComponentEvent e) {				
				if(scanning!=true){					
					((InformationBar) infoPanel).update();
				}
			}
		});
		
		add(partitionPanel, BorderLayout.WEST);		
		
	
		
		scanning = false;
	}
	
	
	/**
	 * Allows for changing of the current status of scanning, which subsequently updates the Information bar and Graph Panel
	 */
	public void setScanStatus(boolean b) {
		if (scanning = b == true)
		{			
			scanning = true;			
		}
		
		else if(scanning = b == false && canceled == true)
		{
			scanning = false;
			((InformationBar) infoPanel).update();
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