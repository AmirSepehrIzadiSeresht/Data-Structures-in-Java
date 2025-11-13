import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.swing.*;
import javax.swing.tree.TreeNode;

import java.awt.*;
import java.util.List;

import org.w3c.dom.Node;

public class Tree {
    public interface Position<T> {
        public T getEle();
    }

    public interface I_Tree<T> extends Iterable<T> {
        Position<T> getRoot();

        Position<T> getParent(Position<T> pos) 
            throws IllegalArgumentException;

        Iterable<Position<T>> children(Position<T> pos); // AbsteractBinaryTree

        int numChildren(Position<T> pos) // AbsteractBinaryTree
            throws IllegalArgumentException;

        boolean isInternal(Position<T> pos)
            throws IllegalArgumentException;

        boolean isExternak(Position<T> pos)
            throws IllegalArgumentException;

        boolean isRoot(Position<T> pos) 
            throws IllegalArgumentException;

        int size();

        boolean isEmpty();

        Iterator<T> iterator();

        Iterable<Position<T>> positions();
    } 


    public interface I_BinaryTree<T> extends I_Tree<T> {
        /** returns position of p's left child (or null) if no child exists*/
        Position<T> getLeftChild(Position<T> pos) // in LinkedBinaryTree
            throws IllegalArgumentException; 

        /** returns postion of p's right child */
        Position<T> getRightChild(Position<T> pos) // in LinkedBinaryTree
            throws IllegalArgumentException;

        /** Returns the position of p's sibling (or null if no sibling exists) */
        Position<T> sibling(Position<T> pos) // in AbsteractBinaryTree
            throws IllegalArgumentException;


    }



    /** Abstract base class providing some functionality of the tree base class */

    public static abstract class AbstractTree<T> implements I_Tree<T> {
        public boolean isInternal(Position<T> pos) {
            return numChildren(pos) > 0;
        }

        public boolean isExternak(Position<T> pos){
            return numChildren(pos) == 0;
        }

        public boolean isRoot(Position<T> pos){
            return pos == getRoot();
        }

        public boolean isEmpty(){
            return size() == 0;
        }

        protected void preorderSubtree(Position<T> pos, List<Position<T>> snapshot) {
            snapshot.add(pos);
            for(Position<T> c : children(pos)){
                preorderSubtree(c, snapshot);
            }
        }

        public Iterable<Position<T>> preorder() {
            List<Position<T>> snapshot = new ArrayList<>();
            if(!isEmpty()){
                preorderSubtree(getRoot(), snapshot);
            }
            return snapshot;
        }

        public Iterable<Position<T>> positions(){
            return preorder();
        }

        private class ElementIterator implements Iterator<T> {
            // adapter design pattern
            Iterator<Position<T>> positionIterator = positions().iterator();
            public boolean hasNext(){return positionIterator.hasNext();}
            public T next(){return positionIterator.next().getEle();}
            public void remove(){positionIterator.remove();}
        }

        public Iterator<T> iterator() {
            return new ElementIterator();
        }


        public Iterable<Position<T>> breadthFirst(){
            List<Position<T>> snapshot = new ArrayList<>();
            if(!isEmpty()){
                Queue<Position<T>> fringe = new ArrayDeque<>();
                fringe.add(getRoot());

                while(!fringe.isEmpty()){
                    Position<T> p = fringe.poll();
                    snapshot.add(p);
                    for(Position<T> c : children(p)){
                        fringe.add(c);
                    }
                }
            }

            return snapshot;
        }



    }// --- end of abstract tree ---


