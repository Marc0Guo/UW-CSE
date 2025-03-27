import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopKHeap<T extends Comparable<T>> {
    private BinaryMinHeap<T> topK; // Holds the top k items
    private BinaryMaxHeap<T> rest; // Holds all items other than the top k
    private int size; // Maintains the size of the data structure
    private final int k; // The value of k
    private Map<T, MyPriorityQueue<T>> itemToHeap; // Keeps track of which heap contains each item.

    // Creates a topKHeap for the given choice of k.
    public TopKHeap(int k){
        topK = new BinaryMinHeap<>();
        rest = new BinaryMaxHeap<>();
        size = 0;
        this.k = k;
        itemToHeap = new HashMap<>();
    }

    // Returns a list containing exactly the
    // largest k items. The list is not necessarily
    // sorted. If the size is less than or equal to
    // k then the list will contain all items.
    // The running time of this method should be O(k).
    public List<T> topK() {
        return topK.toList();
    }

    // Add the given item into the data structure.
    // The running time of this method should be O(log(n)+log(k)).
    public void insert(T item) {
        if (size < k) { // topk has room
            topK.insert(item); // direct insert to topk
            itemToHeap.put(item, topK);
        } else if (item.compareTo(topK.peek()) > 0) { //item greater than smallest topK
            T smallest = topK.extract(); // put smallest topk into rest
            rest.insert(smallest);
            itemToHeap.put(smallest, rest);

            topK.insert(item); // put item into topK
            itemToHeap.put(item, topK);
        } else { //put item directly into rest
            rest.insert(item);
            itemToHeap.put(item, rest);
        }
        size++;
    }

    // Indicates whether the given item is among the
    // top k items. Should return false if the item
    // is not present in the data structure at all.
    // The running time of this method should be O(1).
    // We have provided a suggested implementation,
    // but you're welcome to do something different!
    public boolean isTopK(T item){
        return itemToHeap.get(item) == topK;
    }

    // To be used whenever an item's priority has changed.
    // The input is a reference to the items whose priority
    // has changed. This operation will then rearrange
    // the items in the data structure to ensure it
    // operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log(n)+log(k)).
    public void updatePriority(T item) {
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Item not in heap");
        }

        // update priority first
        MyPriorityQueue<T> heap = itemToHeap.get(item);
        heap.updatePriority(item);

        // Originally in topK, but now less than smallest is topK
        if (heap == topK && item.compareTo(topK.peek()) < 0) {
            topK.remove(item); // take item from topk to rest
            rest.insert(item);
            itemToHeap.put(item, rest);


            if (!rest.isEmpty()) { // take largest from rest and put in topL
                T restTop = rest.extract();
                topK.insert(restTop);
                itemToHeap.put(restTop, topK);
            }
        // Originally in rest, but greater than smallest in topK
        } else if (heap == rest && item.compareTo(topK.peek()) > 0) {
            // remove item from rest and put in topK
            rest.remove(item);
            topK.insert(item);
            itemToHeap.put(item, topK);

            // move smallest in topK into rest
            if (topK.size() > k) {
                T kBot = topK.extract();
                rest.insert(kBot);
                itemToHeap.put(kBot, rest);
            }
        }
    }

    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log(n)+log(k)).
    public void remove(T item) {
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Item not present");
        }

        MyPriorityQueue<T> heap = itemToHeap.get(item);
        heap.remove(item);
        itemToHeap.remove(item);

        // if item being removed from k, take largest in rest and put in topK
        if (topK.size() < k && !rest.isEmpty()) {
            T restTop = rest.extract();
            topK.insert(restTop);
            itemToHeap.put(restTop, topK);
        }
        size--;
    }
}