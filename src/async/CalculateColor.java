package async;

import mandelbrot.Complex;
import mandelbrot.Picture;

import java.awt.*;

public class CalculateColor implements Runnable {
    private int initial_x;
    private int initial_y;
    private final double xc;
    private final double yc;
    private final double size;
    private final int n;
    private final int maxIterations;
    private static Picture picture;
    private boolean shouldStop;
    
    public CalculateColor(int initial_x, int initial_y, double xc, double yc, double size, int n, int maxIterations, Picture pic) {
    	this.initial_x = initial_x;
    	this.initial_y = initial_y;
    	this.xc = xc;
    	this.yc = yc;
    	this.size = size;
    	this.n = n;
    	this.maxIterations = maxIterations;
    	picture = pic;
    	this.shouldStop = false;
    }
    
    // object to kill threads
    public CalculateColor() {
    	this.xc = 0;
    	this.yc = 0;
        this.size = 0;
        this.n = 0;
        this.maxIterations = 0;
        this.shouldStop = true;
    }
    
    // checks if a complex number is in the mandelbrot set
    public float numberInMandelbrotSet(Complex number, int maxIterations) {
    	Complex z = number;
    	for (int t = 0; t < maxIterations; t++) {
    		double fAbs = z.abs();
            if (fAbs > 2.0) {
                // based on the final value, add a fractional amount based on
                // how much it escaped by (fAbs will be in the range of 2 to around 4):                 
                return (float) ((float)t + (2.0f - (Math.log(fAbs) / Math.log(2.0))));
            }
            z = z.times(z).plus(number);
        }
    	return (float)maxIterations;
    }
    
    public boolean getStop() {
    	return this.shouldStop;
    }
    
    @Override
    public void run() {
    	float result;
    	float value;
    	float hue;
    	
    	for (int i = initial_x; i < initial_x + 80; i++) {			// + 80 is window division, always the same to form a square
    		for (int j = initial_y; j < initial_y + 80; j++) {
    			double x = xc - size/2 + size*i/n;
    			double y = yc - size/2 + size*j/n;
    			Complex number = new Complex(x, y);
    			
    			result = numberInMandelbrotSet(number, maxIterations);
                hue = result / (float)maxIterations;
    			
    			if (result == maxIterations) {
    				value = 0.0f;
    			} else {
    				value = 1.0f;
    			}

    			Color color = Color.getHSBColor(hue, 1.0f, value);
    	        picture.set(i, n-1-j, color);						// set color in picture
    		}
    	}
    	
    	picture.show();
    }
}
