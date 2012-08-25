package visual.data;

public interface IDirectoryTree {
	
	/**
	 * Deletes the Node with path filePath. 
	 * @param filePath The absolute path of the file that is to be deleted
	 * @return true if the node was successfully deleted, false otherwise
	 */
	boolean delete(String filePath);
	
	/**
	 * Constructs the tree, beginning with the root node and recursively creating new nodes for 
	 * sub-files and directories. This is a depth-first search and runs in its own thread. 
	 */
	void buildTree();
	
	/**
	 * Finds and returns the Node located at nodePath
	 * @param nodePath the absolute path to the path of the desired node
	 * @return null if the node was not found in the tree
	 */
	Node retrieveNode(String nodePath);
	
	/**
	 * Constructs a list of Nodes, each of which 
	 * @param n
	 * @return
	 */
	Node[] getChildNodes(Node n);
	
	/**
	 * Used to determine whether or not this tree has been fully constructed
	 * @return whether or not this tree is complete
	 */
	boolean isBuilt();
	
	/**
	 * Attempts to print a crude representation of this tree to the console window
	 */
	void printTree();
	
	/**
	 * Returns the number of files contained in this Directory Tree
	 * @return
	 */
	long getNumFiles();

}
