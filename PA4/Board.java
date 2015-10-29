import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private char[] mBoard;
    private char N;
    private int manDis;
    public Board(int[][] blocks)           // construct a board from an N-by-N 
    {
        N = (char) blocks.length;
        manDis = -1;
        mBoard = new char[N*N]; 
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            { 
                int k = blocks[i][j];
                if (k > 0 && k < mBoard.length)
                    mBoard[i*N + j] = (char) blocks[i][j];
                else
                    mBoard[i*N + j] = 0;
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
        for (int i = 0; i < mBoard.length; i++)
        {
               if (mBoard[i] != 0)
               {
                   if (mBoard[i] != i + 1)
                       count += 1;
               }
        }
        return count;
    }
    public int manhattan()// sum of Manhattan distances between blocks and goal
    {
        if (manDis != -1) return manDis;
        manDis = 0;
        for (int i = 0; i < mBoard.length; i++)
        {
            if (mBoard[i] != 0)
            {
                //char x = (char) Math.abs(mBoard[i]-1-i);
                manDis += (int) (Math.abs((mBoard[i]-1)/N - i/N)) 
                    + (int) (Math.abs((mBoard[i]-1) % N - i % N));
            }
        }
        return manDis;
    }
    public boolean isGoal() // is this board the goal board?
    {
        for (int i = 0; i < mBoard.length-1; i++)
        {
            if (mBoard[i] != (char) i+1)
                return false;
        }
        return true;
    }
    // return the ramdom number int (0,N) and # with e1, e2
    private int pick(int s, int bound, int e1, int e2)
    {
        int count = 0;
        if (e1 != -1)
        {
            count++;
        }
        if (e2 != -1)
        {
            count++;
        }
        int res = StdRandom.uniform(s, bound - count);
        if (count == 1)
        {
            if (res >= e1)
                return res + 1;
            return res;
        }
        if (count == 2)
        {
            int min = e1;
            int max = e2;
            if (e1 > e2)
            {
                min = e2; 
                max = e1; 
            }
            if (res >= min)
            {
                res += 1; 
            }
            if (res >= max)
            {
                res += 1;
            }
            return res;
        }
        return res;
    }
    public Board twin()// a board that is obtained by 
    {                  // exchanging any pair of blocks
        
//        int i1 = -1;
//        int i2 = -1;
//        while(i1 == -1 && i2 == -1)
//        {
//            if (i1 == -1)
//            {
//                do
//                {
//                    i1 = StdRandom.uniform(0, N);
//                }while(mBoard[i1] == 0);
//            }
//            
//            if (i1 != -1 && i2 == -1)
//            {
//                do
//                {
//                    i2 = StdRandom.uniform(0, N);
//                }while(mBoard[i2] == 0 || i2 == i1);
//            }
//        }
        int i0 = 0;
        for (int i = 0; i < mBoard.length; i++)
        {
            if (mBoard[i] == 0)
            {
                i0 = i;
                break;
            }
        }
        int i1 = pick(0, mBoard.length, i0, -1);
        int i2 = pick(0, mBoard.length, i0, i1);

        int[][] tempBlocks = new int[N][N];
        for (int i = 0; i < mBoard.length; i++)
        {
               if (i == i2)
                   tempBlocks[i/N][i % N] = mBoard[i1];
               else if (i == i1)
                   tempBlocks[i/N][i % N] = mBoard[i2];
               else
                   tempBlocks[i/N][i % N] = mBoard[i];
        }
        return new Board(tempBlocks);
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
        for (int i = 0; i < mBoard.length; i++)
        {
                
                if (mBoard[i] != that.mBoard[i])
                    return false;
        }
        return true;
    }
    private void exchange(char[] src, int[][] dst, int i1, int i2)
    {
        //dst = new int[src[0].length][src[0].length];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
               if (i * N + j  == i2)
                   dst[i][j] = (int) src[i1];
               else if (i * N + j == i1)
                   dst[i][j] = (int) src[i2];
               else
                   dst[i][j] = (int) src[i * N + j];
            }
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> q = new Queue<Board>();
        int i0 = 0;
        for (int i = 0; i < mBoard.length; i++)
        {
                if (mBoard[i] == 0)
                {
                    i0 = i;
                    break;
                }
        }
        
        if (i0 % N > 0)
        {
            int[][] dst1 = new int[N][N];
            exchange(mBoard, dst1, i0, i0 - 1);
            q.enqueue(new Board(dst1));
        }
        
        if (i0 % N < N -1)
        {
            int[][] dst2 = new int[N][N];
            exchange(mBoard, dst2, i0, i0 + 1);
            q.enqueue(new Board(dst2));
        }
        
        if (i0 / N < N - 1)
        {
            int[][] dst3 = new int[N][N];
            exchange(mBoard, dst3, i0, i0 + N);
            q.enqueue(new Board(dst3));
        }
        
        if (i0 / N > 0)
        {
            int[][] dst4 = new int[N][N];
            exchange(mBoard, dst4, i0, i0 - N);
            q.enqueue(new Board(dst4));
        }
        return q;
        
    }
    public String toString() // string representation of this board (in the 
    {                        // output format specified below)
        StringBuilder s = new StringBuilder();
        s.append((int) N + "\n"); 
        for (int i = 0; i < N; i++)
        {
           
            for (int j = 0; j < N; j++)
            {
                
                    s.append(String.format("%2d ", (int) mBoard[i * N + j]));
           
//                else
//                {
//                    s.append("   ");
//                }
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
        StdOut.println("Hamming : " + initial.hamming());
        StdOut.println("Mahattan : " + initial.manhattan());
        StdOut.println("Is goal? : " + initial.isGoal());
        StdOut.println("Content :\n" + initial);
        Board tw = initial.twin();
        StdOut.println("Check same object: "+ (initial.equals(initial)));
        StdOut.println("Check null: "+ (initial.equals(null)));
        StdOut.println("Check equal: "+ (initial.equals(b2)));
        StdOut.println("Check equal twin: "+ (initial.equals(tw)));
                       
        StdOut.println("Twin :\n" + tw);
        StdOut.println("Neighbors :");
        for (Board t: initial.neighbors())
            StdOut.println(t);
    }
}