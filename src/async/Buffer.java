package async;

import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {
    private final ColorCalculator[] colorCalculators;
    private static LinkedBlockingQueue<CalculateColor> tasks;
    
    public Buffer(int numberOfWorkers) {
        tasks = new LinkedBlockingQueue<>();
        colorCalculators = new ColorCalculator[numberOfWorkers];
        								
        for (int i = 0; i < numberOfWorkers; i++) {
            colorCalculators[i] = new ColorCalculator(tasks);
            colorCalculators[i].start();
        }
    }

    public void push(CalculateColor task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }
}
