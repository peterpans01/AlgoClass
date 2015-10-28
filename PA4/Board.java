import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private char[] m_board;
    private char N;
    private int man_dis;
    public Board(int[][] blocks)           // construct a board from an N-by-N 
    {
        N = (char) blocks.length;
        man_dis = -1;
        m_board = new char[N*N]; 
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            { 
                int k = blocks[i][j];
                if (k > 0 && k < m_board.length)
                    m_board[i*N + j] = (char) blocks[i][j];
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
        if(man_dis != -1) return man_dis;
        man_dis = 0;
        for (int i = 0; i < m_board.length; i++)
        {
            if (m_board[i] != 0)
            {
                //char x = (char) Math.abs(m_board[i]-1-i);
                man_dis+= (int) (Math.abs((m_board[i]-1)/N - i/N)) + (int) (Math.abs((m_board[i]-1)%N - i%N));
            }
        }
        return man_dis;
    }
    public boolean isGoal() // is this board the goal board?
    {
        for (int i = 0; i < m_board.length-1; i++)
        {
            if(m_board[i] != (char) i+1)
                return false;
        }
        return true;
    }
    public Board twin()// a board that is obtained by 
    {                  // exchanging any pair of blocks
        
        int i_1 = -1;
        int i_2 = -1;
        while(i_1 == -1 && i_2 == -1)
        {
            if(i_1 == -1)
            {
                do
                {
                    i_1 = StdRandom.uniform(0, N);
                }while(m_board[i_1] == 0);
            }
            
            if(i_1 != -1 && i_2 == -1)
            {
                do
                {
                    i_2 = StdRandom.uniform(0, N);
                }while( m_board[i_2] == 0 || i_2 == i_1);
            }
        }

        int[][] temp_blocks = new int[N][N];
        for (int i = 0; i < m_board.length; i++)
        {
               if(i == i_2)
                   temp_blocks[i/N][i%N] = m_board[i_1];
               else if (i == i_1)
                   temp_blocks[i/N][i%N] = m_board[i_2];
               else
                   temp_blocks[i/N][i%N] = m_board[i];
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
                
                if( m_board[i] != that.m_board[i])
                    return false;
        }
        return true;
    }
    private void exchange(char[] src, int[][] dst, int i_1, int i_2)
    {
        //dst = new int[src[0].length][src[0].length];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
               if(i * N + j  == i_2)
                   dst[i][j] = (int) src[i_1];
               else if (i * N + j == i_1)
                   dst[i][j] = (int) src[i_2];
               else
                   dst[i][j] = (int) src[i * N + j];
            }
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> q = new Queue<Board>();
        int i_0 = 0;
        for (int i = 0; i < m_board.length; i++)
        {
                if( m_board[i] == 0)
                {
                    i_0 = i;
                    break;
                }
        }
        
        if(i_0 % N > 0)
        {
            int[][] dst1 = new int[N][N];
            exchange(m_board, dst1, i_0, i_0 - 1);
            q.enqueue(new Board(dst1));
        }
        
        if(i_0 % N < N -1)
        {
            int[][] dst2 = new int[N][N];
            exchange(m_board, dst2, i_0, i_0 + 1);
            q.enqueue(new Board(dst2));
        }
        
        if(i_0 / N < N - 1)
        {
            int[][] dst3 = new int[N][N];
            exchange(m_board, dst3, i_0, i_0 + N);
            q.enqueue(new Board(dst3));
        }
        
        if(i_0 / N > 0)
        {
            int[][] dst4 = new int[N][N];
            exchange(m_board, dst4, i_0, i_0 - N);
            q.enqueue(new Board(dst4));
        }
        return q;
        
    }
    public String toString() // string representation of this board (in the 
    {                        // output format specified below)
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < N; i++)
        {
           
            for (int j = 0; j < N; j++)
            {
                if( m_board[i * N + j] != 0)
                {
                    s.append(String.format("%2d ", (int) m_board[i * N + j]));
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