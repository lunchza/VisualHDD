package visual.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DirectoryTree extends AbstractDirectoryTree {
	
	Thread scan;
	
	public DirectoryTree(Node root) {
		super(root);
	}
	
	public DirectoryTree(String rootPath)
	{
		super(rootPath);
	}

	@Override
	public boolean delete(String filePath) {
		Node targetNode = retrieveNode(filePath);
		Node parentOfTargetNode = retrieveNode(filePath.substring(0, filePath.lastIndexOf("\\")));
		
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
				delete(f);
				System.out.println("Deleted " + targetNode.getPath());

			}
			else
				System.out.println("Cannot find file " + f.getAbsolutePath());
				/**
				 * TEST CODE
				 */
			return true;
		}
		return false;
	}
	
	void delete(File f) {
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
	
	public void stopBuild(){		
		scan.interrupt();
		built=true;		
	}

	@Override
	public Node retrieveNode(String nodePath) {	
		return retrieveNode(root, nodePath.substring(nodePath.indexOf("\\")+1));
	}
	
	/**
	 * Helper method
	 * @param n
	 */
	private Node retrieveNode(Node current, String nodePath)
	{
		if(nodePath.equals("")){
			return root;
		}
		
		else if (!nodePath.contains("\\"))
		{
			Node[] children = current.getChildren();
			if (children != null)
			{
				for (Node n: children)
					if (n.getName().equalsIgnoreCase(nodePath))
						return n;
			}
		}
		
		else
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
	
	private void scan(Node n)
	{
		//System.out.println("Scanning " + n.getPath());
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
