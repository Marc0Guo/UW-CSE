import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable <K,V> implements DeletelessDictionary<K,V>{
    private List<Item<K,V>>[] table; // the table itself is an array of linked lists of items.
    private int size;
    private static int[] primes = { 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853 };
    private int primeIndex = 0;

    public ChainingHashTable(){
        table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, primes[0]);
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    // TODO
    public V insert(K key, V value) {
        if (size >= table.length * 0.75) { // load factor threshold 0.75
            resize();
        }

        int index = Math.floorMod(key.hashCode(), table.length);
        List<Item<K, V>> bucket = table[index]; // create bucket of that index

        // check existence of key
        for (Item<K, V> item : bucket) {
            if (item.key.equals(key)) {
                V oldValue = item.value;
                item.value = value;
                return oldValue;
            }
        }

        // create key value pair if there isn't one
        bucket.add(new Item<>(key, value));
        size++;
        return null;
    }

    private void resize() {
        int newSize;

        // when we have prime number to use
        if (primeIndex < primes.length - 1) {
            primeIndex++;
            newSize = primes[primeIndex];
        } else { // no prime number to use
            newSize = table.length * 2 + 1;
        }

        // Create new hashtable using `newSize`
        List<Item<K, V>>[] oldTable = table;
        table = (LinkedList<Item<K, V>>[]) Array.newInstance(LinkedList.class, newSize);
        for (int i = 0; i < newSize; i++) {
            table[i] = new LinkedList<>();
        }

        // reset the size and move everything back
        size = 0; // since insert will update size
        for (List<Item<K, V>> bucket : oldTable) {
            for (Item<K, V> item : bucket) {
                insert(item.key, item.value);
            }
        }
    }

    // TODO
    public V find(K key) {
        int index = Math.floorMod(key.hashCode(), table.length);
        for (Item<K, V> item : table[index]) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    // TODO
    public boolean contains(K key){
        int index = Math.floorMod(key.hashCode(), table.length);
        for (Item<K, V> item : table[index]) {
            if (item.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    // TODO
    public List<K> getKeys() {
        List<K> keyList = new ArrayList<>();
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                keyList.add(item.key);
            }
        }
        return keyList;
    }

    // TODO
    public List<V> getValues(){
        List<V> valList = new ArrayList<>();
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                valList.add(item.value);
            }
        }
        return valList;
    }

    public String toString(){
        String s = "{";
        s += table[0];
        for(int i = 1; i < table.length; i++){
            s += "," + table[i];
        }
        return s+"}";
    }

}
