package visual.data;

import java.io.File;
import java.util.ArrayList;

public class DirectoryTree extends AbstractDirectoryTree {
	
	//The scan task associated with this DirectoryTree
	private Thread scan;
	
	public DirectoryTree(Node root) {
		super(root);
	}
	
	public DirectoryTree(String rootPath)
	{
		super(rootPath);
	}

	@Override
	public boolean delete(String filePath) {
		if (filePath.contains("\\\\")) //This code fixes an oversight that arises when top-level nodes are assigned for deletion
			filePath = filePath.replace("\\\\", "\\");
		Node targetNode = retrieveNode(filePath); //get the node object associated with the specified filePath
		Node parentOfTargetNode = retrieveNode(filePath.substring(0, filePath.lastIndexOf("\\"))); //get the parent of the target node
		
		Node[] childNodes = parentOfTargetNode.getChildren();
		ArrayList<Node> nodeList = new ArrayList<Node>(); //Build a list of all nodes that are siblings of the parent node
		for (Node n: childNodes)
			nodeList.add(n);
		
		if(nodeList.remove(targetNode)) //remove the target node from the list of siblings
		{
			Node[] newNodeList = new Node[nodeList.size()];
			for (int i = 0; i < nodeList.size(); i++)
				newNodeList[i] = nodeList.get(i);
				
			parentOfTargetNode.setChildren(newNodeList); //parent now has the same children as before, minus the target node
			File f = new File(targetNode.getPath()); //Create a file object pointing to the specified node
			
			if (f.exists())
			{
				delete(f); //ask the user's operating system to delete the file
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Private helper method. Java only allows the deletion of directories that are empty. This recursive method iterates through the 
	 * children of any specified folder and deletes all subfiles
	 * @param f
	 */
	private void delete(File f) {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		    {
		      delete(c);
		    }
		  }
		    f.delete();
		}


	@Override
	public void buildTree() {
		if (scan == null)
			scan = new ScanTask();
		
		//scan has been done previously. "reset" the tree and rebuild from scratch
		else
		{
			root.setChildren(null);
			root.setSize(0); //this will be corrected with the size is recalculated
			totalSize = fileCount = runningTime = 0;
			built = false;
			scan = new ScanTask();
		}
		scan.start();
	}
	
	/**
	 * Interrupts the scanning process
	 */
	public void stopBuild(){		
		scan.interrupt();
		built=true;		
	}

	@Override
	public Node retrieveNode(String nodePath) {	
		return retrieveNode(root, nodePath.substring(nodePath.indexOf("\\")+1));
	}
	
	/**
	 * Recursive helper method for retreiving a node
	 * @param n
	 */
	private Node retrieveNode(Node current, String nodePath)
	{
		if(nodePath.equals("") || nodePath.length() <=2){ //dealing with a root node
			return root;
		}
		
		else if (!nodePath.contains("\\")) //dealing with a top-tier node. Find it by searching among the children of the current root
		{
			Node[] children = current.getChildren();
			if (children != null)
			{
				for (Node n: children)
					if (n.getName().equalsIgnoreCase(nodePath))
						return n;
			}
		}
		
		else //dealing with a deep node. Traverse down the tree from the root node until it is found
		{
			String[] nodePath_split = nodePath.split("\\\\");
			Node[] children = current.getChildren();
			
			if (children != null)
			{
				String nextNodeName = nodePath_split[0];

				for (Node n: children)
					if (n.getName().equalsIgnoreCase(nextNodeName))
						return retrieveNode(n, nodePath.substring(nodePath.indexOf("\\")+1, nodePath.length()));
			}
			
		}
		return null;

	}
	
	/**
	 * Private recursive method that does the scanning necessary for building the tree
	 * @param n
	 */
	private void scan(Node n)
	{
		Node[] childNodes = getChildNodes(n);
		totalSize += n.getSize();
		
		//base case
		if (childNodes == null)
		{
			fileCount++;
		}
		
		else
		{
			n.setChildren(childNodes);
			for (Node node: childNodes)
					scan(node);
		}
	}
	
	/**
	 * Scanning happens in its own thread to prevent the UI from locking up. 
	 * @author Peter Pretorius
	 *
	 */
	private class ScanTask extends Thread
	{
		public void run()
		{
			long startTime = System.currentTimeMillis();
			scan(root);
			runningTime = System.currentTimeMillis() - startTime;
			built = true;
			recalculateSizes();
		}
	}
}
