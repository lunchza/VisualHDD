package visual.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
	private static DirectoryTree[] partitionTrees;
	
	//singleton
	private static VisualHDD visualHDD;
	
	/**
	 * Program frame
	 */
	static ProgramWindow frame;
	
	/**
	 * Program version
	 */
	public static final String VERSION = "0.1";

	private VisualHDD()
	{
		/*
		ArrayList<File> rootsTemp = new ArrayList<File>(Arrays.asList(getAvailableSystemPartitions())); //get all top-level folders/partitions
		ArrayList<File> temp = new ArrayList<File>();
		
		for(int i=0;i<rootsTemp.size();i++){
			System.out.println(rootsTemp.get(i).getAbsolutePath() + ": " + rootsTemp.get(i).getTotalSpace());
			if(rootsTemp.get(i).getTotalSpace()>0)
				temp.add(rootsTemp.get(i));			
		}*/
		File[] roots = getAvailableSystemPartitions();
		
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
	 * Returns a list of available partitions on this system. Does not return partitions that have size 0
	 * @return
	 */
	private File[] getAvailableSystemPartitions()
	{
		File[] allRoots = File.listRoots();
		ArrayList<File> rootsList = new ArrayList<File>();
		for (File f: allRoots)
			if (f.getTotalSpace() != 0) //empty partition
				rootsList.add(f);
		
		File[] roots = new File[rootsList.size()];
		for (int i = 0; i < rootsList.size(); i++)
			roots[i] = rootsList.get(i);
		
		return roots;
			
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
	public void scan(final int index)
	{
		if (frame.isScanning())
		{
			JOptionPane.showMessageDialog(null, "Can't interrupt scanning atm, fix soon etc");
			return;
		}
		frame.setCanceled(false);
		frame.setScanStatus(true);
		
		partitionTrees[index].buildTree();
		
		Thread checkScan = new Thread(new Runnable(){
			
			@Override
			public void run() {
				while(!partitionTrees[index].isBuilt()){					
					frame.repaint();								
				}			
				frame.setScanStatus(false);				
			}		
		});
		
		checkScan.start();	
	}
	
	 public static void stopScan(final int index){
		 if(!frame.isScanning()){
		 JOptionPane.showMessageDialog(null, "There is no scan underway");
		 frame.setCanceled(true);
		 return;
		 }

		 partitionTrees[index].stopBuild();
		 frame.setCanceled(true);
		 }

	
	public static DirectoryTree getTree(int index){
		return partitionTrees[index];
	}
}
