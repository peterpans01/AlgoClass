import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
   // construct an empty set of points
   private static class Node
   {
       private Point2D p;   // the point
       private RectHV rect; // the axis-aligned rectangle corresponding to this node
       private Node lb;     // the left/bottom subtree
       private Node rt;     // the right/top subtree
       public Node(Point2D iP, RectHV iRect) {
            this.p = iP;
            this.rect = iRect;
        }
       //public void setRect(RectHV )
   }
   private Node mRoot;
   private int mSize;
   public         KdTree() 
   {
       mRoot = null;
       mSize = 0;
   }
   // is the set empty?
   public           boolean isEmpty()
   {
       return mRoot == null;
   }
   // number of points in the set
   public               int size()
   {
       if (mRoot == null)
       {
           return 0;
       }
       return mSize;
   }
   // add the point to the set (if it is not already in the set)
   public              void insert(Point2D p)
   {
       if (contains(p))
       {
           StdOut.println("Point " + p + " missing from list");
           return;
       }
       mRoot = insert(mRoot, p, new RectHV(0.0, 0.0, 1.0, 1.0), true);
       mSize++;
   }
   private Node insert(Node x, Point2D iP, RectHV iRect, boolean compare)
   {
       if (x == null) return new Node(iP, iRect);
       int cmp = compare2(iP, x.p, compare);
       RectHV tmp;
       if (cmp < 0) 
       {
           
           if (compare)
           {
              tmp = new RectHV(iRect.xmin(), iRect.ymin(), x.p.x(), iRect.ymax());
           }
           else
           {
               tmp = new RectHV(iRect.xmin(), iRect.ymin(), iRect.xmax(), x.p.y());
           }
           x.lb  = insert(x.lb,  iP, tmp, !compare);
       }
       else //if (cmp > 0) 
       {
           if (compare)
           {
              tmp = new RectHV(x.p.x(), iRect.ymin(), iRect.xmax(), iRect.ymax());
           }
           else
           {
               tmp = new RectHV(iRect.xmin(), x.p.y(), iRect.xmax(), iRect.ymax());
           }
           x.rt = insert(x.rt, iP, tmp, !compare);
       }
       //else              x.p   = iP;
       
       return x;
       
   }
   private int compare2(Point2D x1, Point2D x2, boolean compare)
   {
       if (compare)
       {
          if (x1.x() < x2.x()) return -1;
          if (x1.x() > x2.x()) return 1;
          return  0;
       }
       else
       {
           if (x1.y() < x2.y()) return -1;
          if (x1.y() > x2.y()) return 1;
          return  0;
       }
   }
   
   // does the set contain point p? 
   public           boolean contains(Point2D p)
   {
       return contains(mRoot, p, true);
   }
   private boolean contains(Node x, Point2D p, boolean compare)
   {
       if (x == null) return false;
       int cmp = compare2(p, x.p, compare);
       if (cmp < 0) return contains(x.lb, p, !compare);
       else return contains(x.rt, p, !compare); //if (cmp > 0)
       //else return true;
   }
   // draw all points to standard draw
   public              void draw()
   {
       draw(mRoot, true);
   }
   private void draw(Node x, boolean compare)
   {
       if (x == null) return;
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(.01);
       x.p.draw();
       if (compare)
       {
           StdDraw.setPenColor(StdDraw.RED);
           StdDraw.setPenRadius(.005);
           StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
       }
       else
       {
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.setPenRadius(.005);
           StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
       }
       draw(x.lb, !compare);
       draw(x.rt, !compare);
   }
   // all points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)  
   {
       Queue<Point2D> points = new Queue<Point2D>();
       Stack<Node> iter = new Stack<Node>();
       iter.push(mRoot);
       while (!iter.isEmpty())
       {
           Node current = iter.pop();
           if (rect.contains(current.p))
           {
               points.enqueue(current.p);
           }
           if (current.rt != null && rect.intersects(current.rt.rect))
           {
               iter.push(current.rt);
           }
           if (current.lb != null && rect.intersects(current.lb.rect))
           {
               iter.push(current.lb);
           }
       }
      return points;
   }
   
   
   // a nearest neighbor in the set to point p; null if the set is empty
   public           Point2D nearest(Point2D p)
   {
       Point2D nearestPoint = mRoot.p;
       Stack<Node> iter = new Stack<Node>();
       iter.push(mRoot);
       double dis = 1.0;
       int count = 0;
       while (!iter.isEmpty())
       {
           Node current = iter.pop();
           double temp = p.distanceSquaredTo(current.p);
           if (temp < dis)
           {
               nearestPoint = current.p;
               dis = temp;
           }
           count = 0;
           if (current.rt != null && current.rt.rect.distanceSquaredTo(p) < dis)
           {
               //iter.push(current.rt);
               count += 1;
           }
           if (current.lb != null && current.lb.rect.distanceSquaredTo(p) < dis)
           {
               //iter.push(current.lb);
               count += 2;
           }
           if (count == 1)
           {
               iter.push(current.rt);
           }
           if (count == 2)
           {
               iter.push(current.lb);
           }
           if (count == 3)
           {
               if (current.lb.rect.contains(p))
               {
                   iter.push(current.lb);
                   iter.push(current.rt);
               }
               else
               {
                   iter.push(current.rt);
                   iter.push(current.lb);
               }
           }
       }
      return nearestPoint;
   }
   
   // unit testing of the methods (optional)
   public static void main(String[] args)
   {
       String filename = args[0];
        In in = new In(filename);

        //StdDraw.show(0);

        // initialize the two data structures with point from standard input
        SET<Point2D> brute = new SET<Point2D>();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.add(p);
        }
//        boolean result = true;
//       for (Point2D p : brute)
//       {
//           if ( !kdtree.contains(p))
//           {
//               result = false;
//               break;
//           }
//       }
//       StdOut.println("Result :" + result);
   }
}