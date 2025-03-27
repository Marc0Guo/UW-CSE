public class ArrayQueue<T> implements MyQueue<T>{

    private int front;
    private int back;
    private int size;
    private T[] arr;

    public ArrayQueue() {
        arr = (T[]) new Object[10];
        front = 0;
        back = 0;
        size = 0;
    }

    // Adds an item into the queue.
    @Override
    public void enqueue(T item){
        if (size == arr.length){
            resize();
        } 

        arr[back] = item;
        back = (back + 1) % arr.length;
        size ++;
    };


    public void resize(){
        int newSize = arr.length*2;
        T[] newArr = (T[]) new Object[newSize];

        for (int i = 0; i<size; i++){
            newArr[i] = arr[(front + i) % arr.length];
        }

        arr = newArr;
        front = 0;
        back = size;
    }


    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    @Override
    public T dequeue(){
        if (size == 0){
            throw new IllegalStateException("Queue is empty!");
        }

        T temp = arr[front];
        arr[front] = null;
        front = (front + 1) % arr.length;
        size--;
        return temp;
    };

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    @Override
    public T peek(){
        if (size == 0){
            throw new IllegalStateException("Queue is empty!");
        }
        return arr[front];
    };

    // Return the number of items currently in the queue
    public int size(){
        return size;
    };

    // Returns a boolean to indicate whether the queue has items
    public boolean isEmpty(){
        return size == 0;
    };
}
