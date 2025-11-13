

public class ArrayQueue<T> {


    public static class ArrQueue<T> {
        private int sz = 0;
        private int f = 0;
        private T[] data;

        public ArrQueue(int capacity){
            data = (T[]) new Object[capacity];
        }

        public int size(){
            return sz;
        }

        public boolean isEmpt(){
            return (0 == sz);
        }

        public void enqueue(T ele) throws IllegalStateException{
            if(sz == data.length){
                throw new IllegalStateException("Queue is full.");
            }
            int avail = (f + sz) % data.length;
            data[avail] = ele;
            ++sz;
        }

        public T dequeue(){
            if(isEmpt()){
                return null;
            }
            T ele = data[f];
            data[f] = null;
            f = (f + 1) % data.length;
            --sz;
            return ele;
        }

        public void showQueue(){
            for(int i = f; data[i]  != null; i = (i + 1)%data.length){
                System.out.print(" " + data[i] + " ");
                
            }
            System.out.println("");
        }


    }

    public static void main(){
        ArrQueue<Integer> queue = new ArrQueue<>(10);

        queue.enqueue(10);
        queue.showQueue();
        queue.enqueue(5);
        queue.showQueue();
        queue.enqueue(4);
        queue.showQueue();
        queue.dequeue();
        queue.showQueue();
    }

}



