package async;

import mandelbrot.Complex;

import java.awt.*;

public class CalculateColor implements Runnable {
    private final Complex number;
    private final int maxIterations;

    public CalculateColor(Complex number, int maxIterations) {
        this.number = number;
        this.maxIterations = maxIterations;
    }
    @Override
    public void run() {
        Complex newNumber = number;
        int result = maxIterations;
        for (int t = 0; t < maxIterations; t++) {
            if (newNumber.abs() > 2.0) {
                result = t;
                break;
            };
            newNumber = newNumber.times(newNumber).plus(newNumber);
        }

        int gray = maxIterations - result;
        Color color = new Color(gray, gray, gray);
        // TODO: Push color to another buffer for the drawing thread, I think another buffer instance with one thread will do the trick
        System.out.println(color.toString());
    }
}
