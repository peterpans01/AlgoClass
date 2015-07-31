//import java.lang.Math;

public class PercolationStats {
   private double[] res;
   private int mT;
   private double mean;
   private double std;
   public PercolationStats(int N, int T)
   // perform T independent experiments on an N-by-N grid
   {
       if (N <= 0 || T <= 0)
       {
           throw new java.lang.IllegalArgumentException();
       }
       mean = 0.0;
       std = 0.0;
       mT = T;
       res = new double[T];
       for (int i = 0; i < T; i++)
       {
           Percolation temp = new Percolation(N);
           int opened = 0;
           while (!temp.percolates())
           {
              //choose the random blocked site
              int block = StdRandom.uniform(N*N);
              while (temp.isOpen(block/N + 1, block % N + 1))
              {
                  block = StdRandom.uniform(N*N);
              }
               
              //open that blocked
              temp.open(block/N + 1, block % N + 1);
              
              //counting number of blocked / opened sites
              opened++;
           }
           // set the number of result
           res[i] = (double) opened/(N*N);
           
       }
       double sum = 0.0;
           for (int i = 0; i < mT; i++)
           {
             sum += res[i];
           }
           mean = sum/ mT;
           double sum2 = 0.0;
           for (int i = 0; i < mT; i++)
           {
              sum2 += (res[i]-mean)*(res[i]-mean);
           }
           std = Math.sqrt(sum2/(mT-1));
   }
   public double mean()                      // sample mean of percolation threshold
   {  
      return mean;
   }
   public double stddev() // sample standard deviation of percolation threshold
   {
       return std;
   }
   public double confidenceLo()  // low  endpoint of 95% confidence interval
   {
      return mean - (double) (1.96*std) /(double) (Math.sqrt((double) (mT)));
   }
   public double confidenceHi() // high endpoint of 95% confidence interval
   {
      return  mean + (double) (1.96*std) / (double) (Math.sqrt((double) (mT)));    
   }

   public static void main(String[] args)    // test client (described below)
   {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats exp = new PercolationStats(N, T);
       System.out.println(String.format("mean                    : %.10f", 
                                        exp.mean()));
       System.out.println(String.format("stddev                  : %.10f", 
                                        exp.stddev()));
       System.out.println(String.format("95%% confidence interval : %.10f, %.10f",
                                        exp.confidenceLo() , exp.confidenceHi()));
   }
}