    public static abstract class  AbsteractBinaryTree<T> extends AbstractTree<T> 
        implements I_BinaryTree<T> {
        public Position<T> sibling(Position<T> pos) {
            Position<T> parent = getParent(pos);
            if(parent == null) {
                return null;
            }
            return (pos == getLeftChild(parent)) ? getRightChild(parent) : getLeftChild(parent);
        }

        public int numChildren(Position<T> pos) {
            int count = 0;
            if(getLeftChild(pos) != null) count++;
            if(getRightChild(pos) != null) count++;
            return count;
        }

        /** Returns an iterable collection of positions representing p's children */
        public Iterable<Position<T>> children(Position<T> pos) {
            List<Position<T>> snapshot = new ArrayList<>(2);
            if(getLeftChild(pos) != null) snapshot.add(getLeftChild(pos));
            if(getRightChild(pos) != null) snapshot.add(getRightChild(pos));
            return snapshot;
        }


        /**
         *  overiding positions method to make inorder
         * default traversal scheme for proper binary trees
         * 
          *  in order traversal is specific to proper binary trees
         */

        public void inorderSubtree(Position<T> pos, List<Position<T>> snapshot){
            if(getLeftChild(pos) != null) 
            inorderSubtree(getLeftChild(pos), snapshot);
            snapshot.add(pos);
            if(getRightChild(pos) != null) 
                inorderSubtree(getRightChild(pos), snapshot);
        }
        
        private Iterable<Position<T>> inorder(){
            List<Position<T>> snapshot = new ArrayList<>();
            if(!isEmpty()){
                inorderSubtree(getRoot(), snapshot);
            }
            return snapshot;
        }

        public Iterable<Position<T>> positons() {
            return inorder();
        }

        /**
         * Euler tour adaptation for a binary tree
         */

        private void eulerTour(Position<T> pos){
            //perform invisit action
        }
        

    }// -- end of abstract binary tree --

    public static class LinkedBinaryTree<T> extends AbsteractBinaryTree<T> {
        private static class Node<T> implements Position<T>{
            private Node<T> parent;
            private Node<T> leftChild;
            private Node<T> rightChild;
            private T ele;

            private int x = 0;
            private int y = 0;

            public Node (T ele, Node<T> parent, Node<T> leftChild, Node<T> rightChild) {
                this.ele = ele;
                this.parent = parent;
                this.leftChild = leftChild;
                this.rightChild = rightChild;
            }

            /** accessor */
            public T getEle() {
            
                return ele;
            }
            public Node<T> getParent(){
                return parent;
            }
            public Node<T> getLeftChild(){
                return leftChild;
            }
            public Node<T> getRightChild(){
                return rightChild;
            }
            
            public int getX() 
            { 
                return x;
            }
            public int getY() 
            { 
                return y;
            }
            /** Update */
            public void setEle(T ele){
                this.ele = ele;
            }
            public void setParent(Node<T> parent){
                this.parent = parent;
            }
            public void setLeftChild(Node<T> left){
                leftChild = left;

            }
            public void  setRightChild(Node<T> right){
                rightChild = right;
            }

            public void setX(int x){
                this.x = x;
            }
            public void setY(int y){
                this.y = y;
            }
        }
     //-- end of Node internal class--

    
    //factory design pattern
    protected Node<T> createNode(T ele, Node<T> parent, Node<T> leftChild, Node<T> rightChild) {
        return new Node<>(ele, parent, leftChild, rightChild);
    }
    
    //fields
    protected Node<T> root = null;
    private int size = 0;
    private final int dscale= 80;

    // //default constructor
    // public LinkedBinaryTree() {}

    protected Node<T> validateToNode(Position<T> pos) 
        throws IllegalStateException {
        if(!(pos instanceof Node)) 
            throw new IllegalArgumentException ("doesn t belong to this tree");

        Node<T> node = (Node<T>) pos;

        if(node.getParent() == node) 
            throw new IllegalArgumentException("pos is already removed from the tree");
        return node;
    }

    public int size() { return size; }
    public Node<T> getRoot() { return root; }

    public Position<T> getParent(Position<T> pos) 
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getParent();
    }

