package LinkedLists;

/**
 * An exception for when you try to do stupid things
 */
class LinkedListIndexOutOfBoundsException extends Exception
{
    private static final long serialVersionUID = -3774191810320314003L;
    
    public LinkedListIndexOutOfBoundsException(String message)
    {
        super(message);
    }
}

/**
 * A generic linked list class
 * @author Éamonn "Wing" Kearns
 * @param <T>   The type of the object to be listed linkedly
 */
public class LinkedList<T>
{
    /**
     * The counter for the length of the linked list
     */
    protected int length;
    /**
     * The head of the linked list
     */
    protected Node<T> head;
    /**
     * The tail of the linked list
     */
    protected Node<T> tail;
    
    // mid?
    
    /**
     * The constructor just initialises the properties. Which I think is unneeded, but it's nice for clarity
     */
    public LinkedList()
    {
        length = 0;
        head = null;
        tail = null;
    }
    
    public Iterator<T> getIterator()
    {
        return new Iterator<T>(this);
    }
    
    /**
     * Add content to the tail of the linked list. This is actually just a helper method that instantiates a new node and calls the method for that footprint.
     * @param content   The content to add to the linked list
     */
    public void addToTail(T content)
    {
        addToTail(new Node<T>(content));
    }
    
    /**
     * Actual add to tail method. Adds a node to the tail of the Linked List.
     * @param next
     */
    public void addToTail(Node<T> next)
    {
        if(this.length == 0)
        {
            // if the LL has no contents, then we set the head and tail to next
            head = next;
            tail = next;
        }
        else
        {
            // otherwise we set the tail's next Node to be the new node
            tail.setNext(next);
            // and set the tail to be the new node
            tail = next;
        }
        // and we increase the length by 1
        this.length++;
    }
    
    /**
     * Helper method to add content to the Linked List directly.
     * @param content   The content to add
     */
    public void addToHead(T content)
    {
        addToHead(new Node<T>(content));
    }
    
    /**
     * Actual method to add a Node to the head of the LinkedList
     * @param previous  The node to add
     */
    public void addToHead(Node<T> previous)
    {
        
        if(this.length == 0)
        {
            // if we have no nodes, then we just set the first and last nodes to be the new node
            head = previous;
            tail = previous;
        }
        else
        {
            // otherwise we set the next node of our new node to be the current head
            previous.setNext(head);
            // and then set the head to be the 
            head = previous;
        }
        // and increase the length by one
        this.length++;
    }
    
    /**
     * A shortcut method. By default if we're adding, we add to the tail. By convention.
     */
    public void add(T content)
    {
        this.addToTail(content);
    }
    
    public void push(T content)
    {
    	this.addToHead(content);
    }
    
    /**
     * A shortcut method. By default if we're adding, we add to the tail. By convention.
     */
    public void add(Node<T> next)
    {
        this.addToTail(next);
    }
    
    public int find(T content)
    {
        return find(new Node<T>(content));
    }
    
    public int find(Node<T> n)
    {
        Node<T> current = head;
        int index = -1;
        int searchIndex = 0;
        
        do
        {
            if(current.equals(n))
            {
                index = searchIndex;
            }
            else
            {
                current = current.getNext();
            }
            searchIndex ++;
        }while(current.hasNext() && index == -1);
        return index;
    }
    
    public T pop()
    {
        if(this.head == null)
        {
            return null;
        }
    	T value = this.head.getContents();
    	this.head = this.head.next;
    	return value;
    }
    
    /**
     * Remove an element by value
     * @param value	Returns the element that has been removed
     * @return
     */
    public Node<T> remove(T value)
    {
    	Node<T> current = this.head;
    	Node<T> previous = this.head;
    	Node<T> toRemove = null;
    	
    	while(current.hasNext() && toRemove == null)
    	{
    		previous = current;
    		current = current.getNext();
    		if(current.equals(value))
    		{
    			toRemove = current;
    		}
    	}
    	
    	if(toRemove != null)
    	{
    		previous.setNext(current.getNext());
    	}
    	
    	return toRemove;
    }
    
