package visual.test;

import java.io.File;

import javax.swing.JFileChooser;

import visual.data.DirectoryTree;

public class Main {
	
	static JFileChooser chooser;
	static File directory;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
			directory = chooser.getSelectedFile();
		else
			System.exit(0);
		RecursiveScan rs = new RecursiveScan(directory);
		TestThread tt = new TestThread(rs);
		tt.start();		
	}

}
