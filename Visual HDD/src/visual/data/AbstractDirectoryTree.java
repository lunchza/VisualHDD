package visual.data;

import java.io.File;

public abstract class AbstractDirectoryTree implements IDirectoryTree {
	
	
	/**
	 * The root of this Directory Tree. This Node is the only way that the tree elements can be
	 * traversed
	 */
	protected Node root;
	
	/**
	 * Keeps track of whether or not this tree has been fully constructed. Will be false for either 
	 * an empty tree or one that's in the process of being built
	 */
	protected boolean built;
	
	/**
	 * The total number of files stored in this tree. Does not include directories 
	 */
	protected static long fileCount;
	
	/**
	 * The collective size of all files in this tree
	 */
	protected long totalSize;
	
	/**
	 * The total time taken to construct this DirectoryTree
	 */
	protected long runningTime;
	
	/**
	 * Instantiates the Directory Tree with the specified node as Root. Tree propagation won't occur 
	 * until the build() method is called
	 * @param root
	 */
	public AbstractDirectoryTree(Node root)
	{
		this.root = root;
		built = false;
	}
	
	/**
	 * Instantiates a Directory Tree with root Path being the path of the root Node. 
	 * Tree propagation won't occur until the build() method is called
	 * @param root
	 */
	public AbstractDirectoryTree(String rootPath)
	{
		this(new Node(new File(rootPath)));
	}
	
	@Override
	public boolean isBuilt()
	{
		return built;
	}
	
	@Override
	public long getNumFiles()
	{
		return fileCount;
	}
	
	@Override
	public Node[] getChildNodes(Node n)
	{
		File nodeFile = new File(n.getPath());

		File[] subFiles = nodeFile.listFiles();

		if (subFiles != null) //happens with some protected system folders
		{
			Node[] nodeList = new Node[subFiles.length];

			for (int i = 0; i < nodeList.length; i++)
			{
				nodeList[i] = new Node(subFiles[i]);
			}
			return nodeList;
		}
		else
			return null;
	}
	
	@Override
	public void printTree()
	{
		System.out.println("\n\n*****************PRINTING TREE STRUCTURE: ************************\n\n");
		print(root, 0);
	}
	
	/**
	 * Helper method for printTree()
	 */
	private void print(Node n, int depth)
	{
		for (int i = 0; i < depth; i++)
			System.out.print("|-");
		
		System.out.println(n.getName() + "(" + n.getSize() + " bytes)");
		
		
		if (n.isChildNode()) //base case
			return;
			
		else
		{
			for (Node children: n.getChildren())
				print(children, depth+1);
		}
	}
	
	public long getRunningTime()
	{
		return runningTime;
	}
	
	/**
	 * Once the tree structure has been created, the sizes of all directories are recalculated
	 */
	public void recalculateSizes()
	{
		totalSize = recalculateSize(root);
	}
	
	/**
	 * Recalculates the sizes of all nodes by summing the sizes of their child nodes. This is required since Java doesn't automatically
	 * determine the sizes of folders
	 * @param n
	 * @return
	 */
	private long recalculateSize(Node n)
	{
		long size = n.getSize();
		
		//base case
		if (!n.isChildNode())
		{
			Node[] children = n.getChildren();
			for (Node child: children)
				size += recalculateSize(child);
			n.setSize(size);
		}
		
			return size;
	}
	
	public long getTotalSize()
	{
		return totalSize;
	}
}