    public Position<T> getLeftChild(Position<T> pos) 
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getLeftChild();
    }

    public Position<T> getRightChild(Position<T> pos) 
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getRightChild();
    }

    public void setX(Position<T> pos, int x) 
        throws IllegalArgumentException {
        Node<T> node = validateToNode(pos);
        node.setX(x);
    }
    
    public void setY(Position<T> pos, int y)
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        node.setY(y);
    }

    public int getX(Position<T> pos) 
        throws IllegalArgumentException {
        Node<T> node = validateToNode(pos);
        return node.getX();
    }
    
    public int getY(Position<T> pos)
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getY();
    }

    public Position<T> addRoot(T ele)
        throws IllegalStateException{
        if(!isEmpty()) throw new IllegalStateException("tree is not empty");
        root = createNode(ele, null, null, null);
        size = 1;
        return root;
    }

    public Position<T> addLeftChild(Position<T> pos, T ele)
        throws IllegalStateException {
        Node<T> parent = validateToNode(pos);

        if(parent.getLeftChild() != null)
            throw new IllegalStateException("already has left child");
        
        Node<T> child = createNode(ele, parent, null, null);
        parent.setLeftChild(child);
        size++;
        return child;
    }
    
    public Position<T> addRightChild(Position<T> pos, T ele)
        throws IllegalStateException {
        Node<T> parent = validateToNode(pos);

        if(parent.getRightChild() != null)
            throw new IllegalStateException("already has right child");
        
        Node<T> child = createNode(ele, parent, null, null);
        parent.setRightChild(child);
        size++;
        return child;
    }

    public T replaceEle(Position<T> pos, T ele)
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        T temp = node.getEle();
        node.setEle(ele);
        return temp;
    }

    //** Attaches trees t1 & t2 as left and right subtrees of external p */
    public void attach(Position<T> pos, LinkedBinaryTree<T> leftSubTree, LinkedBinaryTree<T> rightSubTree)
        throws IllegalArgumentException{
        Node<T> parent = validateToNode(pos);

        if(isInternal(pos)) 
            throw new IllegalArgumentException("pos must be external");

        size += leftSubTree.size() + rightSubTree.size();

        if(!leftSubTree.isEmpty()) {
            parent.setLeftChild(leftSubTree.getRoot());
            leftSubTree.getRoot().setParent(parent);
            leftSubTree.root = null;
            leftSubTree.size = 0;
        }

        if(!rightSubTree.isEmpty()) {
            parent.setRightChild(rightSubTree.getRoot());
            rightSubTree.getRoot().setParent(parent);
            rightSubTree.root = null;
            rightSubTree.size = 0;
        }
    }

    public T remove (Position<T> pos)
        throws IllegalArgumentException {
        Node<T> node = validateToNode(pos);
        if(numChildren(pos) == 2){
            throw new IllegalArgumentException("pos has two childreb");

        }
        Node<T> child = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();
        if(child != null){
            child.setParent(node.getParent());
        }
        if(node == root){
            root = child;
        } else {
            Node<T> parent = node.getParent();
            if(node == parent.getLeftChild()){
                parent.setLeftChild(child);

            } else {
                parent.setRightChild(child);
            }
        }

        T temp = node.getEle();
        node.setEle(null);
        node.setParent(node);
        node.setLeftChild(null);
        node.setRightChild(null);
        size--;
        return temp;
    }



        // -- start of drawing functionality for binary trees

        /**
         * sets x & y coordinates of proper binary tree based on 
         * an inorder traversal of the nodes
         * x(p) : number of positions visited before p
         * y(p) : depth of p
         */
        private int calcCoordinates(Position<T> pos, int depth, int x){
            Position<T> child = null;
            if ((child = getLeftChild(pos)) != null) {
               x = calcCoordinates(child, depth+1, x);
            }

            setX(pos, (x * dscale));
            x++;
            setY(pos, depth*dscale);

            if((child = getRightChild(pos)) != null){
                x = calcCoordinates(child, depth+1, x);
            }

            return x;
        }

        private void makeEdgeList(Position<T> pos, List<Position<T>[]> edgeList, Position<T>[] edge) {
            // uses postorder traversal
            edge[1] = pos;
            for(Position<T> c : children(pos)){
                makeEdgeList(c, edgeList, edge);
                edge[0] = pos;

                edgeList.add(edge.clone());
            }
            edge[1] = pos;
        }

        private Iterable<Position<T>[]> makeEdgeListUtil() {
            List<Position<T>[]> edgeList = new ArrayList<>();
            Position<T>[] edge = (Position<T>[]) new Position[2];
            makeEdgeList(getRoot(), edgeList, edge);
            return edgeList;
        }



}// -- end of linkedbinarytree --

