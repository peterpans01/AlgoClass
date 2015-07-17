

public class Percolation {
   private boolean[][] grid;
   public Percolation(int N)               // create N-by-N grid, with all sites blocked
     {
       if(N>0)
       {
         grid = new int[N][N]; //open = true; close = false
       }
       else
       {
         throw new java.lang.IllegalArgumentException();
       }
     }
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
     {
       if(i>0 && i<N+1)
       {
          if (!grid[i-1][j-1])
               grid[i-1][j-1] = true;
       }
       else
       {
          throw new java.lang.IndexOutOfBoundsException();
       }
     }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
     {
       if(i>0 && i<N+1)
       {
         return grid[i][j];
       }
       else
       {
          throw new java.lang.IndexOutOfBoundsException();
       }
     }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
      if(i>0 && i<N+1)
       {
         //logic here
       }
       else
       {
          throw new java.lang.IndexOutOfBoundsException();
       }
   }
   public boolean percolates()             // does the system percolate?
   {
     //logic here
   }

   public static void main(String[] args)   // test client (optional)
   {
      //logic here
   }
}