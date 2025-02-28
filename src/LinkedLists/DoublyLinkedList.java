package LinkedLists;

class DLLNode<T> extends Node<T>
{
	DLLNode<T> previous;
	DLLNode<T> next;

	
	public DLLNode(T contents)
	{
		super(contents);
		this.previous = null;
		this.next = null;
	}
	
	public boolean hasPrevious()
	{
		return this.previous == null;
	}
	
	public DLLNode<T> getPrevious()
	{
		return this.previous;
	}
	
	public void setPrevious(DLLNode<T> previous)
	{
		this.previous = previous;
	}
	
	public void setNext(DLLNode<T> next)
	{
		this.next = next;
	}
	
	public DLLNode<T> getNext()
	{
		return this.next;
	}
	
	public boolean hasNext()
	{
		return this.next != null;
	}
	
}

class DoublyLinkedListIterator<T>
{
	DoublyLinkedList<T> references;
	DLLNode<T> current;
	DLLNode<T> previous;
	
	public DoublyLinkedListIterator(DoublyLinkedList<T> list)
	{
		this.references = list;
		reset();
	}
	
	public void reset()
	{
		this.current = this.references.getHead();
		this.previous = null;
	}
	
	public DLLNode<T> getCurrent()
	{
		return this.current;
	}
	
	public boolean hasNext()
	{
		return this.current.hasNext();
	}
	
	public boolean isAtEnd()
	{
		return !this.current.hasNext();
	}
}

public class DoublyLinkedList<T> extends LinkedList<T>
{
	protected DLLNode<T> head;
	protected DLLNode<T> tail;
	
	public DoublyLinkedList()
	{
		super();
	}
	
	public DLLNode<T> getHead()
	{
		return this.head;
	}
	
	public void addToHead(T content)
	{
		this.addToHead(new DLLNode<T>(content));
	}
	
	/**
     * Actual method to add a Node to the head of the LinkedList
     * @param previous  The node to add
     */
    public void addToHead(DLLNode<T> previous)
    {
        if(this.length == 0)
        {
            // if we have no nodes, then we just set the first and last nodes to be the new node
            tail = previous;
        }
        else
        {
            // otherwise we set the next node of our new node to be the current head
            previous.setNext(head);
            head.setPrevious(previous);
            // and then set the head to be the 
        }
        // and increase the length by one
        head = previous;
        this.length++;
    }
    
    public void addToTail(T content)
    {
    	this.addToTail(new DLLNode<T>(content));
    }
    
    public void addToTail(DLLNode<T> next)
    {
    	if(this.length == 0)
        {
    		// if we have no nodes, then we just set the first and last nodes to be the new node
            head = next;
        }
    	else
    	{
    		next.setPrevious(this.tail);
    		this.tail.setNext(next);
    	}
        tail = next;
    	this.length++;
    }
    
    public void insertBefore(int index, T content) throws LinkedListIndexOutOfBoundsException
    {
        this.insertBefore(index, new DLLNode<T>(content));
    }
    
    public void insertBefore(int index, DLLNode<T> content) throws LinkedListIndexOutOfBoundsException
    {
    	int i = 0;
    	DLLNode<T> current = head;
    	while(current != null && head.hasNext() && i < index)
    	{
    		i++;
    		current = current.getNext();
    	}
    	if(current.hasPrevious())
    	{
    		content.setNext(current);
    		current.getPrevious().setNext(content);
    	}
    	
    }
    
    public void insertAfter(int index, T content) throws LinkedListIndexOutOfBoundsException
    {
    	this.insertAfter(index, new DLLNode<T>(content));
    }
    
    /**
     * Get the value of the given index
     * @param index The index of the Linked List to address
     * @return The value of the node at the index
     * @throws LinkedListIndexOutOfBoundsException
     */
    public T getNthFromTail(int index) throws LinkedListIndexOutOfBoundsException
    {
        if(index < 0)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        if(index >= length)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        // no need to search for these two
        if(index == 0)
        {
            return tail.getContents();
        }
        if(index == this.length - 1)
        {
            return head.getContents();
        }
        
        // since the first node has already been checked and returned by now, we can start at the one after it 
        DLLNode<T> current = tail.getPrevious();
        int i = 1;
        while(i < index - 1)
        {
            // and, as long as we have to go one higher, go one higher
            current = current.getPrevious();
            // and increment the ticker
            i++;
        }
        // and return the value of current.
        return current.getContents();
    }
    
    public T getNthFromHead(int index) throws LinkedListIndexOutOfBoundsException
    {
        if(index < 0)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        if(index >= length)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        // no need to search for these two
        if(index == 0)
        {
            return head.getContents();
        }
        if(index == this.length - 1)
        {
            return tail.getContents();
        }
        // since the first node has already been checked and returned by now, we can start at the one after it 
        DLLNode<T> current = head.getNext();
        
        int i = 1;
        while(i < index)
        {
            // and, as long as we have to go one higher, go one higher
            current = current.getNext();
            // and increment the ticker
            i++;
        }
        
        // and return the value of current.
        return current.getContents();
    }
    
    
    /**
     * A method to help debugging. This is only useful for primitive and primitive-like contents that can reasonably be toStringed
     */
    public void debug()
    {
        String out = "";
        
        DLLNode<T> next = head;
        while(next.hasNext())
        {
        	out += next.getContents().toString()+", ";
            next = next.getNext();
        }
        out += next.getContents().toString();
        System.out.println(out);
    }
}
