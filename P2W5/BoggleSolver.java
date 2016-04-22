import java.util.HashSet;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import java.util.Iterator;
import java.util.Arrays;
import java.util.*;
//import BoggleBoard;

//command: java BoggleSolver boggle\\dictionary-algs4.txt boggle\\board-q.txt
public class BoggleSolver
{
    //custom Trie
     private static final int R = 26;
    private static class NodeT {
        private NodeT[] next = new NodeT[R];
        private String isString;
        private int length;
        private int count;
        private int ID;
    }
    private class CustomTrieSET implements Iterable<String> {
           // extended ASCII

    public NodeT root;      // root of trie
    private int N;          // number of keys in trie
    
    public CustomTrieSET() {
        N = 0;
    }
    
    public int size()
    {
        return N;
    }
    public boolean contains(String key) {
        NodeT x = get(root, key, 0);
        if (x == null) return false;
        return x.isString != null;
    }
    public NodeT getNode(String key)
    {
        return get(root, key, 0);
    }
    public NodeT getRNodeChar(char key)
    {
        return root.next[key - 'A'];
    }
    public NodeT getNode(NodeT before_x, String key, int d)
    {
        char c = key.charAt(d);
        return before_x.next[c - 'A'];
    }
    public NodeT get(NodeT x, char key)
    {
        if (x == null) return null;
        return x.next[key - 'A'];
    }
    public NodeT get(NodeT x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - 'A'], key, d+1);
    }
    
    public void add(String key) {
        root = add(root, key, 0, 0);
    }

    private NodeT add(NodeT x, String key, int d, int count) {
        if (x == null) x = new NodeT();
        x.count = count + 1;
        x.length = d;
        if (d == key.length()) {
            if (x.isString == null) {
                x.ID = N;
                N++;
            }
            x.isString = key;
        }
        else {
            char c = key.charAt(d);
            x.next[c - 'A'] = add(x.next[c - 'A'], key, d+1, count + 1);
        }
        return x;
    }
    
    public Iterator<String> iterator() {
        return keysWithPrefix("").iterator();
    }
    public boolean containsKeysWithPrefix(String prefix) {
        NodeT x = get(root, prefix, 0);
        return x != null;
    }
    
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        NodeT x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(NodeT x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.isString != null) results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append((char)(c + 'A'));
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    
    
    }
    
    //private HashSet<String> dic;
    private CustomTrieSET dic;
    //private HashSet<String> possibleWords;
    private class Node
    {
        //public int num;
        //public HashSet<Integer> path;
        public int i,j;
        public BitSet path;
        //public String s;
        public NodeT n;
        //public int d;
    }
    private Stack<Node> stack;
    private int[] have;
    private int turn;
//    private class Pair
//    {
//        char ch;
//        int id;
//    }
//    private class Container
//    {
//        public char key;
//        public Bag<Pair> neighbor;
//    }
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
        //dic = new HashSet<String>();
        dic = new CustomTrieSET();
        for(int i = 0; i < dictionary.length; i++)
        {
            if (dictionary[i].length() > 2)
                dic.add(dictionary[i]);
        }
        stack = new Stack<Node>();
        have = new int[dic.size()];
        turn = 0;
        //In in = new In("nums.txt");
        //ref = in.readAllInts();
        
        //StdOut.println("ref : " + Arrays.toString(ref));
        //possibleWords 
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        int N = board.rows();
        int M = board.cols();
        char[] flatBoard = new char[N*M];
        for (int i = 0; i < N; i++)//
        {
            for (int j = 0; j < M; j++)//
            {
               flatBoard[i * M + j] = board.getLetter(i,j);
            }
        }
//        for (int i = 0; i < N * M; i++)//
//        {
//               flatBoard[i] = board.getLetter(i/M,i%M);
//        }
        //HashSet<String> possibleWords = new HashSet<String>();
        Bag<String> possibleWords = new Bag<String>();
        boolean[] path = new boolean[N * M];
        turn++;
        //CustomTrieSET possibleWords = new CustomTrieSET();
