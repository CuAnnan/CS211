package LinkedLists;

public class Iterator<T>
{
    LinkedList<T> list;
    Node<T> previous;
    Node<T> current;
    
    public Iterator(LinkedList<T> list)
    {
        this.list = list;
        this.reset();
    }
    
    public boolean hasNext()
    {
        return this.current != null;
    }
    
    public Node<T> getCurrent()
    {
        return this.current;
    }
    
    public void next()
    {
        this.previous = this.current;
        this.current = this.current.next;
    }
    
    public void reset()
    {
        this.previous = null;
        this.current = this.list.head;
    }
}