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
   public int size()                        // return the number of items on the queue
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
   private void print_q()
   {
       System.out.println("f = " + first + " l = " + last); 
       System.out.print("q = :");
       for (int i = 0; i < N; i++)
       {
           if (q[i]==null)
               System.out.print("- ");
           else
               System.out.print(q[i] + " ");
       }
       System.out.print("\n");
   }
   public void enqueue(Item item)           // add the item
   {
        
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
       if(first <= last-1)
       {
           chosen = StdRandom.uniform(first, last);
       }
       if(last-1 < first)
       {
           chosen = StdRandom.uniform(first, last + q.length);
           chosen = chosen % q.length;
       }
       if(q[chosen]==null)
       {
           System.out.println("Wrong implement for dequeue()");
       }
       temp =  q[chosen];
       if(chosen != last-1)
       {
          q[chosen] = q[last-1];
          q[last-1] = null; 
       }
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
   public Item sample()                     // return (but do not remove) a random item
   {
       if (isEmpty()) throw new NoSuchElementException("Queue underflow");
       int chosen = 0;
       Item temp = null;
       if(first <= last)
       {
           chosen = StdRandom.uniform(first, last);
       }
       if(last < first)
       {
           chosen = StdRandom.uniform(first, last + N);
           chosen = chosen % N;
       }
       return q[chosen];
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RQIterator();
   }
   private class RQIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }
   public static void main(String[] args)   // unit testing
   {
       RandomizedQueue<Integer> dq = new RandomizedQueue<>();
       dq.enqueue(Integer.valueOf(5));
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       dq.enqueue(Integer.valueOf(7));
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       dq.enqueue(Integer.valueOf(8));
       dq.enqueue(6);
       dq.enqueue(2);
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Size of dq : " + dq.size());
       System.out.println("Sample get : " + dq.sample());
       System.out.println("Remove get : " + dq.dequeue());
       dq.print_q();
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Remove get : " + dq.dequeue());
       dq.print_q();
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Remove last get : " + dq.dequeue());
       dq.print_q();
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Remove first get : " + dq.dequeue());
       dq.print_q();
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       dq.dequeue();
       dq.print_q();
       
       //dq.dequeue();
       //dq.print_q();
       
       dq.enqueue(15);
       //Iterator it = dq.iterator();
       //it.remove();
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Done unit test!!!");
   }
}