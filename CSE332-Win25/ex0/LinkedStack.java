public class LinkedStack<T> implements MyStack<T> {
    
    private int size;
    private ListNode<T> front;

    private static class ListNode<T>{
        private final T data;
        private ListNode<T> next;

        private ListNode(T data, ListNode<T> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(T data){
            this.data = data;
        }
    }

    public LinkedStack(){
        front = null;
        size = 0;
    }

    // Adds an item into the stack
    public void push(T item){
        front = new ListNode<>(item, front);
        size++;
    };

    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop(){
        if (front == null){
            throw new IllegalStateException("Stack is empty!");
        }
        T temp = front.data;
        front = front.next;
        size--;
        return temp;
    };

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    public T peek(){
        if (front == null) {
            throw new IllegalStateException("Stack is empty!"); 
        }
        return front.data;
    };

    // Returns the number of items in the stack
    public int size(){
        return size;
    };

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty(){
        return front == null;
    };
}
