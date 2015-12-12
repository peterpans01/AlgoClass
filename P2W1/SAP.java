//command: java SAP digraph1.txt
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

//===========================================================================
public class SAP {
   private Digraph mGraph;
   private static final int INFINITY = Integer.MAX_VALUE;
   private int[] distToW;
   private int[] distToV;
   private int[] reachFromW;
   private int[] reachFromV;
   private int index;
   private Queue<Integer> queue;
   private class Node
   {
       private int n;
       private int length;
       public Node(int n, int length)
       {
           this.n = n;
           this.length = length;
       }
       public int getNode()
       {
           return this.n;
       }
       public int getLength()
       {
           return this.length;
       }
   }
   
   //private Queue<Interable<int>> recently;
   
   public SAP(Digraph G)
   {
       mGraph = new Digraph(G);
       distToW = new int[mGraph.V()];
       distToV = new int[mGraph.V()];
       reachFromV = new int[mGraph.V()];
       reachFromW = new int[mGraph.V()];
       queue = new Queue<Integer>();
       index = 0;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
       Node t = BFS(v,w);
       return t.getLength();
   }
   private Node BFS(Iterable<Integer> v, Iterable<Integer> w)
   {
       
       
       //distToV = new int[mGraph.V()];
       //for (int v1 = 0; v1 < mGraph.V(); v1++)
            //distToV[v1] = -1;
       if(index == INFINITY)
       {
           for (int v1 = 0; v1 < mGraph.V(); v1++)
           {
               reachFromV[v1] = 0;
               reachFromW[v1] = 0;
           }
           index = 0;
       }
       index++;
       for (int s : v) {
            
            distToV[s] = 0;
            reachFromV[s] = index;
            queue.enqueue(s);
        }
        BFSLoop();
        
       
       //for (int v1 = 0; v1 < mGraph.V(); v1++)
       //     distToW[v1] = -1;
       for (int s2 : w) {
            reachFromW[s2] = index;
            distToW[s2] = 0;
            queue.enqueue(s2);
        }
       return BFSFinder();
   }
   
   private void BFSLoop()
   {
       
       while (!queue.isEmpty())
       {
           int curr = queue.dequeue();
           for (Integer m : mGraph.adj(curr))
           {
               if (reachFromV[m] < index) {
                   reachFromV[m] = index;
                   distToV[m] = distToV[curr] + 1;
                   queue.enqueue(m);
               }
           }
       }
   }
   private Node BFSFinder()
   {
       int bestSoFar = INFINITY;
       int ancestor = -1;
       while (!queue.isEmpty())
       {
           int curr = queue.dequeue();
           if(reachFromV[curr] == index)
           {
               if (bestSoFar > distToV[curr] + distToW[curr])
               {
                   bestSoFar = distToV[curr] + distToW[curr];
                   ancestor = curr;
               }
           }
           for (Integer m : mGraph.adj(curr))
           {
               if (reachFromW[m] < index)
               {
                     queue.enqueue(m);
                     reachFromW[m] = index;
                     distToW[m] = distToW[curr] + 1;
               }
           }
       }
       if (ancestor == -1)
           bestSoFar = -1;
       while (!queue.isEmpty())
       {
           queue.dequeue();
       }
       return new Node(ancestor, bestSoFar);
   }
   
   private Node BFS(int v, int w)
   {
       if (v == w)
           return new Node(v, 0);
//       Queue<Integer> queue = new Queue<Integer>();
//       int[] distToV = new int[mGraph.V()];
//        for (int v1 = 0; v1 < mGraph.V(); v1++)
//            distToV[v1] = -1;
       if(index == INFINITY)
       {
           for (int v1 = 0; v1 < mGraph.V(); v1++)
           {
               reachFromV[v1] = 0;
               reachFromW[v1] = 0;
           }
           index = 0;
       }
       index++;
       queue.enqueue(v);
       reachFromV[v] = index;
       distToV[v] = 0;
       BFSLoop();
       
       
      
//       while (!queue.isEmpty())
//       {
////           if (left == 0)
////           {
////               left += queue.size();
////               l++;
////           }
//           //StdOut.println(Arrays.toString(reach));
//           int curr = queue.dequeue();
//           
//           //left--;
////           if(curr == w)
////           {
////               bestSoFar = l;
////               ancestor = w;
////           }
////           if(curr != v && (reach[curr] == 0 || reach[curr] > l - 1))
////           {
////               reach[curr] = l - 1;
////           }
//           for (Integer m : mGraph.adj(curr))
//           {
//               if (distTo[m] == -1) {
//                    //edgeTo[m] = curr;
//                    distTo[m] = distTo[curr] + 1;
//                    //group[m] = group[curr]; 
//                    //marked[m] = true;
//                   queue.enqueue(m);
//               }
//           }
//       }
       
 
       queue.enqueue(w);
       //int[] distToW = new int[mGraph.V()];
       //for (int v1 = 0; v1 < mGraph.V(); v1++)
       //     distToW[v1] = -1;
       distToW[w] = 0;
       return BFSFinder();
   }
   

   // a common ancestor of v and w that participates 
   // in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
       Node t = BFS(v,w);
       return t.getNode();
   }

   // length of shortest ancestral path between any vertex 
   // in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       Node t = BFS(v,w);
       return t.getLength();
   }
   
   // a common ancestor that participates in shortest 
   // ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       Node t = BFS(v,w);
       return t.getNode();
   }

   // do unit testing of this class
   public static void main(String[] args)
       {
    In in = new In(args[0]);
    boolean t = false;
    StdOut.println("Chuoi : |" + args[1] + "|" );
    if (args[1].equals("c"))
    {
        t = true;
    }
    Digraph G = new Digraph(in);
       
    SAP sap = new SAP(G);
    if (t)
    {
        
        while (!StdIn.isEmpty()) {
            String v1 = StdIn.readLine();
            String[] s1 = v1.split(" ");
            Queue<Integer> v = new Queue<Integer>();
            for (int i = 0; i < s1.length; i++)
            {
                v.enqueue(Integer.parseInt(s1[i]));
            }
            
            String v2 = StdIn.readLine();
            String[] s2 = v2.split(" ");
            Queue<Integer> w = new Queue<Integer>();
            for (int i = 0; i < s2.length; i++)
            {
                w.enqueue( Integer.parseInt(s2[i]));
            }
            
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
    else
    {
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
   }
   }
}