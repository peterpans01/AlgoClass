/*         Dutch national flag. 
 * Given an array of N buckets, each containing a red, white, or blue pebble, 
 * sort them by color. The allowed operations are:
 * swap(i,j): swap the pebble in bucket i with the pebble in bucket j.
 * color(i): color of pebble in bucket i.
 * The performance requirements are as follows:
 *    At most N calls to color().
 *    At most N calls to swap().
 *    Constant extra space.
 * 
 * 
 * */


import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

public class Dutch_Nation_Flag
{
    static int count_swap, count_color;
    static int[] create(int N)
    {
        int red = StdRandom.uniform(N);
        int blue = StdRandom.uniform(N-red);
        int white = N - red - blue;
        int[] a = new int[N];
        for (int i = 0; i < N; i++)
        {
            if (i < red)
            {
                a[i] = 0;
            }
            else if (i>=red && i <red+blue)
            {
                a[i] = 1;
            }
            else
            {
                a[i] = 2;
            }
        }
        StdRandom.shuffle(a);
        return a;
    }
    static void swap(int[] a, int i, int j)
    {
        int s = a[i];
        a[i] = a[j];
        a[j] = s;
        count_swap++;
    }
    static int color(int[] a, int i)
    {
        count_color++;
        return a[i];
    }
    static void sort(int[] a, boolean show)
    {
        count_swap = 0;
        count_color = 0;
        int i = 0, j = 0, k = a.length-1;
        while(j<=k)
        {
            int c = color(a,j);
            if ( c == 0)
            {
                swap(a,i,j);
                i++;
                j++;
            }
            else if (c == 2)
            {
                swap(a,j,k);
                k--;
            }
            else
            {
                j++;
            }
          
           
            if(show)
            {
                System.out.println(i+"|"+j+"|"+k + " : " + Arrays.toString(a));
            }    
        } 
    }
    public static void main(String[] args)
    {
        int[] in = create(20);
        System.out.println("Before sort: " + Arrays.toString(in));
        sort(in,true);
        System.out.println("After sort: \n" + Arrays.toString(in));
        System.out.println("Number of swap:" + count_swap );
        System.out.println("Number of color:" + count_color );
    }
}