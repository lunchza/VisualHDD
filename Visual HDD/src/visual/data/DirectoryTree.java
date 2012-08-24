package visual.data;

import java.io.File;

public class DirectoryTree extends AbstractDirectoryTree {

	public DirectoryTree(Node root) {
		super(root);
	}
	
	public DirectoryTree(String rootPath)
	{
		super(rootPath);
	}

	@Override
	public boolean delete(String filePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void buildTree() {
		
		new Thread()
		{
			public void run()
			{
				scan(root);
				System.out.println("Tree built successfully. Scanned " + fileCount + " files.");
				built = true;
			}
		}.start();
	}

	@Override
	public Node retrieveNode(String nodePath) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void scan(Node n)
	{
		System.out.println("Scanning " + n.getPath());
		Node[] childNodes = getChildNodes(n);
		
		//base case
		if (childNodes == null)
			fileCount++;
		
		else
		{
			n.setChildren(childNodes);
			for (Node node: childNodes)
				scan(node);
		}
	}
	

}
