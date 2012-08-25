package visual.data;

import java.io.File;

/**
 * A Node is an element in the heirarchical file structure that represents either a file or directory.
 * Each node stores information about the file it represents, as well as any child nodes (sub-files/folders)
 * that it possesses.
 * @author Peter Pretorius
 *
 */
public class Node {

	/**
	 * The absolute path of this node. Paths are constructed as nodes are created, with each node
	 * inheriting the cumulative path of its parent nodes
	 */
	private String path;
	
	/**
	 * The name of this node. This is the relative path of the file associated with this node
	 */
	private String name;
	
	/**
	 * The size of the file associated with this node. This is a local size i.e. for directories this
	 * value will reportedly be zero. Once the entire tree has been constructed, the size of directory
	 * nodes will be recalculated
	 */
	private long size;
	
	/**
	 * Each node points to child nodes, resulting in top-down access. The root node will hence be the
	 * partition identifier 
	 */
	private Node[] children;
	/*
	public Node(String path)
	{
		this.path = path;
		this. size = 0;
	}*/
	
	public Node(File f)
	{
		this.path = f.getAbsolutePath();
		this.name = f.getName();
		if (!f.isDirectory())
			this.size = f.length();
		else
			this.size = 0;
	}
	
	
	/**
	 * Determines whether or not this node has any child nodes
	 * @return
	 */
	public boolean isChildNode()
	{
		return children == null;
	}
	
	/**
	 * returns the child nodes belonging to this node
	 * @return
	 */
	public Node[] getChildren()
	{
		return children;
	}
	
	/**
	 * 
	 * @param children
	 */
	public void setChildren(Node[] children)
	{
		this.children = children;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public String getName()
	{
		return name;
	}
	
	public long getSize()
	{
		return size;
	}
	
	public void setSize(long s)
	{
		size = s;
	}
}
