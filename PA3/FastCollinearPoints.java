/**Version with Pair of Integer for purpose of reduce memory using 
 * for thi program didnt work out. Ignore it and think about 
 * different solution.
 */ 




import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.QuickX;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

//import java.lang.NullPointerException;
//import java.lang.IllegalArgumentException;
import java.util.*;
//import java.util.Comparator;
public class FastCollinearPoints {
  
   //private int N;
   private class Pair
   {
       private int i;
       private int j;
       public Pair(P p, int q)
       {
           i = p;
           j = q;
       }
       public int f()
       {
           return i;
       }
       public int s()
       {
           return j;
       }
       public boolean equal(Pair other)
       {
           return i == other.i && j == other.j;
       }
   }
   //private class IndexComparator
   //private LinkedList<LineSegment> result;
   private LinkedList<Pair> result;
   private class IntegerComparator implements Comparator<Integer>
   {
       public SortType {BYPOINT, BYSLOPE}
       private Point[] myPoints;
       //private SortType myST; 
       private int myidr;
       public IntegerComparator( Point[] outPoints, SortType st, int idr)
       {
           myPoints = outPoints;
           if (st == BYPOINT)
           {
               myidr = -1;
           }
           else
           {
               myidr = idr;
           }
       }
       public void setSortType(SortType st,int idr)
       {
           if (st == BYPOINT)
           {
               myidr = -1;
           }
           else
           {
               myidr = idr;
           }
       }
           
       public int compare(int u, int v)
       {
           if (myidr == -1)
           {
               return myPoints[u].compareTo(myPoints[v]);
           }
           else
           {
               return myPoints[myidr].slopeOrder().compare(myPoints[u], myPoints[v]);
           }
       }
       
   }
   public FastCollinearPoints(Point[] points)     
   // finds all line segments containing 4 or more points
   {
       //Handle corner case
       //Point[] m_points = new Point[points.length];
       //System.arraycopy(points, 0, m_points, 0, points.length);
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
       IntegerComparator ic = new IntegerComparator(points, SortType.BYPOINT, 0);
       
       int[] ind = new int[size];
       for (int i = 0; i < size; i++)
       {
           ind[i] = i;
       }
       
       //Arrays.sort(m_points,ic);
       Arrays.sort(ind,ic);
//       for (int i = 0; i < size -1; i++)
//       {
//           if (m_points[i].compareTo(m_points[i+1]) == 0)
//               throw new java.lang.IllegalArgumentException();
//       }
       for (int i = 0; i < size -1; i++)
       {
           if (points[ind[i]].compareTo(points[ind[i+1]]) == 0)
               throw new java.lang.IllegalArgumentException();
       }
       
//       result = new LinkedList<LineSegment>();
       result = new LinkedList<Pair>();
       //Point[] arraysort = new Point[size];
       //System.arraycopy(m_points , 0 , arraysort , 0 , size);
       //aplly fast algorithm
       for (int i = 0; i < size; i++)
       {
           ic.setSortType(SortType.BYSLOPE,i);
           //Arrays.sort(arraysort , m_points[i].slopeOrder());
           Arrays.sort(ind, ic);
           int start = 1, end = 1;
           
           for (int j = 2; j < size; j++)
           {
               //double is = m_points[i].slopeTo(arraysort[start]);
               //double ij = m_points[i].slopeTo(arraysort[j]); 
               double is = points[i].slopeTo(points[ind[start]]);
               double ij = points[i].slopeTo(points[ind[j]]);
               if (is != ij || (j == size -1))
               {
                   if (j == size -1 && is == ij)
                   {
                       end = size - 1;
                   }
                   
                   if (end-start > 1)
                   {
                       //Point tempStart = m_points[i];
                       //Point tempEnd = m_points[i];
                       int tempStart = i; 
                       int tempEnd = i;
                       boolean add = false;
                       for(int k = start; k<=end; k++)
                       {
//                           if (points[ind[k]].compareTo(tempStart) < 0)
//                           {
//                               tempStart = arraysort[k];
//                           }
                           if (points[ind[k]].compareTo(points[tempStart]) < 0)
                           {
                               tempStart = ind[k];
                           }
//                           if (arraysort[k].compareTo(tempEnd) > 0)
//                           {
//                               tempEnd = arraysort[k];
//                           }
                           if (points[ind[k]].compareTo(points[tempEnd]) > 0)
                           {
                               tempEnd = ind[k];
                           }
                       }
//                       if (m_points[i].compareTo(tempStart) == 0)
//                       {
//                           LineSegment temp = new LineSegment(tempStart, tempEnd);
//                           result.add(temp);
//                       }
                       if (i == tempStart)
                       {
                           Pair temp = new Pair(tempStart, tempEnd);
                           result.add(temp);
                       }
                   }
                   start = j;
                   end = j;
               }
               else
               {
                   end = j;
               }
           } 
       }
   }
   public int numberOfSegments()        // the number of line segments
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}
}
