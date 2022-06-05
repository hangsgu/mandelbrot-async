package async;

import java.util.concurrent.BlockingQueue;

public class ColorCalculator extends Thread {
    private final BlockingQueue<CalculateColor> tasks;

    public ColorCalculator(BlockingQueue<CalculateColor> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        CalculateColor task;
        while (true) {
            synchronized (tasks) {
                while (tasks.isEmpty()) {
                    try {
                        tasks.wait();
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                task = tasks.poll();

                try {
                    task.run();
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }
}
