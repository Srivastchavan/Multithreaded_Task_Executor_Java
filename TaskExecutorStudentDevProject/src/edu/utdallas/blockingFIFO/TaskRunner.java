package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.blockingFIFO.BlockingFIFO;

public class TaskRunner implements Runnable {
	BlockingFIFO queue;
	
	public TaskRunner(BlockingFIFO q) {
		// To start as many consumer threads as required
		queue = q;
		Thread newThread = new Thread(this);
		newThread.start();
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				//To execute a task from the queue but then block if not possible 
				Task task = queue.take();
				task.execute();
			} catch (Exception e) {
				 System.out.println(e.getMessage());
			}
			Thread.yield();
		}
	}

}
