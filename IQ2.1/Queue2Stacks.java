/*Queue with two stacks. 
 * Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.
 * */
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Queue2Stacks<Item> //implements Iterable<Item>
{
    private Stack<Item> s1, s2;
    
    public Queue2Stacks()
    {
        s1 = new Stack<Item>();
        s2 = new Stack<Item>();
    }
    public void enqueue(Item item)
    {
        s1.push(item);
    }
    public Item dequeue()
    {
        // Solution here is you should move elements to s2 when dequeue 
        if ((s2.isEmpty()))
        {
            while(!s1.isEmpty())
            {
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }
    public boolean isEmpty()
    {
        return s1.isEmpty() && s2.isEmpty();
    }
    public int size()
    {
        return s1.size() + s2.size();
    }
    
    public static void main(String[] args)
    {
        Queue2Stacks<String> q = new Queue2Stacks<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
    
}