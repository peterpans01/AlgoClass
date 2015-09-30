/*Intersection of two sets. 
 * Given two arrays a[] and b[], each containing N distinct 2D points in the plane, 
 * design a subquadratic algorithm to count the number of points 
 * that are contained both in array a[] and array b[].
 */



import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Shell;
public class IntersectionSets
{
    // generate ramdomly an arrays with N = 50, range (0-3)
    public static Point2D[] create_array(int N,int r)
    {
        Point2D[] a = new Point2D[N];
        for (int i = 0; i < N; i++)
        {
            a[i] = new Point2D(StdRandom.uniform(r),StdRandom.uniform(r));
        }
        return a;
    }
    //goal : design the subquadratic algo.
    //analyze: the complexity of this algo depends on the the complexity of sort
    //algorithms. SO choose the sorting algo. wisely. 
    // Here is the step of algo.:
    //
    // + sort both array a and b
    // + starting from bottom, we use to pointer like i,j:
    //     - if a[i] < b[j], i++
    //     - elseif a[i] > b[j] , j++
    //     - else count +=1, i++, j++
    public static int intersection(Point2D[] a, Point2D[] b)
    {
        Shell.sort(a);
        Shell.sort(b);
        System.out.println("Sorted : \nList of Point a:");
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(a[i]);
        }
        System.out.println("List of Point b:");
        for (int i = 0; i < b.length; i++)
        {
            System.out.println(b[i]);
        }
        int i = 0, j = 0, count = 0;
        while(i<a.length && j < b.length)
        {
            if (a[i].compareTo(b[j])<0)
            {
                i++;
            }
            else if(a[i].compareTo(b[j])>0)
            {
                j++;
            }
            else
            {
                System.out.println(a[i]+"==" +b[j]);
                count++;
                i++;
                j++;
            }
        }
        return count;
    }
    public static void main(String[] args)
    {
        Point2D[] a = create_array(9,4);
        Point2D[] b = create_array(9,4);
        System.out.println("List of Point a:");
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(a[i]);
        }
        System.out.println("List of Point b:");
        for (int i = 0; i < b.length; i++)
        {
            System.out.println(b[i]);
        }
        System.out.println("Number of intersection:" + intersection(a,b));
    }
}