import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private char[] m_board;
    private char N;
    public Board(int[][] blocks)           // construct a board from an N-by-N 
    {
        N = blocks.length;
        m_board = new char[N*N]; 
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            { 
                int k = blocks[i][j];
                if (k > 0 && k < m_board.length)
                    m_board[i*N + j] = blocks[i][j];
                else
                    m_board[i*N + j] = 0;
            }
    }
                                           // array of blocks
                                           // (where blocks[i][j] = block in 
                                           // row i, column j)
    public int dimension()                 // board dimension N
    {
        return N;
    }
        
    public int hamming()                   // number of blocks out of place
    {
        int count = 0;
        for (int i = 0; i < m_board.length; i++)
        {
               if (m_board[i]!=0)
               {
                   if (m_board[i]!=i + 1)
                       count+=1;
               }
        }
        return count;
    }
    public int manhattan()// sum of Manhattan distances between blocks and goal
    {
        int dis = 0;
        for (int i = 0; i < m_board.length; i++)
            for (int j = 0; j < m_board.length; j++)
            {
               if (m_board[i][j]!=0)
               {
                  dis+= Math.abs(m_board[i][j]%m_board.length - j)
                      + Math.abs(m_board[i][j]/m_board.length - i);
               }
            }
        
        return dis;
    }
    public boolean isGoal() // is this board the goal board?
    {
        return hamming()==0;
    }
    public Board twin()// a board that is obtained by 
    {                  // exchanging any pair of blocks
        StdOut.println("Start debug ============== ");
        int i_2 = 0, j_2 = 0;
        boolean found = false;
        for (int i = 0; i < m_board.length; i++)
        {
            for (int j = 0; j < m_board.length; j++)
            {
                System.out.print(m_board[i][j] + " ");
                if(m_board[i][j] != 0)
                {
                    i_2 = i;
                    j_2 = j;
                    if(found == false) 
                    {
                        found = true;
                    }
                    break;
                }
    
            }
            if (found)
                break;
         }
        StdOut.println("\n end debug ==============");
        StdOut.println("i_2 = " + i_2 + " j_2 = " + j_2);
        int i_1 = StdRandom.uniform(m_board.length);
        int min = 0, max =  m_board.length;
        if (i_1 == 0)
            min = 1;
        if (i_1 == m_board.length - 1)
            max = m_board.length - 1;
        int j_1 = StdRandom.uniform(min,max);
        StdOut.println("i_1 = " + i_1 + " j_1 = " + j_1);
        int[][] temp_blocks = new int[m_board.length][m_board.length];
        for (int i = 0; i < m_board.length; i++)
            for (int j = 0; j < m_board.length; j++)
            {
               if(i == i_2 && j == j_2)
                   temp_blocks[i][j] = m_board[i_1][j_1];
               else if (i == i_1 && j == j_1)
                   temp_blocks[i][j] = m_board[i_2][j_2];
               else
                   temp_blocks[i][j] = m_board[i][j];
            }
        return new Board(temp_blocks);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension())
            return false;
        for (int i = 0; i < m_board.length; i++)
        {
            for (int j = 0; j < m_board.length; j++)
            {
                
                if( m_board[i][j] != that.m_board[i][j])
                    return false;
            }
        }
        return true;
    }
    private void exchange(int[][] src, int[][] dst, int i_1, int j_1, int i_2, 
                          int j_2)
    {
        //dst = new int[src[0].length][src[0].length];
        for (int i = 0; i < src[0].length; i++)
            for (int j = 0; j < src[0].length; j++)
            {
               if(i == i_2 && j == j_2)
                   dst[i][j] = src[i_1][j_1];
               else if (i == i_1 && j == j_1)
                   dst[i][j] = src[i_2][j_2];
               else
                   dst[i][j] = src[i][j];
            }
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> q = new Queue<Board>();
        int i_0 = 0;
        int j_0 = 0;
        for (int i = 0; i < m_board.length; i++)
        {
            for (int j = 0; j < m_board.length; j++)
            {
                
                if( m_board[i][j] == 0)
                {
                    i_0 = i;
                    j_0 = j;
                }
            }
        }
        
        int N = m_board.length;
        if(i_0 - 1 > 0)
        {
            int[][] dst1 = new int[N][N];
            exchange(m_board, dst1, i_0, j_0, i_0 - 1, j_0);
            q.enqueue(new Board(dst1));
        }
        
        if(i_0 + 1 < m_board[0].length)
        {
            int[][] dst2 = new int[N][N];
            exchange(m_board, dst2, i_0, j_0, i_0 + 1, j_0);
            q.enqueue(new Board(dst2));
        }
        
        if(j_0 + 1 < m_board[0].length)
        {
            int[][] dst3 = new int[N][N];
            exchange(m_board, dst3, i_0, j_0, i_0, j_0 + 1);
            q.enqueue(new Board(dst3));
        }
        
        if(j_0 - 1 > 0)
        {
            int[][] dst4 = new int[N][N];
            exchange(m_board, dst4, i_0, j_0, i_0, j_0 - 1);
            q.enqueue(new Board(dst4));
        }
        return q;
        
    }
    public String toString() // string representation of this board (in the 
    {                        // output format specified below)
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < m_board.length; i++)
        {
           
            for (int j = 0; j < m_board.length; j++)
            {
                if( m_board[i][j] != 0)
                {
                    s.append(String.format("%2d ", m_board[i][j]));
                }
                else
                {
                    s.append("   ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
        

    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board b2 = new Board(blocks);
        StdOut.println("dimension of board :" + initial.dimension());
        StdOut.println("Content :\n" + initial);
        StdOut.println("Hamming : " + initial.hamming() );
        StdOut.println("Mahattan : " + initial.manhattan() );
        StdOut.println("Is goal? : " + initial.isGoal() );
        StdOut.println("Content :\n" + initial);
        Board tw = initial.twin();
        StdOut.println("Check same object: "+ (initial.equals(initial)));
        StdOut.println("Check null: "+ (initial.equals(null)));
        StdOut.println("Check equal: "+ (initial.equals(b2)));
        StdOut.println("Check equal twin: "+ (initial.equals(tw)));
                       
        StdOut.println("Twin :\n" + tw);
        StdOut.println("Neighbors :");
        for(Board t: initial.neighbors())
            StdOut.println(t);
    }
}