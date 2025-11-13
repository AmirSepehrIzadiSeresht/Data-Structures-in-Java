import java.util.Comparator;
public class HaepAdaptablePriorityQueue<K,V> extends HeapPriorityQueue 
                                            implements I_HeapAdaptablePriorityQueue<K,V>{
    protected static class APQEntry<K,V> extends PQEntry<K,V> {
        private int index;
        public APQEntry(K key, V val, int i){
            super(key, val);
            index = i;
        }

        public void setIndx(int i){
            index = i;
        }

        public int getIndx(){
            return index;
        }
    }

    public HaepAdaptablePriorityQueue(){
        super();
    }
    public HaepAdaptablePriorityQueue(Comparator<K> c){
        super(c);
    }
    public HaepAdaptablePriorityQueue(K[] keys, V[] values){
        super();

        for (int i =0; i < Math.min(keys.length, values.length); ++i){
            heap.addLast(new APQEntry<>(keys[i], values[i], i));
        }

        heapify();
    }

    protected APQEntry<K,V> validateEntry(Entry<K,V> e) throws IllegalArgumentException{
        if(!(e instanceof APQEntry))
            throw new IllegalArgumentException("Invalid entry.");

        APQEntry<K,V> locator = (APQEntry<K,V>) e; // safe cast

        int indx = locator.getIndx();

        if(indx > size() || heap.get(indx) != locator)
            throw new IllegalArgumentException("Invalid entry");

        return locator;
    }

    // override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException{
        checkKey(key); // might throw exception
        Entry<K,V> newest = new APQEntry<>(key, value, size());
        heap.addLast(newest);
        upHeap(size() - 1);
        return newest;
    }

    //overide
    protected void swap(int i, int j){
        super.swap(i, j);
       ((APQEntry<K,V>) heap.get(i)).setIndx(i);
       ((APQEntry<K,V>) heap.get(j)).setIndx(j);
    }

    protected void bubble(int i){
        upHeap(i); //not necesserily performed 
        downHeap(i); // not necessarily performed
    }

    /**
     * O(log) n bound
     */
    public void remove (Entry<K,V> e) throws IllegalArgumentException{
     
        APQEntry<K,V> entry = validateEntry(e);
        int i = entry.getIndx();
        if(i == size() - 1){ 
            heap.removeLast();
        }
        else {

            heap.replace(i, heap.getLast());
            heap.removeLast();
            bubble(i);
        }
    }

    /**
     * O(log n) bound
     */
    public void setKey(Entry<K,V> e, K key) throws IllegalArgumentException{
       APQEntry<K,V> entry = validateEntry(e); // might throw excep
        checkKey(key); // might throw excep
        
        entry.setKey(key);
        bubble(entry.getIndx());
    }
    
    public void setVal(Entry<K,V> e, V val) throws IllegalArgumentException{
       APQEntry<K,V> entry =  validateEntry(e);
       entry.setValue(val);
    }
}
