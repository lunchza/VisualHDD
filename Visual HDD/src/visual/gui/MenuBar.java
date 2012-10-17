package visual.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import visual.gui.graph.DefaultGraphPanel;
import visual.main.VisualHDD;

/**
 * Menu bar for the program frame. Menu structure is as follows:
 * 
 * @author Peter Pretorius
 *
 */
public class MenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Menu components
	 */
	private JMenu fileMenu, optionsMenu, helpMenu;
	
	/**
	 * MenuItem components
	 */
	private JMenuItem rescanMenuItem, exitMenuItem, configurationMenuItem, quickHelpMenuItem, aboutMenuItem, stopMenuItem;
	
	public MenuBar()
	{
		init();
		buildMenu();
	}
	
	/**
	 * Initialises all menu items and sets up appropriate listeners
	 */
	private void init()
	{		
		rescanMenuItem = new JMenuItem("Scan");
		rescanMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				if(InformationBar.getPartition()!=-1){
					setVisible(false);
					setVisible(true);
				}
			}
		});
		
		stopMenuItem = new JMenuItem("Stop Scan");
		stopMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VisualHDD.stopScan(InformationBar.getPartition());	
				setVisible(false);
				setVisible(true);
			}
		});	
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		configurationMenuItem = new JMenuItem("Configuration");
		
		quickHelpMenuItem = new JMenuItem("Quick Help");
		aboutMenuItem = new JMenuItem("About");
		
		fileMenu = new JMenu("File");
		optionsMenu = new JMenu("Options");
		helpMenu = new JMenu("Help");
	}
	
	/**
	  * Constructs the menu according to the following structure:
	  * FILE
	  * 	->Rescan
	  * 	->Exit
	  * OPTIONS
	  * 	->Configuration
	  * HELP
	  * 	->Quick help
	  * 	->About
	 */
	private void buildMenu()
	{		
		fileMenu.add(rescanMenuItem);
		fileMenu.add(stopMenuItem);
		fileMenu.add(exitMenuItem);
		
		optionsMenu.add(configurationMenuItem);
		
		helpMenu.add(quickHelpMenuItem);
		helpMenu.add(aboutMenuItem);
		
		add(fileMenu);
		add(optionsMenu);
		add(helpMenu);
	}
}
