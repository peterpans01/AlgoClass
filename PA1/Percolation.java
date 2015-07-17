//import QuickFindUF;
//import In;

public class Percolation {
   private boolean[][] grid;
   private QuickFindUF qf;
   private int size;
   private int bot;
   private int top;
   public int blocked;
   public Percolation(int N)               // create N-by-N grid, with all sites blocked
     {
       if(N>0)
       {
         size = N;
         grid = new boolean[N][N]; //open = true; close = false
         qf = new QuickFindUF(N*N + 2);
         bot = size*size;
         top = size*size + 1;
         blocked = size*size;
       }
       else
       {
         throw new java.lang.IllegalArgumentException();
       }
     }
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
     {
       if(i>0 && i<size+1 && j>0 && j<size+1)
       {
          //System.out.println("Go inside");
          //set this as open
          if (!grid[i-1][j-1])
          {
               grid[i-1][j-1] = true;
               blocked--;
               if(i == 1)
               {
                  qf.union( (i-1)*size + j-1, top);
               }
               if(i == size)
               {
                  qf.union( (i-1)*size + j-1, bot);
               }
               
               //connect it
               if(j < size && isOpen(i,j+1))
               {
                   qf.union( (i-1)*size + j-1, (i-1)*size + j );
               }
               if(j > 1 && isOpen(i,j-1))
               {
                    qf.union( (i-1)*size + j-1, (i-1)*size + j-2 );
               }
               if(i < size && isOpen(i+1,j))
               {
                   qf.union( (i-1)*size + j-1, i*size + j-1 );
               }
               if(i > 1 && isOpen(i-1,j))
               {
                   qf.union( (i-1)*size + j-1, (i-2)*size + j-1 );
               }                   
          }
       }
       else
       {
          //System.out.println(String.format("Size of input: %d", size));
          throw new java.lang.IndexOutOfBoundsException();
       }
     }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
     {
       if(i>0 && i<size+1 && j>0 && j<size+1)
       {
         return grid[i-1][j-1];
       }
       else
       {
           //return false;
          throw new java.lang.IndexOutOfBoundsException();
       }
     }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
      if(i>0 && i<size+1 && j>0 && j<size+1 )
      {
         if (isOpen(i,j))
         {
               return (qf.connected((i-1)*size + j-1, top));
         }
         return false;
       }
       else
       {
          throw new java.lang.IndexOutOfBoundsException();
       }
   }
   public boolean percolates()             // does the system percolate?
   {
     //logic here
      return qf.connected(bot, top);
   }

   public static void main(String[] args)   // test client (optional)
   {
      In input = new In("/user/hunnguye/home/algs4/PA/PA1/percolation/" + args[0]);
      int[] inn = input.readAllInts();
      Percolation data = new Percolation(inn[0]);
      //System.out.println(String.format("Size of input: %d", inn.length));
      for(int i = 1; i< inn.length; i++)
      {
          //System.out.println(String.format("value[%d]: %d | %d",i, inn[i],inn[i+1]));
          data.open(inn[i],inn[i+1]);
          i++;
      }
      if (data.percolates())
      {
          System.out.println("This net is percolates");
      }
      else
      {
          System.out.println("This net is not percolates");
      }
   }
}