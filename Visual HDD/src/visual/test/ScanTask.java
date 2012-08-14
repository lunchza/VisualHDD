package visual.test;

import java.io.File;

/**
 * The Scan Tasks each use different algorithms to accomplish the same goal: iterating through every
 * file on a HDD/partition/folder, beginning with a supplied "root" folder
 * @author lunch
 *
 */
public abstract class ScanTask{
	
	/**
	 * The root folder of this task (top-level parent)
	 */
	public File root;
	
	/**
	 * The name of this task. Useful when comparing algorithms
	 */
	private String name;
	
	/**
	 * The number of files scanned by this Scan Task
	 */
	private int numFiles;
	
	/**
	 * Records the time that the task starts (ms). When the task completes, the running time is
	 * calculated as endTime-startTime
	 */
	private long startTime, runningTime;
	
	/**
	 * Determines whether execution of this task has finished
	 */
	private boolean isRunning;
	
	public ScanTask(File root)
	{
		this.root = root;
		name = this.getClass().getName();
		numFiles = 0;
		startTime = 0;
		isRunning = false;
	}
	
	/**
	 * Start this task
	 * @return whether or not this task is running
	 */
	public void start()
	{
		isRunning = true;
		new Thread()
		{
			public void run()
			{

				startTime = System.currentTimeMillis();
				numFiles =  scan(root);
				runningTime = System.currentTimeMillis()-startTime;
				isRunning = false;
				System.out.println("Scan " + name + " completed in " + runningTime + " ms. " + numFiles + " files scanned total");
			}
		}.start();
	}
	
	/**
	 * The scanning algorithm is contained in this method
	 * @param root the top-level folder
	 * @return the number of scanned files
	 */
	abstract int scan(File root);
	
	/**
	 * @return The number of files scanned by this Scan Task
	 */
	public int getNumFiles()
	{
		return numFiles;
	}
	
	/**
	 * Determines whether execution of this task has finished
	 * @return true if scanning is in progress
	 */
	public boolean isRunning()
	{
		return isRunning;
	}
	
	/**
	 * @return the name of this scan task
	 */
	public String getName()
	{
		return name;
	}
	
	public long getRunningTime()
	{
		return runningTime;
	}
}
