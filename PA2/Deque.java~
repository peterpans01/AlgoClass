import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   private int N;   //size of deque
   private Node<Item> first;
   private Node<Item> last;
   //private Node<Item> prelast;

   private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }
   public Deque()         // construct an empty deque
   {
       first = new Node<Item>();
       last = new Node<Item>();
       first.next = last;
       last.prev = first;
       N = 0;
       //dll = new DoublyLinkedList<>();
   }
   public boolean isEmpty()                 // is the deque empty?
   {
       return N == 0;
       //return dll.isEmpty();
   }
   public int size()                        // return the number of items on the deque
   {
       return N;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       if (item == null)
       {
           throw new java.lang.NullPointerException();
       }
       Node<Item> oldfirst = first.next;
       Node<Item> f = new Node<Item>();
       f.item = item;
       f.next = oldfirst;
       f.prev = first;
       oldfirst.prev = f;
       first.next = f;
       N++;
   }
   public void addLast(Item item)           // add the item to the end
   {
       if (item == null)
       {
           throw new java.lang.NullPointerException();
       }
       Node<Item> oldlast = last.prev;
       Node<Item> l = new Node<Item>();
       l.item = item;
       l.next = last;
       l.prev = oldlast;
       oldlast.next = l;
       last.prev = l;
       N++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
       if (isEmpty())
       {
           throw new java.util.NoSuchElementException();
       }
       Node<Item> oldfirst = first.next;
       first.next = oldfirst.next;
       oldfirst.next.prev = first;
       N--;
       return oldfirst.item;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if (isEmpty())
       {
           throw new java.util.NoSuchElementException();
       }
       Node<Item> oldlast = last.prev;
       last.prev = oldlast.prev;
       oldlast.prev.next = last;
       //oldlast.prev = null;
       N--;
       return oldlast.item;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator<Item>(first);
   }
   private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first.next;
        }

        public boolean hasNext() {
            return current.next != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
   
   public static void main(String[] args)   // unit testing
   {
       Deque<Integer> dq = new Deque<>();
       dq.addFirst(Integer.valueOf(5));
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       dq.addFirst(Integer.valueOf(7));
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       dq.addLast(Integer.valueOf(8));
       dq.addFirst(6);
       dq.addLast(2);
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Size of dq : " + dq.size());
       
       System.out.println("Remove first get : " + dq.removeFirst());
       
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Remove last get : " + dq.removeLast());
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Remove last get : " + dq.removeLast());
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       System.out.println("Remove first get : " + dq.removeFirst());
       
       System.out.print("dq :");
       for (int i: dq)
       {
           System.out.print(i + " ");
       }
       System.out.print("\n");
       
       dq.removeFirst();
       
       //dq.removeLast();
       
       dq.addLast(15);
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