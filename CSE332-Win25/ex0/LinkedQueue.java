public class LinkedQueue<E> implements MyQueue<E>{
    
    private int size;
    private ListNode<E> front;
    private ListNode<E> back;

    private static class ListNode<E>{
        private final E data;
        private ListNode<E> next;

        private ListNode(E data, ListNode<E> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(E data){
            this.data = data;
        }
    }

    
    // Adds an item into the queue.
    @Override
    public void enqueue(E item){
        ListNode<E> newNode = new ListNode<>(item, null); 
        
        // Empty case
        if (back == null){
            front = newNode;
            back = newNode;
        }
        else{
            back.next = newNode;
            back = newNode;
        }
        size++;
    };


    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    @Override
    public E dequeue(){
        if (front == null) {
            throw new IllegalStateException("Queue is empty!");
        }

        E temp = front.data;
        front = front.next;
        if(front == null){
            back = null;
        }
        size--;
        return temp;
    };

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    @Override
    public E peek(){
        if (front == null) {
            throw new IllegalStateException("Queue is empty!");
        }

        return front.data;
    };

    // Return the number of items currently in the queue
    @Override
    public int size(){
        return size;
    };

    // Returns a boolean to indicate whether the queue has items
    @Override
    public boolean isEmpty(){
        return size == 0;
    };
}
