import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> mPoints;
    
   // construct an empty set of points
   public         PointSET() 
   {
       mPoints = new SET<Point2D>();
   }
   // is the set empty?
   public           boolean isEmpty()
   {
       return mPoints.isEmpty();
   }
   // number of points in the set
   public               int size()
   {
       return mPoints.size();
   }
   // add the point to the set (if it is not already in the set)
   public              void insert(Point2D p)
   {
       if (p == null)
       {
           throw new java.lang.NullPointerException();
       }
       if (!mPoints.contains(p))
       {
           mPoints.add(p);
       }
   }
   // does the set contain point p? 
   public           boolean contains(Point2D p)
   {
       if (p == null)
       {
           throw new java.lang.NullPointerException();
       }
       return mPoints.contains(p);
   }
   
   // draw all points to standard draw
   public              void draw()
   {
       for (Point2D p1 : mPoints)
       {
           p1.draw();
       }
   }
   // all points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)  
   {
       if (rect == null)
       {
           throw new java.lang.NullPointerException();
       }
       SET<Point2D> setPoint = new SET<Point2D>();
       for ( Point2D p1 :  mPoints)
       {
           if (rect.contains(p1))
           {
               setPoint.add(p1);
           }
       }
       return setPoint;
   }
   // a nearest neighbor in the set to point p; null if the set is empty
   public           Point2D nearest(Point2D p)
   {
       if (p == null)
       {
           throw new java.lang.NullPointerException();
       }
       if (mPoints.isEmpty())
       {
           return null;
       }
       double min = -1.0;
       Point2D nearestPoint = new Point2D(0.0, 0.0);
       for ( Point2D p1 :  mPoints)
       {
           double d = p.distanceTo(p1);
           if (min < 0 || d < min)
           {
               min = d;
               nearestPoint = p1;
           }
       }
       return nearestPoint;
   }
   
   // unit testing of the methods (optional)
   public static void main(String[] args)
   {
       
   }
}