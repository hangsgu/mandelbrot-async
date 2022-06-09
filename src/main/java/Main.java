package main.java;

import async.Buffer;
import async.CalculateColor;
import mandelbrot.Picture;

public class Main {

    public static void main(String[] args)  {
        double xc   = -0.5;
        double yc   = 0;
        double size = 2;
        int numberOfThreads = 5;

        if (args.length == 3) {
            xc   = Double.parseDouble(args[0]);
            yc   = Double.parseDouble(args[1]);
            size = Double.parseDouble(args[2]);
        }
        else if (args.length == 4) {
        	xc   = Double.parseDouble(args[0]);
            yc   = Double.parseDouble(args[1]);
            size = Double.parseDouble(args[2]);
            numberOfThreads = Integer.parseInt(args[3]);
        }

        int n   = 720;   // create n-by-n image
        int max = 255;   // maximum number of iterations
        
        System.out.println("Running parallel mandelbrot set with " + numberOfThreads + " threads.");
        
        Buffer buffer = new Buffer(numberOfThreads);	// buffer for tasks and start of threads
        Picture picture = new Picture(n, n);			// picture initialization for output image
        for (int i = 0; i < n; i+=80) {					// 80x80 pixels each task
        	for (int j = 0; j < n; j+=80) {
        		int initial_x = i;
        		int initial_y = j;
        		
        		// need first X and Y to form a square with know size, that is 80x80 pixels
        		// pass xc, yc and size from args, size of n-n image, max number of iterations
        		// and picture to set the pixels
        		CalculateColor task = new CalculateColor(initial_x, initial_y, xc, yc, size, n, max, picture);
        		buffer.push(task);
        	}
        }
        
        // kill all threads sendind a task with stop command
        for (int i = 0; i < numberOfThreads; i++) {
        	CalculateColor task = new CalculateColor();
        	buffer.push(task);
        }
    }
}
