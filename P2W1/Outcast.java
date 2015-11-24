
//command: 
//java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
   // constructor takes a WordNet object
   private WordNet wn;
   //private static final int INFINITY = Integer.MAX_VALUE;
   public Outcast(WordNet wordnet)
   {
       wn = wordnet;
   }
   
   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns)
   {
       String k = "";
       int max = 0;
       for (int i = 0; i < nouns.length; i++)
       {
           int dis = 0;
           for (int j = 0; j < nouns.length; j++)
           {
               dis += wn.distance(nouns[i], nouns[j]);
           }
           //StdOut.println( nouns[i] +  " : " + dis);
           if (dis > max)
           {
               max = dis;
               k = nouns[i];
           }
       }
       return k;
   }
   
   // see test client below
   public static void main(String[] args)
   {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
   }
}