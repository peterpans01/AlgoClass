import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stack;
//import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.DirectedCycle;
//import java.util.Arrays;


public class WordNet {
//   private class Synset
//   {
//       public int id;
//       public String synonym;
//        
//       //public String gloss;
//   }
   private LinearProbingHashST<Integer, Stack<String>> stID;
   private LinearProbingHashST<String, Stack<Integer>> stSyn;
   //private Stack<Synset> mSyn;
   //private ST<Integer, int[]> mHyper;
   private SAP sa;
   private int nSyn;
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if (synsets == null || hypernyms == null)
           throw new java.lang.NullPointerException();
       //StdOut.println("---1-----");
       nSyn = 0;
       getSynset(synsets);
       //StdOut.println("---2-----");
       getHyper(hypernyms);
       //StdOut.println("---3-----");
       
   }
   //java WordNet synsets3.txt hypernyms3InvalidTwoRoots.txt
   private void getSynset(String synsets)
   {
       In in = new In(synsets);
       stID = new LinearProbingHashST<Integer, Stack<String>>();
       stSyn = new LinearProbingHashST<String, Stack<Integer>>();
       
        while (in.hasNextLine()) {
            nSyn++;
            String line = in.readLine();
            String[] tokens = line.split(",");
            
            int t = Integer.parseInt(tokens[0]);
            String[] ws = tokens[1].split(" ");
            for (int i = 0; i < ws.length; i++)
            {
                String w = ws[i];
                //stID.put(t, w);
                if (stID.contains(t))
                {
                    stID.get(t).push(w);
                }
                else
                {
                    Stack<String> t1 = new Stack<String>();
                     t1.push(w);
                     stID.put(t,t1);
                }
                
                if (stSyn.contains(w))
                {
                    stSyn.get(w).push(t);
                }
                else
                {
                   Stack<Integer> t1 = new Stack<Integer>();
                   t1.push(t);
                   stSyn.put(w,t1);
                }
            }
            
            
            //a.gloss = tokens[2];
//            mSyn.push(a);
            //nSyn++;
        }
   }
   private boolean isRootedDAG(Digraph G)
   {
       DirectedCycle dc = new DirectedCycle(G);
       if (dc.hasCycle())
           return false;
       int count = 0;
       for (int i = 0; i < G.V(); i++)
       {
           if (G.outdegree(i) == 0)
               count++;
       }
       if(count != 1)
           return false;
//       boolean[] marked = new boolean[G.V()];
//       int root = -1;
//       int count = 0;
//       int next = 0;
//       Stack<Integer> s = new Stack<Integer>();
//       while (count < G.V())
//       {
//           for (int i = 0; i < G.V(); i++)
//           {
//               if (!marked[i])
//               {
//                   next = i;
//                   break;
//               }
//           }
//           s.push(next);
//           count++;
//           while (!s.isEmpty())
//           {
//               int c = s.pop();
//               int c1 = 0;
//               for (int a : G.adj(c))
//               {
//                   c1++;
//                   marked[a] = true;
//                   count++;
//                   s.push(a);
//               }
//               if(c1 == 0)
//               {
//                   if(root > 0 && root != c)
//                   {
//                       return false;
//                   }
//                   if(root < 0)
//                   {
//                       root = c;
//                   }
//               }
//           }
//           
//       }
       return true;
       
   }
   private void getHyper(String hypernyms)
   {
       In in = new In(hypernyms);
       Digraph mDi = new Digraph(nSyn);
        while (in.hasNextLine()) {
       String line = in.readLine();
       String[] tokens = line.split(",");
       int u = Integer.parseInt(tokens[0]);
       for (int i = 1; i < tokens.length; i++)
       {
           mDi.addEdge(u, Integer.parseInt(tokens[i]));
       }
     }
       //check digraph is rooted DAG or not.
       if (!isRootedDAG(mDi))
           throw new java.lang.IllegalArgumentException();
       sa = new SAP(mDi);
       //StdOut.println("========================");
       //StdOut.println(mDi);
       //StdOut.println("========================");
//       mHyper = new ST<Integer, int[]>();
//       while (in.hasNextLine()) {
//            String line = in.readLine();
//            String[] tokens = line.split(",");
//            
//            Integer id = Integer.parseInt(tokens[0]);
//            int[] temp = new int[tokens.length - 1];
//            int c = 0;
//            //StdOut.println("tokens.length :" + tokens.length);
//            while(c < tokens.length - 1)
//            {
//                temp[c] = Integer.parseInt(tokens[c+1]);
//                c++;
//            }
//            StdOut.println("temp :" + temp.length);
//            mHyper.put(id, temp);
//        }
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
//       Queue<String> s = new Queue<String>();
//       for (Synset syn : mSyn)
//       {
//           s.enqueue(syn.synonym);
//       }
//       return s;
       return stSyn.keys();
   }
//   public void printMSyn()
//   {
//       for (String syn : stSyn.keys())
//       {
//           StdOut.println(syn + " | " + stSyn.get(syn));
//       }
//   }
//   public void printHyper()
//   {
//       for (int id : stID.keys())
//       {
//           StdOut.println(id + " | " + stID.get(id));
//       }
//   }
   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
       if (word == null)
       {
           throw new java.lang.NullPointerException();
       }
       return stSyn.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
       if (nounA == null || nounB == null)
       {
           throw new java.lang.NullPointerException();
       }
       if (isNoun(nounA) && isNoun(nounB))
       {
           if (nounA == nounB)
               return 0;
           Stack<Integer> idA = stSyn.get(nounA);
           Stack<Integer> idB = stSyn.get(nounB);
           return sa.length(idA, idB);
       }
       else
           throw new java.lang.IllegalArgumentException();
   }

   // a synset (second field of synsets.txt) 
   //that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
       if (nounA == null || nounB == null)
       {
           throw new java.lang.NullPointerException();
       }
       if(isNoun(nounA) && isNoun(nounB))
       {
           if (nounA == nounB)
               return nounA;
           Stack<Integer> idA = stSyn.get(nounA);
           Stack<Integer> idB = stSyn.get(nounB);
           return stID.get(sa.ancestor(idA, idB)).iterator().next();
       }
       else
           throw new java.lang.IllegalArgumentException();
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
//       WordNet a = new WordNet(args[0], args[1]);
////       while (!StdIn.isEmpty()) {
////           String s1 = StdIn.readString();
////           String s2 = StdIn.readString();
////           StdOut.println(s1 + " - " + s2 + " : " + 
////                          a.distance(s1, s2) + " | " + a.sap(s1, s2));
////       }
//       a.printMSyn();
//       a.printHyper();
   }
}