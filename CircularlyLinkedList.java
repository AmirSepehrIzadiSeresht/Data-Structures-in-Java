public class CircularlyLinkedList<T> {
    private static class Node<T> {
        private T ele;
        private Node<T> next;
        public Node(T ele, Node<T> next) {
            this.ele = ele;
            this.next = next;
        }

        public T getEle() {
            return ele;
        }
        public Node<T> getNext(){
            return next;
        }
        public void setNext(Node<T> next){
            this.next = next;
        }
    }
    
    private Node<T> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {};

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){return size == 0; };

    // interfaces: get first - get last

    public T getFirst(){
        if(isEmpty()) {
            return null;
        }
        return tail.getNext().getEle();
    }

    public T getLast() {
        if(isEmpty()) {
            return null;
        }

        return tail.getEle();
    }

    //oprs: add first - rotate -add last
    public void rotate() {
        if(tail != null){
            tail = tail.getNext();
        } 
    }

    public void addFirst(T ele) {
        if(isEmpty()) {
            tail = new Node<>(ele, null);
            tail.setNext(tail);
        } 

        Node<T> head = tail.getNext();
        tail.setNext(new Node<>(ele, head));

        ++size;
    }

    public void addLast(T ele) {
        addFirst(ele);
        tail = tail.getNext();
    }

    public T removeFirst() {
        if(isEmpty()){
            return null;
        }

        Node<T> head = tail.getNext();
        if(head == tail){
            tail = null;
        } else {
            tail.setNext(head.getNext());
        }

        --size;

        return head.getEle();
    }
}
