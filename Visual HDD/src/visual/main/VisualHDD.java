package visual.main;

import java.io.File;

import visual.data.DirectoryTree;
import visual.data.Node;

public class VisualHDD {

	public VisualHDD()
	{
		File f = new File("C:\\test");
		DirectoryTree tree = new DirectoryTree(f.getAbsolutePath());
		System.out.println("Tree instantiated");
		tree.buildTree();
		while(!tree.isBuilt())
		{
			double percentage = tree.getTotalSize() / (double)f.getTotalSpace();
			System.out.println((int)(percentage*100) + "%  complete.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//tree.printTree();
		long calculateTime = System.currentTimeMillis();
		System.out.print("\nRecalculating sizes...");
		tree.recalculateSizes();
		System.out.println("Done!");
		System.out.println("Size recalculation took: " + (System.currentTimeMillis() - calculateTime) + "ms");
		tree.printTree();
		System.out.println("\nDeleting node \'test\\Hello\\herp.txt\': ");
		if (tree.delete("test\\Hello\\herp.txt"))
			System.out.println("Node deleted successfully");
		else
			System.out.println("Could not find node");
		tree.printTree();
	}
	
	
	public static void main(String[] args) {
		new VisualHDD();

	}

}
