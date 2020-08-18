package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFO {

	private Task[] boundedBuffer;
	private int size;
	private int front;
	private int rear;
	private int counter;
	Object notFull;
	Object notEmpty;

	public BlockingFIFO(int s) {
		
		size = s;
		boundedBuffer = new Task[size];
        front = 0;
        rear = 0;
        counter = 0;
		notFull = new Object();
		notEmpty = new Object();
	}

//To check if the Blocking FIFO Queue is Empty
	boolean isEmpty() {
        if(counter<=0)
        return true;
		else
        return false;
	}

//To check if Blocking FIFO Queue is full
	boolean isFull() {
		if(counter >= size)
        return true;
        else
        return false;
	}

//To place a task in Blocking FIFO Queue
	public void put(Task task) {
		synchronized (this) {
			while (isFull()) {
				try {
					this.wait();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			boundedBuffer[front] = task;
			counter++;
//This condition is for circular Queue wherein the first few elements may remain
//empty and we have to fill it in vacant spots.
			front = (front + 1) % size;

		}
		
		// To make output easier to understand
		try {
			Thread.sleep(0);
		} catch (Exception e) {
		}

		
		
		synchronized (this) {
			this.notify();
		}

	}

//To take out the task from Blocking Queue
	public Task take() {
		Task temp;

		
		synchronized (this) {
			while (isEmpty()) {
				try {
					this.wait();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			temp = boundedBuffer[rear];
			
			rear = (rear + 1) % size;
			counter--;

		}

		// To make output easier to understand
		try {
			Thread.sleep(40);
		} catch (Exception e) {
		}

		
		synchronized (this) {
			this.notify();
		}

		return temp;

	}
	
}

