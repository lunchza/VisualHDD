package visual.main;

import java.io.File;

import javax.swing.JOptionPane;

import visual.data.DirectoryTree;
import visual.gui.ProgramWindow;

public class VisualHDD {
	
	/**
	 * A tree is constructed for every available partition on the user's filesystem. The summary of total disk usage is constructed
	 * from the concatenation of the sizes of these individual trees
	 * 
	 * e.g A user has 2 partitions, C: and D:, with sizes 150gb and 200gb respectively. The program constructs 2 trees for these partitions.
	 * with the summary consisting of a pie-graph with 2 slices, C: (43%) and D: (57%)
	 */
	private DirectoryTree[] partitionTrees;
	
	//singleton
	private static VisualHDD visualHDD;
	
	/**
	 * Program frame
	 */
	ProgramWindow frame;
	
	/**
	 * Program version
	 */
	public static final String VERSION = "0.1";

	private VisualHDD()
	{
		File[] roots = getAvailableSystemPartitions(); //get all top-level folders/partitions
		partitionTrees = new DirectoryTree[roots.length]; //need 1 DirectoryTree for each partition
		
		for (int i = 0; i < partitionTrees.length; i++)
		{
			partitionTrees[i] = new DirectoryTree(roots[i].getAbsolutePath());
		}
		
		frame = new ProgramWindow(roots);
		frame.setVisible(true);		
	}
	
	
	public static void main(String[] args) {
		getInstance();
	}
	
	/**
	 * Returns a list of available partitions on this system
	 * @return
	 */
	private File[] getAvailableSystemPartitions()
	{
		return File.listRoots();
	}
	
	//singleton
	public static VisualHDD getInstance()
	{
		if (visualHDD == null)
			visualHDD = new VisualHDD();
		
		return visualHDD;
	}
	
	/**
	 * Starts scanning the drive corresponding to the DirectoryTree at the specified index. If the index is -1, every partition will be
	 * scanned in order to create the overview
	 * @param index
	 */
	public void scan(int index)
	{
		if (frame.isScanning())
		{
			JOptionPane.showMessageDialog(null, "Can't interrupt scanning atm, fix soon etc");
			return;
		}
		frame.setScanStatus(true);
		partitionTrees[index].buildTree();
	}
}
