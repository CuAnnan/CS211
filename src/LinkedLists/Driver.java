package LinkedLists;

import java.util.Random;

public class Driver
{
    public static void main(String[] args)
    {
        DoublyLinkedList<Integer> dll = new DoublyLinkedList<Integer>();
        Random random = new Random();
        
        for(int i = 0; i < 20; i++)
        {
        	int r = random.nextInt(100) + 1;
        	
            dll.add(r);
        }
        
        try
        {
        	int index1 = random.nextInt(20) + 1;
        	int index2 = 20 - index1;
        	
        	System.out.println(dll.getNthFromTail(index1));
        	System.out.println(dll.getNthFromHead(index2));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        
        dll.debug();
        System.out.println(dll.length);
    }

}
