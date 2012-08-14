package visual.test;

/**
 * Test class for different scanning algorithms. Returns the running time for each algorithm,
 * as well as the number of processed files. Can run multiple benchmarks sequentially for comparison
 * purposes
 * @author lunch
 *
 */
public class TestThread extends Thread{
	
	/**
	 * The list of tasks that will be executed (sequentially) by this thread
	 */
	private ScanTask []task;
	
	public TestThread(ScanTask task)
	{
		this.task = new ScanTask[1];
		this.task[0] = task;
	}
	
	public TestThread(ScanTask[] task)
	{
		this.task = task;
	}
	
	/**
	 * returns the number of tasks that this thread will execute
	 * @return
	 */
	public int getNumTasks()
	{
		return task.length;
	}
	
	public void start()
	{
		for (ScanTask st: task)
		{
			System.out.println("Starting test " + st.getName());
			st.start();
		}
	}
	
}
