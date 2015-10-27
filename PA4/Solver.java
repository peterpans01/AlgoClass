import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;


public class Solver {
    private Queue<Board> solution;
    private boolean solvable;
    private static class Node implements Comparable<Node>
    {
        private Board board;
        private int move;
        private Board prev;
        
        public Node(Board _board,int _move,Board _prev)
        {
            board = _board;
            move  = _move;
            prev  = _prev;
        }
        public int hammingPro()
        {
            return move + board.hamming();
        }
        
        public int ManhattanPro()
        {
            return move + board.manhattan();
        }
            
        public int compareTo(Node that)
        {
            return hammingPro() - that.hammingPro();
        }
        public boolean goal()
        {
            return board.isGoal();
        }
        public int getMove()
        {
            return move;
        }
        public Board getPrev()
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
                return u.ManhattanPro() - v.ManhattanPro();
            }
        }
        public Comparator<Node> getManhattanOrder()
        {
            Comparator<Node> c = new ManhattanOrder();
            return c;
        }
    }
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
        {
            throw new java.lang.NullPointerException();
        }
        MinPQ<Node> series = new MinPQ<Node>();
        Node f1 = new Node(initial,0,null);
        solution = new Queue<Board>();
        series.insert(f1);
        solvable = false;
        while( !series.isEmpty() )
        {
            Node current = series.delMin();
            Board cb = current.getBoard();
            solution.enqueue(cb);
            if( current.goal() )
            {
                solvable = true;
                break;
            }
            for (Board t: cb.neighbors())
            {
                if(!t.equals(current.getPrev()))
                {
                    series.insert(new Node(t, current.getMove() + 1, cb));
                }
            }
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if(solvable)
        {
            return solution.size() -1;
        }
        return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(solvable)
        {
            return solution;
        }
        return null;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {

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
//            StdOut.println("[Manhattan] " + i + " : " + t_a[i].ManhattanPro());
//        }
        if( initial == null)
        {
            StdOut.println("NULL POINTER");
        }
        // solve the puzzle
        Solver solver = new Solver(initial);
        
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