import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stack;
//import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.DirectedCycle;



public class WordNet {

   private LinearProbingHashST<Integer, String> stID;
   private LinearProbingHashST<String, Stack<Integer>> stSyn;
   private SAP sa;
   private int nSyn;
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if (synsets == null || hypernyms == null)
           throw new java.lang.NullPointerException();
       
       nSyn = 0;
       getSynset(synsets);
       getHyper(hypernyms);
       
   }
   //java WordNet synsets3.txt hypernyms3InvalidTwoRoots.txt
   private void getSynset(String synsets)
   {
       In in = new In(synsets);
       stID = new LinearProbingHashST<Integer, String>();
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
                     stID.put(t,tokens[1]);
               
                
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
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
       return stSyn.keys();
   }
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
           if (nounA.equals(nounB))
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
           if (nounA.equals(nounB))
               return stID.get(stSyn.get(nounA).iterator().next());
           Stack<Integer> idA = stSyn.get(nounA);
           Stack<Integer> idB = stSyn.get(nounB);
           return stID.get(sa.ancestor(idA, idB));
       }
       else
           throw new java.lang.IllegalArgumentException();
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       WordNet a = new WordNet(args[0], args[1]);
       
       while (!StdIn.isEmpty()) {
           String s1 = StdIn.readString();
           String s2 = StdIn.readString();
           StdOut.println(s1 + " - " + s2 + " : " + 
                          a.distance(s1, s2) + " | " + a.sap(s1, s2));
       }
   }
}