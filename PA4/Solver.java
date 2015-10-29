import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
//import java.lang.NullPointerException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;


public class Solver {
//    public static boolean DEBUG = false;
    //private ST<Board,Board> solution;
    //private boolean solvable;
    //ST<Board, int> cost_so_far;
    private Stack<Board> path;
    //private Board goal;
    private static class Node implements Comparable<Node>
    {
        private Board board;
        private int move;
        private Node prev;
        
        public Node(Board iBoard, int iMove, Node iPrev)
        {
            board = iBoard;
            move  = iMove;
            prev  = iPrev;
        }
        public int hammingPro()
        {
            return move + board.hamming();
        }
        
        public int manhattanPro()
        {
            return move + board.manhattan();
        }
            
        public int compareTo(Node that)
        {
//            if (hammingPro() == that.hammingPro())
//                return board.hamming() - that.board.hamming();
//            return hammingPro() - that.hammingPro();
            if (manhattanPro() == that.manhattanPro())
                return board.manhattan() - that.board.manhattan();
            return manhattanPro() - that.manhattanPro();
        }
        public boolean goal()
        {
            return board.isGoal();
        }
        public int getMove()
        {
            return move;
        }
        public Node getPrev()
        {
            return prev;
        }
        public Board getBoard()
        {
            return board;
        }
        private class ManhattanOrder implements Comparator<Node>
        {
            public int compare(Node u, Node v)
            {
                return u.manhattanPro() - v.manhattanPro();
            }
        }
        public Comparator<Node> getManhattanOrder()
        {
            Comparator<Node> c = new ManhattanOrder();
            return c;
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) 
    {
        //goal = null;
        if (initial == null)
        {
            throw new java.lang.NullPointerException();
        }
        
        MinPQ<Node> series = new MinPQ<Node>();
        Node f1 = new Node(initial, 0, null);
        series.insert(f1);
//        StdOut.println("============");
        Board twin = initial.twin();
//        StdOut.println("== \n" + initial);
//        StdOut.println("== \n" + twin);
        MinPQ<Node> qtwin = new MinPQ<Node>();
        Node f2 = new Node(twin, 0, null);
        qtwin.insert(f2);
        
        path = new Stack<Board>();
        //solution = new ST<Board, Board>();
        //solution.put(initial, null);
        
        //cost_so_far = new ST<Board, int>();
        //cost_so_far.put(initial,0);
        
//        int count = 0;
        while (!series.isEmpty() && !qtwin.isEmpty()) //&& count != limit
        {
            //count++;
//            if (DEBUG)
//            {
//              StdOut.println("=================DEBUG==================");//+ count
//              for (Node n : series)
//              {
//StdOut.println(n.manhattanPro() +" | " + n.getBoard().manhattan() 
            //+ "\n" + n.getBoard());
//              }
//            }
            //StdOut.println(".......");
            Node current = series.delMin();
            Node cTwin = qtwin.delMin();
            //Board cb = current.getBoard();
//            if (DEBUG)
//            {
//                StdOut.println("============== CCURRENT ==============");
//                StdOut.println(cb);
//                StdOut.println("==============END OF DEBUG==============");
//            }
            if (cTwin.goal())
            {
                break;
            }
            if (current.goal())
            {
                while (current != null)
                {
                    path.push(current.getBoard());
                    current = current.getPrev();
                }
                break;
            }
            for (Board t: current.getBoard().neighbors())
            {
                int newCost = current.getMove() + 1;
                if (current.getPrev() == null 
                        || !t.equals(current.getPrev().getBoard()))
                {
                    //cost_so_far.put(t,newCost);
                    series.insert(new Node(t, newCost, current));
                    //solution.put(t,cb);
                }
            }
            for (Board u: cTwin.getBoard().neighbors())
            {
                int newCostT = cTwin.getMove() + 1;
                if (cTwin.getPrev() == null || !u.equals(cTwin.getPrev().getBoard()))
                {
                    //cost_so_far.put(t,newCost);
                    qtwin.insert(new Node(u, newCostT, cTwin));
                    //solution.put(t,cb);
                }
            }
        }
        //StdOut.println("RESULT : " + path.size());
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return path.size() != 0;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()                     
    {
        return path.size() -1;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()      
    {
        
        if (isSolvable())
        {
            return path;
        }
        return null;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
//        DEBUG = false;
//        int limit = -1;
//        int count = 0;
//        for(String s: args)
//        {
//            if (s.compareTo("-debug") == 0)
//            {
//                DEBUG = true;
//            }
//            if (s.compareTo("-limit") == 0)
//            {
//                limit = Integer.parseInt(args[count + 1]);
//            }
//            count++;  
//        }
        //StdOut.println("LIMIT ======" + limit);
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
//        Node[] t_a = new Node[4]; 
//        int cap = 0;
//        for (Board t : initial.neighbors())
//        {
//            t_a[cap] = new Node(t,1,initial);
//            cap++;
//        }
//        StdOut.println("[hamming] :" + " : " + t_a[1].compareTo(t_a[2]));
//        for(int i = 0; i < cap; i++)
//        {
//            StdOut.println("[hamming]   " + i + " : " + t_a[i].hammingPro());
//            StdOut.println("[Manhattan] " + i + " : " + t_a[i].manhattanPro());
//        }
//        if (initial == null)
//        {
//            StdOut.println("NULL POINTER");
//        }
        // solve the puzzle
        Solver solver = new Solver(initial); //, limit
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}