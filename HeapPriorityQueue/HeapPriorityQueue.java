import java.util.Comparator;
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    protected MyArrayList<Entry<K,V>> heap = new MyArrayList<>();

    public HeapPriorityQueue(){
        super();
    }
    public HeapPriorityQueue(Comparator<K> c){
        super(c);
    }

    public HeapPriorityQueue(K[] keys, V[] values){
        super();

        for(int i = 0; i < Math.min(keys.length, values.length); ++i){
            heap.addLast(new PQEntry<>(keys[i], values[i]));
        }

        heapify();
    }

    /**
     * build heap using buttom up construction approach
     */
    protected void heapify(){
        // get indx of deepest non-leaf node
        int j = parentIndx(size()-1);

        // Worst case run time: O(n)
        while(j >= 0){
            downHeap(j);
            --j;
        }
    }

    protected int parentIndx(int i){
        return (i - 1)/2;
    }
    protected int leftcIndx(int i){
        return 2*i+1;
    }
    protected int rightcIndx(int i){
        return 2*i+2;
    }

    protected boolean hasLeftc(int i){
        return leftcIndx(i) < heap.size();
    }
    protected boolean hasRightc(int i){
        return rightcIndx(i) < heap.size();
    }

    protected void swap(int i, int j) {
        Entry<K,V> temp = heap.get(i);
        heap.replace(i, heap.get(j));
        heap.replace(j, temp);
    }

    protected void upHeap(int i){
        while(i > 0){
            int p = parentIndx(i);
            if(compare(heap.get(i), heap.get(p)) >= 0)
                break;
            swap(i, p);
            i = p;
        }
    }

    protected void downHeap(int i){
        while(hasLeftc(i)) {
            int smallestIndx = leftcIndx(i);
            if(hasRightc(i)){
                if(compare(heap.get(smallestIndx), heap.get(rightcIndx(i))) > 0)
                    smallestIndx = rightcIndx(i);
            }
            if(compare(heap.get(i), heap.get(smallestIndx)) <= 0) break;
            swap(smallestIndx, i);
            i = smallestIndx;
        }
    }

    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException{
        checkKey(key); // might throw exception
        Entry<K,V> newest = new PQEntry<>(key, value);
       heap.addLast(newest);
        upHeap(heap.size() - 1);
        return newest;
    }

    public Entry<K,V> removeMin(){
        if (heap.isEmpty()) return null;
        Entry<K,V> min = heap.get(0);
        heap.replace(0,  heap.getLast());
         heap.removeLast();
         downHeap(0);
         return min;
    }

    public Entry<K,V> min(){
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    public int size(){
        return heap.size();
    }
}