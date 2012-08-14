package visual.test;

import java.io.File;

public class RecursiveScan extends ScanTask {

	public RecursiveScan(File root) {
		super(root);
	}

	@Override
	int scan(File root) {
		int count = 0;
		
			if (root.isDirectory())
			{
				File[] subDirectory = root.listFiles();
				
				if (subDirectory != null) //This happens with some system folders
				{
					//System.out.println(subDirectory.length + " files in subdirectory " + root.getName());
					for (int i = 0; i < subDirectory.length; i++)
						count += scan(subDirectory[i]);
				}
				
			}
		
			else
				count +=1;

		
		return count;
	}
}
