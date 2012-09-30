package visual.data;

import java.util.ArrayList;

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
		Node targetNode = retrieveNode(filePath);
		Node parentOfTargetNode = retrieveNode(filePath.substring(0, filePath.lastIndexOf("\\")));
		
		Node[] childNodes = parentOfTargetNode.getChildren();
		ArrayList<Node> nodeList = new ArrayList<Node>();
		for (Node n: childNodes)
			nodeList.add(n);
		
		if(nodeList.remove(targetNode))
		{
			Node[] newNodeList = new Node[nodeList.size()];
			for (int i = 0; i < nodeList.size(); i++)
				newNodeList[i] = nodeList.get(i);
				
			parentOfTargetNode.setChildren(newNodeList);
			return true;
		}
		return false;
		
	}

	@Override
	public void buildTree() {
		
		new Thread()
		{
			public void run()
			{
				long startTime = System.currentTimeMillis();
				scan(root);
				runningTime = System.currentTimeMillis() - startTime;
				built = true;
			}
		}.start();
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
		if (!nodePath.contains("\\"))
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
}
