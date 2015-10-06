/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.MergeX;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertcal;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        
        double t = that.x - this.x;
        if (t == 0) return Double.POSITIVE_INFINITY;
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (that.y == this.y) return +0.0;
        return (that.y - this.y)/t;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        else return 0;
        
    }
    private class SlopeOrder implements Comparator<Point>
    {
        public int compare(Point u,Point v)
        {
            double s_u = slopeTo(u);
            double s_v = slopeTo(v);
            if (s_u < s_v) return -1;
            else if(s_u > s_v) return 1;
            else return u.compareTo(v);
        }
    }
    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        Comparator<Point> BY_SLOPE = new SlopeOrder();
        return BY_SLOPE;
    }
  

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
//        Point p1 = new Point(0,0);
//        Point p2 = new Point(1,2);
//        Point p3 = new Point(2,3);
//        Point p4 = new Point(3,4);
//        Point p5 = new Point(4,5);
        
//        System.out.println("Slope of " + p2 + " with " + p1 + " is " + p1.slopeTo(p2) );
//        System.out.println("Slope of " + p3 + " with " + p1 + " is " + p1.slopeTo(p3) );
//        System.out.println("Slope of " + p4 + " with " + p1 + " is " + p1.slopeTo(p4) );
//        System.out.println("Slope of " + p5 + " with " + p1 + " is " + p1.slopeTo(p5) );
//        p1.drawTo(p2);
//        p1.drawTo(p3);
//        p1.drawTo(p4);
//        p1.drawTo(p5);
        Point[] a = new Point[5];
        a[0] = new Point(1,0);
        a[3] = new Point(3,3);
        a[4] = new Point(3,4);
        a[1] = new Point(4,5);
        a[2] = new Point(0,5);
        Point r = new Point(0,0);
        MergeX.sort(a, r.slopeOrder());
        
        System.out.println("The order of a is:");
        for (int i = 0; i < 5; i++)
            System.out.println(a[i]);
        System.out.println("==============================");
        
        
    }
}