    /**
     * The logic for removing an element at an index is a little tricky to follow. So pay attention, Wing.
     * @param index The index of the element to remove
     * @throws LinkedListIndexOutOfBoundsException
     */
    public void removeNth(int index) throws LinkedListIndexOutOfBoundsException
    {
        if(index < 0)
        {
            // can't have negative indices.
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        if(index >= length)
        {
            // can't remove something after the last element
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        
        // remove the head.
        if(index == 0)
        {
            // Simple to do, just set the reference to the head to be the next element in the List.
            head = head.getNext();
            // reduce the length
            length--;
            return;
        }
        Node<T> previous = head;
        
        for(int i = 1; i < index; i++)
        {
            // find the element *before* the element to remove
            // we do this because Linked Lists can't go backwards
            previous = previous.getNext();
        }
        // from that, find the element to remove
        Node <T> toRemove = previous.getNext();
        
        if(toRemove == tail)
        {
            // if it's the tail element, we just null the reference.
            previous.setNext(null);
            // and set the length down by one
            length --;
            return;
        }
        // otherwise we connect the element before we're removing to the element after the one we're removing
        previous.setNext(toRemove.getNext());
        // and reduce the length
        length --;
        
    }
    
    /**
     * A method to merge another Linked List onto the end of this Linked List 
     * @param that  The Linked List to merge onto this one.
     */
    public void merge(LinkedList<T> that)
    {
        // If this linked list is empty, just move over the head, tail and length.
        if(this.length == 0)
        {
            this.head = that.head;
            this.tail = that.tail;
            this.length = that.getLength();
            return;
        }
        
        // otherwise what we do is point the tail of this to the head of that
        this.tail.setNext(that.head);
        // set the tail of this to the tail of that.
        this.tail = that.tail;
        // and add the length of that to the length of this
        this.length += that.getLength();
    }
    
    /**
     * A method to insert an element before a given index.
     * @param index     The index of the element to insert this content before
     * @param content   The content to be inserted
     * @throws LinkedListIndexOutOfBoundsException
     */
    public void insertBefore(int index, T content) throws LinkedListIndexOutOfBoundsException
    {
        this.insertBefore(index, new Node<T>(content));
    }
    
    public void insertBefore(int index, Node<T> node) throws LinkedListIndexOutOfBoundsException
    {
        if(index < 0)
        {
            // can't have negative indices.
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        if(index >= length)
        {
            // can't insert something before the element after the last element. It has to be between 0 and length-1
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        
        // the 0th index is just addToHead
        if(index == 0)
        {
            this.addToHead(node);
            return;
        }
        
        // Figure out where we're inserting the node
        Node<T> a = head;
        
        for(int i = 1; i < index; i++)
        {
            // loop to where the actual a is
            a = a.getNext();
        }
        // b is the one after a
        Node<T> b = a.getNext();
        
        // insert the node between a and b by
        // setting a's next to node
        a.setNext(node);
        // and node's next to b
        node.setNext(b);
        // and increase the length by 1
        this.length++;
    }
    
    /**
     * Helper method
     * @param index
     * @param content
     * @throws LinkedListIndexOutOfBoundsException
     */
    public void insertAfter(int index, T content) throws LinkedListIndexOutOfBoundsException
    {
        this.insertAfter(index, new Node<T>(content));
    }
    
    /**
     * Insert a node after the node at a given index
     * @param index
     * @param content
     * @throws LinkedListIndexOutOfBoundsException
     */
    public void insertAfter(int index, Node<T> newNode) throws LinkedListIndexOutOfBoundsException
    {
        // again, bound constraining
        if(index < 0)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        if(index >= length)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        
        // insert the node after a and before b (if b exists)
        Node<T> a = head;
        
        for(int i = 0; i < index; i++)
        {
            a = a.getNext();
        }
        if(a.hasNext())
        {
            newNode.setNext(a.getNext());
        }
        a.setNext(newNode);
        this.length++;
    }
    
    /**
     * Set the value of a given index to a specified value
     * @param index The index to set
     * @param value The value to set it to
     * @throws LinkedListIndexOutOfBoundsException 
     */
    public void set(int index, T value) throws LinkedListIndexOutOfBoundsException
    {
        if(index < 0)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        if(index >= length)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+index+" out of bounds for Linked List size "+this.length);
        }
        Node<T> n = head;
        for(int i = 0; i < index; i++)
        {
            n = n.getNext();
        }
        n.setContents(value);
    }
    
    /**
     * Get the value of the given index
     * @param index The index of the Linked List to address
     * @return The value of the node at the index
     * @throws LinkedListIndexOutOfBoundsException
     */
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
        Node<T> current = head.getNext();
        int i = 1;
        while(i < index - 1)
        {
            // and, as long as we have to go one higher, go one higher
            current = current.getNext();
            // and increment the ticker
            i++;
        }
        // and return the value of current.
        return current.getContents();
    }
    
    public Node<T> getHead()
    {
        return this.head;
    }
    
    /**
     * A method to cut a section of the linked list out from the specified start point to the end of the linked list
     * @param start
     * @return
     * @throws LinkedListIndexOutOfBoundsException
     */
    public LinkedList<T> cut(int start) throws LinkedListIndexOutOfBoundsException
    {
        if(start < 0)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+start+" out of bounds for Linked List size "+this.length);
        }
        if(start >= length)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+start+" out of bounds for Linked List size "+this.length);
        }
        // the linked list we'll return
        LinkedList<T> l = new LinkedList<T>();
        // the first node of the new linked list
        Node<T> first = this.head;
        // the new last node of this linked list
        Node<T> last = null;
        
        for(int i = 0; i < start; i++)
        {
            // move through the linked list to get the node before the one to remove and the one to remove
            last  = first;
            // and the one to remove
            first = first.getNext();
        }
        // add the first element to the new linked list
        l.add(first);
        // set the length of the linked list to the difference of this length and the start index
        l.length = this.length - start;
        // I don't remember why I wrapped this in an if statement. Will review later.
        if(last != null)
        {
            last.setNext(null);
        }
        
        // the end of the new list is the current end of this list
        l.tail = this.tail;
        // this list now has a different tail
        this.tail = last;
        // and the length has been reduced to the index of the start of the new node
        this.length = start;
        // return the list
        return l;
    }
    
    /**
     * Cut out a sub list between start and end
     * @param start The start index of the list to remove
     * @param end   The end index of the list to remove
     * @return      The removed 
     * @throws LinkedListIndexOutOfBoundsException
     */
    public LinkedList<T> cut(int start, int end) throws LinkedListIndexOutOfBoundsException
    {
        // cut the linked list out at its start
        LinkedList<T> out = this.cut(start);
        // cut the bits off the end of the out list that are beyond the sought bounds
        LinkedList<T> tail = out.cut(end - start);
        // merge the tail back onto the end of this 
        this.merge(tail);
        // return the sought list
        return out;
    }
    
    /**
     * A method to get a copy of the list from the start position. This is just a helper method for the start end profile
     * @param start The index of the start point to copy from.
     * @return  A copy of the sub linked list between the provided start point and the end of this
     * @throws LinkedListIndexOutOfBoundsException
     */
    public LinkedList<T> slice(int start) throws LinkedListIndexOutOfBoundsException
    {
        return this.slice(start, true);
    }
    
    /**
     * A method to get a copy of the list from the start position. This is just a helper method for the start end profile but that allows you to toggle off copy by reference
     * @param start             The start index to copy from
     * @param copyByReference   Whether or not to copy by reference
     * @return                  The sublist
     * @throws LinkedListIndexOutOfBoundsException
     */
    public LinkedList<T> slice(int start, boolean copyByReference) throws LinkedListIndexOutOfBoundsException
    {
        return this.slice(start, this.length -1, copyByReference);
    }
    
    /**
     * 
     * @param start
     * @param end
     * @return
     * @throws LinkedListIndexOutOfBoundsException
     */
    public LinkedList<T> slice(int start, int end) throws LinkedListIndexOutOfBoundsException
    {
        return this.slice(start, end, true);
    }
    
    /**
     * A method to get a copy of the list from the start position to the end position. 
     * @param start
     * @param end
     * @return
     * @throws LinkedListIndexOutOfBoundsException
     */
    public LinkedList<T> slice(int start, int end, boolean copyByReference) throws LinkedListIndexOutOfBoundsException
    {
        if(start < 0)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+start+" out of bounds for Linked List size "+this.length);
        }
        if(end >= length)
        {
            throw new LinkedListIndexOutOfBoundsException("Linked List index "+end+" out of bounds for Linked List size "+this.length);
        }
        // create a linked list to hold a copy of the data
        LinkedList<T> sub = new LinkedList<T>();
        
        // find the start of the list to add
        Node<T> next = this.head;
        int i;
        for(i = 0; i < start; i++)
        {
            next = next.getNext();
        }
        if(copyByReference)
        {
            sub.add(next);
        }
        else
        {
            sub.add(next.getContents());
        }
        for(; i < end; i ++)
        {
            next = next.getNext();
            
            if(copyByReference)
            {
                sub.add(next);
            }
            else
            {
                sub.add(next.getContents());
            }
        }
        
        return sub;
    }
    
    /**
     * Get the length of the Linked List
     * @return  The length of the linked list
     */
    public int getLength()
    {
        return this.length;
    }
    
    public void empty()
    {
    	this.tail = null;
    	this.head = null;
    }
    
    /**
     * A method to help debugging. This is only useful for primitive and primitive-like contents that can reasonably be toStringed
     */
    public void debug()
    {
        String out = "";
        
        Node<T> next = head;
        while(next.hasNext())
        {
            out += next.getContents().toString()+", ";
            next = next.getNext();
        }
        out += next.getContents().toString();
        System.out.println(out);
    }
    
}
