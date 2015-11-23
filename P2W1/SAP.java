//command: java SAP digraph1.txt
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
//import java.util.Arrays;

//===========================================================================
public class SAP {
   private Digraph mGraph;
   private static final int INFINITY = Integer.MAX_VALUE;
   
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
   //private Node[][] cache;
   //private int[][] cache;
   //private Queue<Interable<int>> recently;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
       mGraph = G;
       //cache = new Node[G.V()][G.V()];
       //cache = new int[G.V()][G.V()];
       //Arrays.fill(cache, -1);
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
//       if (v == w) 
//           return 0;
////       if (cache[v][w] > 0)
////           return cache[v][w].getLength();
//       int[] reach = new int[mGraph.V()];
//       return BFS(v, w, reach, mGraph.E());
//       BreadthFirstDirectedPaths t = new BreadthFirstDirectedPaths(G,v);
//       int l = -1;
//       if (t.hasPathTo(w))
//       {
//           //Node tN = new Node(
//           l = t.distTo(w); 
//       }
//       cache[v][w] = l;
//       cache[w][v] = l;
//       return l;
       Node t = BFS(v,w);
       return t.getLength();
   }
   private Node BFS(Iterable<Integer> v, Iterable<Integer> w)
   {
       Queue<Integer> q = new Queue<Integer>();
       boolean[] marked = new boolean[mGraph.V()];
       int[] distTo = new int[mGraph.V()];
        for (int v1 = 0; v1 < mGraph.V(); v1++)
            distTo[v1] = INFINITY;
       int[] edgeTo = new int[mGraph.V()];
        for (int s : v) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        for (int s2 : w) {
            marked[s2] = true;
            distTo[s2] = 0;
            q.enqueue(s2);
        }
        int ancestor = -1;
       int distance = -1;
        while (!q.isEmpty())
       {
            int curr = q.dequeue();
            for (Integer m : mGraph.adj(curr))
           {
               if (!marked[m]) {
                    edgeTo[m] = curr;
                    distTo[m] = distTo[curr] + 1;
                    marked[m] = true;
                   q.enqueue(m);
               }
               else
               {
                   ancestor = m;
                   distance = distTo[m] + distTo[curr] + 1;
                   break;
               }
           }
           if (ancestor >= 0)
               break;
       }
       return new Node(ancestor, distance);
   }
   
   private Node BFS(int v, int w)
   {
       //int[] reach = new int[mGraph.V()];
//       if (cache[v][w] != null)
//           return cache[v][w].getLength();
       Queue<Integer> queue = new Queue<Integer>();
       boolean[] marked = new boolean[mGraph.V()];
       int[] distTo = new int[mGraph.V()];
       int[] edgeTo = new int[mGraph.V()];
        for (int v1 = 0; v1 < mGraph.V(); v1++)
            distTo[v1] = INFINITY;
       //int left = 0;
       //int l = 0;
       queue.enqueue(v);
       queue.enqueue(w);
       marked[v] = true;
       distTo[v] = 0;
       marked[w] = true;
       distTo[w] = 0;
       //int bestSoFar = mGraph.E();
       int ancestor = -1;
       int distance = -1;
       while (!queue.isEmpty())
       {
//           if (left == 0)
//           {
//               left += queue.size();
//               l++;
//           }
           //StdOut.println(Arrays.toString(reach));
           int curr = queue.dequeue();
           //left--;
//           if(curr == w)
//           {
//               bestSoFar = l;
//               ancestor = w;
//           }
//           if(curr != v && (reach[curr] == 0 || reach[curr] > l - 1))
//           {
//               reach[curr] = l - 1;
//           }
           for (Integer m : mGraph.adj(curr))
           {
               if (!marked[m]) {
                    edgeTo[m] = curr;
                    distTo[m] = distTo[curr] + 1;
                    marked[m] = true;
                   queue.enqueue(m);
               }
               else
               {
                   ancestor = m;
                   distance = distTo[m] + distTo[curr] + 1;
                   break;
               }
           }
           if (ancestor >= 0)
               break;
       }
       return new Node(ancestor, distance);
//       queue.enqueue(w);
//       left = 0;
//       l = 0;
//       while (!queue.isEmpty())
//       {
//           if (left == 0)
//           {
//               left += queue.size();
//               l++;
//           }
//           if (l > bestSoFar)
//           {
//               break;
//           }
//           int curr = queue.dequeue();
//           left--;
//           if (curr == v || reach[curr] > 0)
//           {
//                   //StdOut.println(curr + " : " + reach[curr]);
//                   if (bestSoFar > reach[curr] + l - 1)
//                   {
//                       bestSoFar = reach[curr] + l - 1;
//                       ancestor = curr;
//                   }
//           }
//           if(curr != w && reach[curr] == 0)
//           {
//               reach[curr] = l - 1;
//           }
//           for (Integer m : mGraph.adj(curr))
//           {
//               queue.enqueue(m);
//           }
//       }
//       
//       if(bestSoFar < mGraph.E())
//       {
//           cache[v][w] = new Node(ancestor, bestSoFar);
//           cache[w][v] = new Node(ancestor, bestSoFar);
//           return bestSoFar;
//       }
//       return -1;
   }
   

   // a common ancestor of v and w that participates 
   // in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
