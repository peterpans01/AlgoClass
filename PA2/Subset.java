

public class Subset {
   public static void main(String[] args)
   {
       RandomizedQueue<String> q = new RandomizedQueue<>();
       int k = Integer.parseInt(args[0]);
       //int i = 0;
       int c = 0;
       while (!StdIn.isEmpty()) {
           String s = StdIn.readString();
           q.enqueue(s);
//           i++;
//           if (i <= k)
//           {
//                 q.enqueue(s);
//           }
//           else
//           {
//               int j = StdRandom.uniform(1, i+1);
//               if (j < k && c < k)
//               {
//                   StdOut.println(s);
//                   c++;
//                   if (!q.isEmpty())
//                       q.dequeue();
//               }
//           }
       }
       
       for (int i = 0; i < k; i++)
       {
           StdOut.println(q.dequeue());
       }
//       while (!q.isEmpty() && c < k)
//       {
//           StdOut.println(q.dequeue());
//           c++;
//       }
   }
}