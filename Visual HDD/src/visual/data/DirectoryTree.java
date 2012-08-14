package visual.data;

import java.io.File;

public class DirectoryTree {
	
	//The file at the root of this tree
	private Directory root;
	
	//The path of the file at the root of this tree
	private String rootPath;
	
	
	/**
	 * Global directory count
	 */
	private int globalCount;
	
	public DirectoryTree(String rootPath)
	{
		globalCount = 0;
		this.rootPath = rootPath;
	}

	/**
	 * Construct the tree by beginning at the root Directory and recursively scanning all files and
	 * subfolders. This method is locking, and should be run in a thread to avoid deadlocks.
	 */
	public boolean buildTree() {
		System.out.println("Attempting to build directory structure");
		root = new Directory(new File(rootPath));
		System.out.println("Success. Global count is " + globalCount);
		
		return true;
	}

	/**
	 * 
	 * @author lunch
	 *
	 */
	private class Directory
	{
		/**
		 * The actual file correlating to this directory
		 */
		private File root;
		
		/**
		 * The files and sub-files in this directory
		 */
		private Directory[] files;
		
		/**
		 * The size of this directory tree
		 */
		private long size;
		
		public Directory(File root)
		{
			this.root = root;
			files = null;

			File[] fileList = root.listFiles();

			if (fileList != null) //This happens with some system folders
			{
				/**
				 * The following lines of code count the number of folders in this sub-directory
				 */
				int count = 0;
				for (int i = 0; i < fileList.length; i++)
					if (fileList[i].isDirectory())
					{
						count++;
						globalCount++;
					}

				//Now we know how many subfolders there are
				files = new Directory[count];


				/**
				 * Now add the folders to the subfolder list
				 */
				for (int i = 0; i < count; i++)
				{
					if (fileList[i].isDirectory())
						files[i] = new Directory(fileList[i]);

					size += fileList[i].length();
				}
			}
			
		}
		
		/**
		 * Returns the root of this directory structure
		 * @return
		 */
		public File getFile()
		{
			return root;
		}
		
		/**
		 * Returns the sub-directory of this directory
		 * @return
		 */
		public Directory[] getSubFiles()
		{
			return files;
		}
		
		/**
		 * Returns the total size (Files and folders) of this entire directory structure
		 * @return
		 */
		public long getTotalSize()
		{
			return size;
		}
	}
}
