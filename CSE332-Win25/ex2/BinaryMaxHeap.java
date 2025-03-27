import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryMaxHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
   private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
                     // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    // Root: 0
    // Parent: (i-1)/2
    // Left Child: 2*i+1
    // Right Child: Left + 1

    public BinaryMaxHeap() {
        // This line just creates an array of type T. We're doing it this way just
        // because of weird java generics stuff (that I frankly don't totally understand)
        // If you want to create a new array anywhere else (e.g. to resize) then
        // You should mimic this line. The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        itemToIndex.put(arr[i], i);
        itemToIndex.put(arr[j], j);
    }

    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (arr[i].compareTo(arr[parent]) > 0) { //item at i greater than parent, swap position
                swap(i, parent);
                i = parent; //update index
            } else {
                break;
            }
        }
    }

    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < size && arr[left].compareTo(arr[largest]) > 0) { //Left child exist and less than largest
                largest = left;
            }
            if (right < size && arr[right].compareTo(arr[largest]) > 0) { // right child exist and  greater than largest
                largest = right;
            }
            if (largest == i) {
                break;
            }
            swap(i, largest);
            i = largest;
        }
    }



    // copy all items into a larger array to make more room.
    private void resize() {
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length * 2);

        for (int i = 0; i < size; i++) {
            larger[i] = arr[i];
        }
        arr = larger;
    }

    @Override
    // Adds the given item into the priority queue
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert null item");
        }
        if (size == arr.length) {
            resize();
        }
        arr[size] = item; // place item at the end and update index to map
        itemToIndex.put(item, size);
        percolateUp(size);
        size++;
    }

    @Override
    // removes and returns the "top priority" item from the priority queue
    // Throws an IllegalStateException if the data structure is empty.
    // The running time of this method should be O(log n).
    public T extract() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        T max = arr[0];
        arr[0] = arr[size - 1]; // move last item to root
        itemToIndex.put(arr[0], 0);
        arr[size - 1] = null; // clear the last item
        itemToIndex.remove(max);
        size--;
        percolateDown(0); // place new first in correct place
        return max;
    }

    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }
        T rm = arr[index];
        swap(index, size - 1); // swap with last item
        arr[size - 1] = null; // clear last and index
        itemToIndex.remove(rm);
        size--;
        if (index < size) {
            updatePriority(index);
        }
        return rm;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void remove(T item){
        remove(itemToIndex.get(item));
    }

    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index) {
        if (index > 0 && arr[index].compareTo(arr[(index - 1) / 2]) > 0) { // item at index greater than parent. Index not root
            percolateUp(index);
        } else {
            percolateDown(index);
        }
    }

    // This method gets called after the client has
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void updatePriority(T item){
        if(!itemToIndex.containsKey(item)){
                throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        updatePriority(itemToIndex.get(item));
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public boolean isEmpty(){
        return size == 0;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public int size(){
        return size;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return arr[0];
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public List<T> toList(){
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++){
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // For debugging
    public String toString(){
        if(size == 0){
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for(int i = 1; i < size; i++ ){
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }

}