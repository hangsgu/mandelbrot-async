package mandelbrot;

import java.awt.*;

/******************************************************************************
 *  Compilation:  javac mandelbrot.Mandelbrot.java
 *  Execution:    java mandelbrot.Mandelbrot xc yc size
 *  Dependencies: StdDraw.java
 *
 *  Plots the size-by-size region of the mandelbrot.Mandelbrot set, centered on (xc, yc)
 *
 *  % java mandelbrot.Mandelbrot -0.5 0 2
 *
 ******************************************************************************/

public class Mandelbrot {

    // return number of iterations to check if c = a + ib is in mandelbrot.Mandelbrot set
    public static int mand(Complex z0, int max) {
        Complex z = z0;
        for (int t = 0; t < max; t++) {
            if (z.abs() > 2.0) return t;
            z = z.times(z).plus(z0);
        }
        return max;
    }

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

        Picture picture = new Picture(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x0 = xc - size/2 + size*i/n;
                double y0 = yc - size/2 + size*j/n;
                Complex z0 = new Complex(x0, y0);
                int gray = max - mand(z0, max);
                Color color = new Color(gray, gray, gray);
                picture.set(i, n-1-j, color);

                System.out.println(color.toString());
            }
        }
        picture.show();
    }
}