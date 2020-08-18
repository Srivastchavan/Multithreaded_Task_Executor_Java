package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.blockingFIFO.TaskRunner;

public class TaskExecutorImpl implements TaskExecutor
{
	TaskRunner tskRunner[];
	//To create new buffer which can hold up to 100 tasks
	private BlockingFIFO boundedBuffer = new BlockingFIFO(100);

	
	@Override
	public void addTask(Task task) {
		// To add Task to the Buffer but block if not possible
		try {
			boundedBuffer.put(task);
		} catch (Exception e) {
			System.out.println("Error while adding the task to the BlockingFIFO queue.");
		}
	}

	public TaskExecutorImpl(int totalThreads) {
		//create a task runner array with requested number of threads
		tskRunner = new TaskRunner[totalThreads];
		for (int i = 0; i < totalThreads; i++) {
			tskRunner[i] = new TaskRunner(boundedBuffer);
		}
	}
}
