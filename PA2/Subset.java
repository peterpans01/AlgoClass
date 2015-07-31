

public class Subset {
   public static void main(String[] args)
   {
       RandomizedQueue<String> q = new RandomizedQueue<>();
       int k = Integer.parseInt(args[0]);
       while (!StdIn.isEmpty() && k > 0) {
           if(StdRandom.uniform(0,2)==1)
           {
               q.enqueue(StdIn.readString());
           }
       }
       
       while(!q.isEmpty())
       {
           StdOut.println(q.dequeue());
       }
   }
}