//       if (cache[v][w] != null)
//           return cache[v][w].getNode();
//       int k = length(v,w);
//       if (cache[v][w] != null)
//           return cache[v][w].getNode();
//       return -1;
       Node t = BFS(v,w);
       return t.getNode();
   }
//   private int bfs(Digraph G, Iterable<Integer> sources, 
//                    int[] marked, int[] distTo, int[] edgeTo, boolean first) {
//       int l = -1; 
//       int ancestor = -1;
//        Queue<Integer> q = new Queue<Integer>();
//        for (int s : sources) {
//            if(!marked[s])
//            {
//                marked[s] = true;
//                distTo[s] = 0;
//                q.enqueue(s);
//            }
//            else
//            {
//                l = 0;
//                ancestor = s;
//            }
//        }
//        if(l > -1 && ancestor > -1)
//        {
//            return
//        }
//        while (!q.isEmpty()) {
//            int v = q.dequeue();
//            for (int w : G.adj(v)) {
//                if (!marked[w]) {
//                    edgeTo[w] = v;
//                    distTo[w] = distTo[v] + 1;
//                    marked[w] = true;
//                    q.enqueue(w);
//                }
//            }
//        }
//    }
   // length of shortest ancestral path between any vertex 
   // in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       //if (recent)
//       int bestSoFar = mGraph.E();
//       //int bestAncestor = -1;
//       int[] reach = new int[mGraph.V()];
//       for (int mV : v)
//       {
//           for (int mW : w)
//           {
//               Arrays.fill(reach, 0);
//               int temp = BFS(mV,mW,reach,bestSoFar);
//               if (temp > 0 && temp < bestSoFar)
//               {
//                   bestSoFar = temp;
//                   //bestAncestor = ancestor(mV,mW);
//               }
//           }
//       }
//       if (bestSoFar == mGraph.E())
//           return -1;
//       return bestSoFar;
       //return -1;
       Node t = BFS(v,w);
       return t.getLength();
   }
   
   // a common ancestor that participates in shortest 
   // ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
//       int bestSoFar = mGraph.E();
//       int bestAncestor = -1;
//       int[] reach = new int[mGraph.V()];
//       for (int mV : v)
//       {
//           for (int mW : w)
//           {
//               Arrays.fill(reach, 0);
//               int temp = BFS(mV,mW,reach,bestSoFar);
//               if (temp > 0 && temp < bestSoFar)
//               {
//                   bestSoFar = temp;
//                   bestAncestor = ancestor(mV,mW);
//               }
//           }
//       }
//       return bestAncestor;
       //return -1;
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
       //StdOut.println("Graph : \n" + G);
    SAP sap = new SAP(G);
    if (t)
    {
        StdOut.println("Chuoi : ");
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