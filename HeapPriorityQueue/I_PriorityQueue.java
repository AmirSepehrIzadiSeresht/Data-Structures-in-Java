public interface I_PriorityQueue<K,V> {
    Entry<K,V> insert(K key, V val) throws IllegalArgumentException;
    Entry<K,V> min();
    Entry<K,V> removeMin();
    int size();
    boolean isEmpty();
    
} 
