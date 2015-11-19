import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import java.util.Arrays;


public class WordNet {
   private class Synset
   {
       public int id;
       public String synonym;
       //public String gloss;
   }
   private Stack<Synset> mSyn;
   //private ST<Integer, int[]> mHyper;
   private Digraph mDi;
   private int nSyn;
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if (synsets == null || hypernyms == null)
           throw new java.lang.NullPointerException();
       StdOut.println("---1-----");
       nSyn = 0;
       getSynset(synsets);
       StdOut.println("---2-----");
       getHyper(hypernyms);
       StdOut.println("---3-----");
       
   }
   //java WordNet synsets3.txt hypernyms3InvalidTwoRoots.txt
   private void getSynset(String synsets)
   {
       In in = new In(synsets);
       mSyn = new Stack<Synset>();
       
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            Synset a = new Synset();
            a.id = Integer.parseInt(tokens[0]);
            a.synonym = tokens[1];
            //a.gloss = tokens[2];
            mSyn.push(a);
            nSyn++;
        }
   }
   private void getHyper(String hypernyms)
   {
       In in = new In(hypernyms);
       mDi = new Digraph(in);
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
       Queue<String> s = new Queue<String>();
       for (Synset syn : mSyn)
       {
           s.enqueue(syn.synonym);
       }
       return s;
   }
//   public void printMSyn()
//   {
//       for (Synset syn : mSyn)
//       {
//           StdOut.println(syn.id + " | " + syn.synonym);
//       }
//   }
//   public void printHyper()
//   {
//       for (int id : mHyper.keys())
//       {
//           StdOut.println(id + " | " + Arrays.toString(mHyper.get(id)));
//       }
//   }
   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
       return true;
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
       return 0;
   }

   // a synset (second field of synsets.txt) 
   //that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
       return "a";
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       WordNet a = new WordNet(args[0], args[1]);
       a.printMSyn();
       a.printHyper();
   }
}