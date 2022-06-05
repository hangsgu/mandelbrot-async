package main.java;

import async.Buffer;
import async.CalculateColor;
import mandelbrot.Complex;

public class Main {
    public static void main(String[] args)  {
        double xc   = -0.5;
        double yc   = 0;
        double size = 2;

        if (args.length == 3) {
            xc   = Double.parseDouble(args[0]);
            yc   = Double.parseDouble(args[1]);
            size = Double.parseDouble(args[2]);
        }

        int n   = 512;   // create n-by-n image
        int max = 255;   // maximum number of iterations

        Buffer buffer = new Buffer(5);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x = xc - size/2 + size*i/n;
                double y = yc - size/2 + size*j/n;
                Complex number = new Complex(x, y);

                CalculateColor task = new CalculateColor(number, max);
                buffer.push(task);
            }
        }

        // TODO: Figure out how to "close" the tasks queue, apparently this is not possible with LinkedBlockingQueue,
        //  one approach is to send invalid objects into the queue as a signal for the threads to end

    }
}
