import java.util.Comparator;
public abstract class AbstractPriorityQueue<K,V> implements I_PriorityQueue<K,V> {
        protected static class PQEntry<K,V> implements Entry<K,V> {
            private V value;
            private K key;

            public PQEntry(K k, V v) {
                value = v;
                key = k;
            }

            public K getKey(){
                return key;
            }

            public V getValue(){
                return value;
            }

            protected void setValue(V value){
                this.value = value;
            }

            protected void setKey(K key){
                this.key = key;
            }
        } // -- end of PQEntry ---

        private Comparator<K> comp;

        protected AbstractPriorityQueue(Comparator<K> c){
            comp = c;
        }
        protected AbstractPriorityQueue(){
            // natural ordering of key values
            this(new DefaultComparator<K>());
        }

        protected int compare(Entry<K,V> a, Entry<K,V> b){
            return comp.compare(a.getKey(), b.getKey());
        }

        /** Determines whether a key is valid */
        protected boolean checkKey(K key) throws IllegalArgumentException{
            try{
                return (comp.compare(key,key) == 0);
            }catch(ClassCastException e){
                throw new IllegalArgumentException("Incompatible key");
            }
        }

        public boolean isEmpty() {return size() == 0;}
}

