package LinkedLists;

/**
 * A class to represent an individual node in a linked list.
 * @param <T>   Makes the node generic. We like generics.
 */
public class Node<T>
{
    /**
     * What is stored in the node
     */
    T contents;
    /**
     * The node that this node points to
     */
    Node<T> next;
    
    /**
     * Constructor
     * @param contents
     */
    public Node(T contents)
    {
        // set the contents
        this.contents = contents;
        // null the nex
        this.next = null;
    }
    
    /**
     * This is just a helper
     * @return  Returns true if there's a next node or false if this is the last node
     */
    public boolean hasNext()
    {
        return this.next != null;
    }
    
    /**
     * Get the next Node
     * @return  The next Node
     */
    public Node<T> getNext()
    {
        return this.next;
    }
    
    /**
     * Set the next Node
     * @param next The next Node
     */
    public void setNext(Node<T> next)
    {
        this.next = next;
    }
    
    /**
     * Get the packaged data the Node contains
     * @return  The content of the Node
     */
    public T getContents()
    {
        return this.contents;
    }
    
    /**
     * Set the contents of the Node
     * @param contents  The packaged contents.
     */
    public void setContents(T contents)
    {
        this.contents = contents;
    }
    
    /**
     * Check whether the contents of a node is the same as the contents of another node
     * @param other
     * @return
     */
    public boolean equals(Node<T> other)
    {
        return this.contents.equals(other.getContents());
    }
    
    public boolean equals(Object other)
    {
        return this.contents.equals(other);
    }
}
