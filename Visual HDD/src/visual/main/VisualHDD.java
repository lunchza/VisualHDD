package visual.main;

import visual.data.DirectoryTree;

public class VisualHDD {

	public VisualHDD()
	{
		DirectoryTree tree = new DirectoryTree("C:\\test");
		System.out.println("Tree instantiated");
		tree.buildTree();
		while(!tree.isBuilt())
		{
			//do nothing
		}
		tree.printTree();
	}
	
	
	public static void main(String[] args) {
		new VisualHDD();

	}

}
