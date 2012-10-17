package visual.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

import visual.main.VisualHDD;

/**
 * Menu bar for the program frame
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
		quickHelpMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DisposableReadmeFrame frame = new DisposableReadmeFrame();
			}
		});
		
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "VisualHDD v" + VisualHDD.VERSION + "\n\nCreated by\n\t\t-Calvin Nefdt\n\t\t-Peter Pretorius\n\t\t-Darren Osterloh\n\t\t-Tristan Goring\n\t\t-Sanchia Sukraj\n\n(c) COMP301 2012");
			}
		});
		
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
	
	/**
	 * Constructs a disposable frame for displaying help information. This frame has a one-time use, it is destoroyed when closed
	 * @return
	 */
	private class DisposableReadmeFrame extends JFrame
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextArea textArea;

		public DisposableReadmeFrame()
		{
			super("Quick help");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setResizable(false);
			setSize(800, 200);
			setLocationRelativeTo(null);

		
			textArea = new JTextArea();;
			textArea.setWrapStyleWord(true);
			
			loadReadme();
			add(new JScrollPane(textArea));
			
			setVisible(true);
		}
		
		/**Loads the readme.txt file and puts its contents into 'area', line-by-line
		 * 
		 */
		private void loadReadme()
		{

			Scanner sc = null;
			File f = null;
			try {
				f = new File("Readme.txt");
				sc = new Scanner(f);

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Unable to locate readme.txt!");
				dispose();
				setVisible(false);
			} 

			if(f.exists())
				while(sc.hasNext())
				{
					textArea.setText(textArea.getText() + sc.nextLine() + "\n");
				}
		}
	}	

}
