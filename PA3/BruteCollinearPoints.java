import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.QuickX;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


//import java.lang; //.NullPointerException
//import java.lang.IllegalArgumentException;
//import java.util.Arrays;

public class BruteCollinearPoints {
   private ResizingArrayBag<LineSegment> result;
   
   public BruteCollinearPoints(Point[] points)    
    // finds all line segments containing 4 points
   {
       Point[] m_points = new Point[points.length];
       System.arraycopy(points, 0, m_points, 0, points.length);
       //Handle corner case
       if (points == null)
       {
           throw new java.lang.NullPointerException();
       }
       int size = points.length;
       for (int i = 0; i < size; i++)
       {
           if (points[i] == null)
               throw new java.lang.NullPointerException();
       }
       QuickX.sort(m_points);
       for (int i = 0; i < size -1; i++)
       {
           if ( m_points[i].compareTo(m_points[i+1]) == 0)
               throw new java.lang.IllegalArgumentException();
       }
       
       result = new ResizingArrayBag<LineSegment>();
       //generate the next combination base on the assumption we are working on
       // the index of array ( so the set is: {0,1,2,..,N-1}) and we need 
       //choose 4 from them. The algorithm is:
       // For example with set of 10 you have this combinations: 0, 2, 8, 9
       // To compute the next combination, you go from right: 
       // 9 -> reach to maximum value (it is the 4elemt element)
       // 8 -> reach to maximum value (it is 3rd last element 
       //          so max = 10 - 3 + 2 (2 is index in array))
       // 2 -> first element not reach the max value -> + 1
       // reset from here to the last element
       if (m_points.length > 3)
       {
           int[] com = new int[4];
           com[0] = 0; 
           com[1] = 1; 
           com[2] = 2; 
           com[3] = 3;
           boolean last = false;
       
       while (!last)
       {
           last = true;
           //handle the point here
           double pq = m_points[com[0]].slopeTo(m_points[com[1]]);
           double pr = m_points[com[0]].slopeTo(m_points[com[2]]);
           double ps = m_points[com[0]].slopeTo(m_points[com[3]]);
           if (pq == pr && pq == ps)
           {
               LineSegment temp = new LineSegment(m_points[com[0]], m_points[com[3]]);
               result.add(temp);
           }
           
           for (int i = 3; i >= 0; i--)
           {
              if (com[i] < size-4+i)
              {
                 com[i] += 1;
                 for (int j = i+1; j < 4; j++)
                 {
                     com[j] = com[i]+ j - i;
                 }
                last = false; 
                break;
               
              }
           }
       }
       }
       
       
   }
   public  int numberOfSegments()        // the number of line segments
   {
       return result.size();
   }
   public LineSegment[] segments()                // the line segments
   {
       LineSegment[] r = new LineSegment[result.size()];
       int i = 0;
       for (LineSegment l: result)
       {
           r[i] = l;
           i++;
       }
       return r;
   }
   public static void main(String[] args) {

    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}
}