public static class drawTree<T> extends JPanel{
private static final int NODE_RADIUS = 20; // Circle radius

Tree.LinkedBinaryTree<T> tree;
private Iterable<Position<T>> nodes;
private Iterable<Position<T>[]> edges; // each edge as {parentIndex, childIndex}

    public drawTree(Tree.LinkedBinaryTree<T> tree) {
        this.tree = tree;
        this.nodes = tree.positions();
        this.edges = tree.makeEdgeListUtil();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
    }

protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2)); // thicker lines

        // Draw edges (lines between nodes)
        int count = 1;
       
        for (Position<T>[] edge : edges) {
            System.out.println("pair number: " + count++);
            System.out.print("x" + tree.getX(edge[0]));
            System.out.print(" ,y" + tree.getY(edge[0]));
            System.out.print("  ");
            System.out.print("x" + tree.getX(edge[1]));
            System.out.print(" ,y" + tree.getY(edge[1]) + "  ||");
            System.out.print("par  " + edge[0].getEle());
            System.out.print("child  " + edge[1].getEle());

            System.out.println();
        }

        for (Position<T>[] edge : edges) {
            Position<T> parent = edge[0];
            Position<T> child = edge[1];
            g2.drawLine(tree.getX(parent), tree.getY(parent), tree.getX(child), tree.getY(child));
        }

        // Draw nodes (circles with labels)

        for (Position<T> pos : nodes) {
            int r = NODE_RADIUS;
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(tree.getX(pos) - r, tree.getY(pos) - r, 2 * r, 2 * r); // Circle
            g2.setColor(Color.BLACK);
            g2.drawOval(tree.getX(pos) - r, tree.getY(pos) - r, 2 * r, 2 * r);

            // Draw label centered
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(pos.getEle() + "");
            int textHeight = fm.getAscent();
            g2.drawString(pos.getEle() + "", tree.getX(pos) - textWidth / 2, tree.getY(pos) + textHeight / 4);
        }
    }
}

public static void main(){
//    Tree tree = new Tree();
//    Tree.LinkedBinaryTree<Integer> binaryTree = tree.new LinkedBinaryTree<>();
//    Position<Integer> left;
//    Position<Integer> right;
   
//    // initializing tree
//    binaryTree.addRoot(10);
//    left = binaryTree.addLeftChild(binaryTree.getRoot(), 27);
//    right = binaryTree.addRightChild(binaryTree.getRoot(), 28);
//   binaryTree.addLeftChild(left, 28);
//    binaryTree.addRightChild(left, 14);
//    left = binaryTree.addRightChild(right, 18);
//    right = binaryTree.addLeftChild(right, 19);
//    binaryTree.addRightChild(left, 88);
//    binaryTree.addLeftChild(right, 28);
//     binaryTree.addRightChild(right, 14);

//    // ------
   
//    binaryTree.calcCoordinates(binaryTree.getRoot(), 1, 1);

// // Iterable<Position<Integer>[]> edgeList = binaryTree.makeEdgeListUtil();
// //     Iterable<Position<Integer>> nodes = binaryTree.positions();

   
//        JFrame frame = new JFrame("Binary Tree Drawer");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.add(tree.new drawTree<>(binaryTree));
//         frame.pack();
//         frame.setLocationRelativeTo(null);
//         frame.setVisible(true);
}

}//-- end of tree--