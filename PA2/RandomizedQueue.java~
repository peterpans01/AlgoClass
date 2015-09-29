import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
   private Item[] q;            // queue elements
   private int N = 0;           // number of elements on queue
   private int first = 0;       // index of first element of queue
   private int last  = 0;       // index of next available slot
   
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       q = (Item[]) new Object[2];
   }
   public boolean isEmpty()                 // is the queue empty?
   {
       return N == 0;
   }
   public int size() // return the number of items on the queue
   {
       return N;
   }
   // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }
   private void printQ()
   {
       System.out.println("f = " + first + " l = " + last); 
       System.out.print("q = :");
       for (int i = 0; i < N; i++)
       {
           if (q[i] == null)
               System.out.print("- ");
           else
               System.out.print(q[i] + " ");
       }
       System.out.print("\n");
   }
   public void enqueue(Item item)           // add the item
   {
        if (item == null) throw new java.lang.NullPointerException();
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        N++;
   }
   public Item dequeue()                    // remove and return a random item
   {
       if (isEmpty()) throw new NoSuchElementException("Queue underflow");
       int chosen = 0;
       Item temp = null;
       first = (first + q.length) % q.length;
       last = (last + q.length) % q.length;
       if (first <= last-1)
       {
           chosen = StdRandom.uniform(first, last);
       }
       if (last-1 < first)
       {
           chosen = StdRandom.uniform(first, last + q.length);
           chosen = chosen % q.length;
       }
       if (q[chosen] == null)
       {
           System.out.println("Wrong implement for dequeue()");
       }
       temp =  q[chosen];
       if (chosen != (last-1 + q.length) % q.length)
       {
          q[chosen] = q[(last-1 + q.length) % q.length];
       }
       q[(last-1 + q.length) % q.length] = null;
       N--;
       if (last == 0)
       {
           last = q.length - 1;
       }
       else
       {
             last--;
       }
       
       
       if (N > 0 && N == q.length/4) resize(q.length/2);
       return temp;
   }
   public Item sample() // return (but do not remove) a random item
   {
       if (isEmpty()) throw new NoSuchElementException("Queue underflow");
       int chosen = 0;
       Item temp = null;
       if (first <= last-1)
       {
           chosen = StdRandom.uniform(first, last);
       }
       if (last - 1 < first)
       {
           chosen = StdRandom.uniform(first, last + q.length);
           chosen = chosen % q.length;
       }
       return q[chosen];
   }
   public Iterator<Item> iterator() 
   // return an independent iterator over items in random order
   {
       return new RQIterator();
   }
   private class RQIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] arr;
        public RQIterator() {
            arr = (Item[]) new Object[N];
            for (int j = 0; j < N; j++)
            {
                arr[j] = q[(j + first) % q.length];
            }
            StdRandom.shuffle(arr);
        }
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = arr[i];
            i++;
            return item;
        }
    }
   public static void main(String[] args)   // unit testing
   {
       RandomizedQueue<Integer> dq = new RandomizedQueue<>();
       dq.enqueue(Integer.valueOf(5));
      
       dq.enqueue(6);
       dq.enqueue(2);
       dq.enqueue(9);
       dq.enqueue(8);
       
       for (int i : dq)
       {
           StdOut.println(">" + i);
           for (int j : dq)
               StdOut.println("+" + j);
           StdOut.println("-----------------------");
       }
       
       System.out.println("Done unit test!!!");
   }
}
