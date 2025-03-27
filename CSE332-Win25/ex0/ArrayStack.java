public class ArrayStack<T> implements MyStack<T> {
    
    private int front;
    private int size;
    private T[] arr;

    public ArrayStack(){
        arr = (T[]) new Object[10];
        front = 0;
        size = 0;
    }

    public void resize(){
        int newSize = arr.length*2;
        T[] newArr = (T[]) new Object[newSize];

        for (int i = 0; i<size; i++){
            newArr[i] = arr[(front + i) % arr.length];
        }

        arr = newArr;
        front = 0;
    }

    // Adds an item into the stack
    @Override
    public void push(T item){
        if (arr.length == size){
            resize();
        }

        int index = (front + size) % arr.length; 
        arr[index] = item;
        size++;
    };

    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    @Override
    public T pop(){
        if (size == 0){
            throw new IllegalStateException("The stack is empty!");
        }

        int index = (front + size - 1) % arr.length; // Calculate index of the top element
        T temp = arr[index];
        arr[index] = null; 
        size--;
        return temp;
    };

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    @Override
    public T peek(){
        if (size == 0){
            throw new IllegalStateException("The stack is empty!");
        }

        int index = (front + size - 1) % arr.length; 
        return arr[index];
    };

    // Returns the number of items in the stack
    @Override
    public int size(){
        return size;
    };

    // Returns a boolean indicating whether the stack has items
    @Override
    public boolean isEmpty(){
        return size==0;
    };
}
