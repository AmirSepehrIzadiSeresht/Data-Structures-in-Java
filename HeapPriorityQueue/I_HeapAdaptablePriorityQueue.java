public interface I_HeapAdaptablePriorityQueue<K,V> {
    void remove(Entry<K,V> e) throws IllegalArgumentException;
    void setKey(Entry<K,V> e, K key) throws IllegalArgumentException;
    void setVal(Entry<K,V> e, V val) throws IllegalArgumentException;
}
