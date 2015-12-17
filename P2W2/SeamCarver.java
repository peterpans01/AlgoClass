import edu.princeton.cs.algs4.Picture;
//import java.lang;//.NullPointerException
import java.awt.Color;

public class SeamCarver {
   Picture pic;
   int W;
   int H;
   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture) 
   {
       if(picture == null)
           throw new java.lang.NullPointerException();
       W = picture.width();
       H = picture.height();
       pic = new Picture(picture);
   }
   // current picture
   public Picture picture()  
   {
       return pic;
   }
   // width of current picture
   public     int width()  
   {
       return W;
   }
   // height of current picture
   public     int height()
   {
       return H;
   }
   // energy of pixel at column x and row y
   public  double energy(int x, int y)  
   {
       
       if(x < 0 || x > W - 1 || y < 0 || y > H -1)
           throw new java.lang.IndexOutOfBoundsException();
       if (x == 0 || x == pic.width() - 1 
           || y == 0 || y == pic.height() -1)
           return 1000.0;
       
       return (double) java.lang.Math.sqrt(deltaX2(x, y) + deltaY2(x, y));
   }
   
   //private function for energy
   private double deltaX2(int x, int y)
   {
       Color p1 = pic.get(x + 1, y);
       Color p2 = pic.get(x - 1, y);
       return delta2(p1, p2);
   }
   private double deltaY2(int x, int y)
   {
       Color p1 = pic.get(x, y + 1);
       Color p2 = pic.get(x, y - 1);
       return delta2(p1, p2);
   }
   private double delta2(Color c1, Color c2)
   {
       double r = c2.getRed() - c1.getRed();
       double g = c2.getGreen() - c1.getGreen();
       double b = c2.getBlue() - c1.getBlue();
       return r * r + g * g + b * b;
   }
   // sequence of indices for horizontal seam
//   public   int[] findHorizontalSeam()   
//   {
//       return new int[];
//   }
//   // sequence of indices for vertical seam
//   public   int[] findVerticalSeam()  
//   {
//       return new int[];
//   }
//   // remove horizontal seam from current picture
//   public    void removeHorizontalSeam(int[] seam)
//   {
//       
//   }
//   // remove vertical seam from current picture
//   public    void removeVerticalSeam(int[] seam) 
//   {
//       
//   }
}