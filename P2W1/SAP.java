//command: java SAP digraph1.txt
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
public class SAP {
   private Digraph mGraph;
   
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
   private Node[][] cache;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
       mGraph = G;
       cache = new Node[G.V()][G.V()];
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
       if (cache[v][w] != null)
           return cache[v][w].getLength();
       int[] reach = new int[mGraph.V()];
       return BFS(v, w, reach, mGraph.E());
   }
   
   private int BFS(int v, int w,  int[] reach, int bestSoFar)
   {
       //int[] reach = new int[mGraph.V()];
       if (cache[v][w] != null)
           return cache[v][w].getLength();
       LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
       int left = 0;
       int l = 0;
       queue.enqueue(v);
       //int bestSoFar = mGraph.E();
       int ancestor = v;
       while (!queue.isEmpty())
       {
           if (left == 0)
           {
               left += queue.size();
               l++;
           }
           //StdOut.println(Arrays.toString(reach));
           int curr = queue.dequeue();
           left--;
           if(curr == w)
           {
               bestSoFar = l;
               ancestor = w;
           }
           if(curr != v && (reach[curr] == 0 || reach[curr] > l - 1))
           {
               reach[curr] = l - 1;
           }
           for (Integer m : mGraph.adj(curr))
           {
               queue.enqueue(m);
           }
       }
       
       queue.enqueue(w);
       left = 0;
       l = 0;
       while (!queue.isEmpty())
       {
           if (left == 0)
           {
               left += queue.size();
               l++;
           }
           if (l > bestSoFar)
           {
               break;
           }
           int curr = queue.dequeue();
           left--;
           if (curr == v || reach[curr] > 0)
           {
                   //StdOut.println(curr + " : " + reach[curr]);
                   if (bestSoFar > reach[curr] + l - 1)
                   {
                       bestSoFar = reach[curr] + l - 1;
                       ancestor = curr;
                   }
           }
           if(curr != w && reach[curr] == 0)
           {
               reach[curr] = l - 1;
           }
           for (Integer m : mGraph.adj(curr))
           {
               queue.enqueue(m);
           }
       }
       
       if(bestSoFar < mGraph.E())
       {
           cache[v][w] = new Node(ancestor, bestSoFar);
           cache[w][v] = new Node(ancestor, bestSoFar);
           return bestSoFar;
       }
       return -1;
   }
   

   // a common ancestor of v and w that participates 
   // in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
       if (cache[v][w] != null)
           return cache[v][w].getNode();
       int k = length(v,w);
       if (cache[v][w] != null)
           return cache[v][w].getNode();
       return -1;
   }

   // length of shortest ancestral path between any vertex 
   // in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       int bestSoFar = mGraph.E();
       //int bestAncestor = -1;
       int[] reach = new int[mGraph.V()];
       for (int mV : v)
       {
           for (int mW : w)
           {
               Arrays.fill(reach, 0);
               int temp = BFS(mV,mW,reach,bestSoFar);
               if (temp > 0 && temp < bestSoFar)
               {
                   bestSoFar = temp;
                   //bestAncestor = ancestor(mV,mW);
               }
           }
       }
       if (bestSoFar == mGraph.E())
           return -1;
       return bestSoFar;
   }

   // a common ancestor that participates in shortest 
   // ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       int bestSoFar = mGraph.E();
       int bestAncestor = -1;
       int[] reach = new int[mGraph.V()];
       for (int mV : v)
       {
           for (int mW : w)
           {
               Arrays.fill(reach, 0);
               int temp = BFS(mV,mW,reach,bestSoFar);
               if (temp > 0 && temp < bestSoFar)
               {
                   bestSoFar = temp;
                   bestAncestor = ancestor(mV,mW);
               }
           }
       }
       return bestAncestor;
   }

   // do unit testing of this class
   public static void main(String[] args)
       {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
       StdOut.println("Graph : \n" + G);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
   }
}