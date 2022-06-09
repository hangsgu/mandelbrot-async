package async;

import java.util.concurrent.BlockingQueue;

public class ColorCalculator extends Thread {
	private static BlockingQueue<CalculateColor> tasks;
	private boolean stop;

	public ColorCalculator(BlockingQueue<CalculateColor> task) {
		tasks = task;
		this.stop = false;
	}

	@Override
	public void run() {
		CalculateColor task;

		while (!stop) {
			if (!tasks.isEmpty()) {
				synchronized (tasks) {
					task = tasks.poll();
				}
				if (task.getStop()) {
					this.stop = true;
					Thread.currentThread().interrupt();
					break;
				} else {
					try {
						task.run();
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
						this.stop = true;
						Thread.currentThread().interrupt();
						break;
					}
				}
			}
		}
		System.out.println(Thread.currentThread().toString() + " is now terminated.");
		return;
	}
}