//        for (int i = 0; i < N; i++)//
//        {
//            for (int j = 0; j < M; j++)//
//            {
        for (int i = 0; i < N; i++)//
        {
            for (int j = 0; j < M; j++)
            {
                //dfs(board, i, j, dic.root, path, M, N, possibleWords, 0);
                dfs(flatBoard, i, j, dic.root, path, M, N, possibleWords);
//                cur = flatBoard[i*M + j];//board.getLetter(i,j);
//                NodeT t_nt = dic.getRNodeChar(cur);
//                if (t_nt == null)
//                    continue;
//                if (cur == 'Q')
//                    t_nt = t_nt.next[20];
//                if (t_nt == null)
//                    continue;
//                Node n = new Node();
//                n.n = t_nt;
//                
////                if (n.n == null)
////                    continue;
////                if (cur == 'Q')
////                    n.n = n.n.next[20];
////                if (n.n == null)
////                    continue;
//                
//                n.i = i;
//                n.j = j;
//                //n.num = i; //* M + j
//                //n.s = "" + flatBoard[n.num];//table[n.num].key;//;board.getLetter(i,j)
//                //if (flatBoard[n.num] == 'Q')//(table[n.num].key == 'Q')//
//                //    n.s+= "U";
//                //String t_string = "" + flatBoard[n.num];
//                //flatBoard[n.num];
//                n.path = new BitSet(N*M);
//                n.path.set(i*M + j);//n.num
//                
//                
//                
//                 //= t_n2;
//                
//                stack.push(n);
                 
                
//                while(!stack.isEmpty())
//                {
//                    Node c = stack.pop();
//                    if(c.n == null)
//                        continue;
//                    //count++;
//                    //c_i = c.num / M;
//                    //c_j = c.num % M;
////                    if (!dic.containsKeysWithPrefix(c.s))
////                    {
////                        continue;
////                    }
//                    //addWord(c.s);
//                    cns = c.n.isString;
//                    if (c.n.length > 2 &&  cns != null)//dic.contains(c.s)
//                    {
////                        if (!possibleWords.contains(c.s))
////                            possibleWords.add(c.s);
////                        if (!possibleWords.contains(cns))
//                            possibleWords.add(cns);
//                    }
//                    
//
//                    for(t_i = c.i -1; t_i<c.i + 2; t_i++)
//                    {
//                        for(t_j = c.j -1; t_j <c.j + 2; t_j++)
//                        {
//                            t_num = t_i * M + t_j;
//                            if (t_i>=0 && t_i<N && t_j>=0 && t_j <M 
//                                && !c.path.get(t_num))
//                            {
//                                char t_c = flatBoard[t_num];//board.getLetter(t_i, t_j)
//                                if (c.n.next[t_c - 'A'] != null)
//                                {
//                                   Node t_n = new Node();
//                                   t_n.n = c.n.next[t_c - 'A'];
//                                   if (t_c == 'Q')
//                                       t_n.n = t_n.n.next[20];
//                                   if (t_n.n == null)
//                                       continue;
//                                   //t_n.num = t_num;
//                                   t_n.i = t_i;
//                                   t_n.j = t_j;
//                                   
//                            
//                                   t_n.path =  (BitSet)c.path.clone();
//                                   t_n.path.set(t_num);
//                              
//                                   
//                                   stack.push(t_n);
//                                }
//                            }
//                        }
//                    } 
//                }
                
                //clear stack
//                while(!stack.isEmpty())
//                {
//                    stack.pop();
//                }
                //turn++;
            }
            }
        //}
        //StdOut.println("Open : " + count + " nodes");
        
        return possibleWords;
    }
    
    //private void dfs(BoggleBoard board, int c_i, int c_j, NodeT n, boolean[] path, int M, int N, HashSet<String> possibleWords, int count)
//    private void dfs(char[] board, int c_i, int c_j, NodeT n, boolean[] path, int M, int N, HashSet<String> possibleWords, int count)
        private void dfs(char[] board, int c_i, int c_j, NodeT n, boolean[] path, int M, int N, Bag<String> possibleWords)
    {
        int t_num = c_i * M + c_j;
        if (path[t_num] || n == null)//path.get(t_num) c_i<0 || c_i>= N || c_j<0 || c_j >= M || 
        {
            return;
        }
//        char letter = board.getLetter(c_i,c_j);
        char letter = board[t_num];
        NodeT cur = n.next[letter - 'A'];
        if (cur == null)
            return;
        if (letter == 'Q')
            cur =  cur.next[20];
        if (cur == null)
            return;
        String cns = cur.isString;
        if ( cns != null)//dic.contains(c.s) cur.length > 2 && 
        {
            //possibleWords.add(cns);
//            StdOut.println(cur.ID);
            if (cur.ID >= 0 && have[cur.ID] < turn)
            {
//                possibleWords.enqueue(cns);
                possibleWords.add(cns);
                have[cur.ID] = turn;
            }
//            count++;
        }
        //path.set(t_num);
        //path[c_i][c_j] = true;
        //int c = 0;
        path[t_num] = true;
//        path.set(t_num);
        //outerloop:
//        for(int t_i = c_i -1; t_i<c_i + 2; t_i++)
//        {
//            for(int t_j = c_j -1; t_j <c_j + 2; t_j++)
//            {
//                if (c > cur.count)
//                {
//                    path[t_num] = false;
//                    return;
//                } 
//                dfs(board, t_i, t_j, cur, path, M,N, possibleWords,c);
//            }
//        }
        if (c_i> 0)
        {
            dfs(board, c_i - 1, c_j, cur, path, M,N, possibleWords);
            if (c_j > 0)
                dfs(board, c_i - 1, c_j - 1, cur, path, M,N, possibleWords);
            if (c_j< M - 1)
                dfs(board, c_i - 1, c_j + 1, cur, path, M,N, possibleWords);
        }
        if (c_i < N - 1)
        {
            dfs(board, c_i + 1, c_j, cur, path, M,N, possibleWords);
            if (c_j > 0)
                dfs(board, c_i + 1, c_j - 1, cur, path, M,N, possibleWords);
            if (c_j< M - 1)
                dfs(board, c_i + 1, c_j + 1, cur, path, M,N, possibleWords);
        }
        
        if (c_j > 0)
            dfs(board, c_i, c_j - 1, cur, path, M,N, possibleWords);
        if (c_j< M - 1)
            dfs(board, c_i, c_j + 1, cur, path, M,N, possibleWords);
        //path.set(t_num, false);
        //path[c_i][c_j] = false;
//        path.set(t_num,false);
        path[t_num]= false;
    }
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
        if (dic.contains(word))
        {
           int n = word.length();
           if (n < 3)
               return 0;
           if (n < 5)
               return 1;
           if (n == 5)
               return 2;
           if (n == 6)
               return 3;
           if (n == 7)
               return 5;
           return 11;
        }
        return 0;
    }
        
   public static void main(String[] args)
{
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    long startTime = System.nanoTime();
    for (int i = 0; i < 20000; i++)
    {
         score = 0;
        //boolean test = false;
        
        
        
        for (String word : solver.getAllValidWords(board))
        {
            //break;
            //StdOut.println(word);
            score += solver.scoreOf(word);
        }
    }
    long endTime = System.nanoTime();
    StdOut.println("Score = " + score);
    double duration = (endTime - startTime)/1e6;
    StdOut.println("Calls/s = " + duration + " calls");
}
}