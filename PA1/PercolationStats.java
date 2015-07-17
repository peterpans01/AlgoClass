import Percolation;

public class PercolationStats {
   private double[] res;
   private int m_T;
   private double mean;
   private double std;
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
       mean = 0.0;
       std = 0.0
       m_T = T;
        res = new int[T];
       for (int i = 0; i < T; i++)
       {
           Percolation temp = new Percolation(N);
           int opened = 0;
           while(!temp.percolates())
           {
              //choose the random blocked site
               
              //open that blocked
               
              
              //counting number of blocked / opened sites
              opened++;
           }
           // set the number of result
           res[i] = (double) opened/(N*N);
       }
   }
   public double mean()                      // sample mean of percolation threshold
   {
       if(mean == 0.0)
       {
          double sum = 0.0;
          for (int i = 0; i < m_T; i++)
         {
            double+= res[i];
          }
          mean = sum/ m_T;
       }
      
      return mean;
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
       if(std==0.0)
       {
       sum = 0.0;
       for (int i = 0; i < m_T; i++)
       {
           sum+=(res[i]-mean)**2;
       }
       std = math.sqrt(sum/(m_T-1));
       }
       return std;
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
      return mean - 1.96*std/(sqrt(double(m_T)));                   
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
      return mean + 1.96*std/(sqrt(double(m_T)));    
   }

   public static void main(String[] args)    // test client (described below)
   {
       
   }
}