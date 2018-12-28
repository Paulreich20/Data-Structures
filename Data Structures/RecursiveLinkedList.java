import java.util.*;
import java.util.concurrent.RecursiveAction;

/**
 * An implementation of the List ADT using
 * a linked list.  Specifically, this implementation
 * only allows a List to contain Comparable items.
 *
 * @author Layla Oesper
 * @author Eric Alexander
 * @author Paul Reich
 */

/* Note <E extends Comparable<E> means this container
 * can only old objects of type E that are Comparable.
 */
public class RecursiveLinkedList<E extends Comparable<E>>{

    /* Internal Node class used for creating linked objects.
     */
    private class Node<E> {
        private E data;
        private Node<E> next;

        private Node(E dataItem) {
            data = dataItem;
            next = null;
        }

        private Node(E dataItem, Node<E> nextNode) {
            data = dataItem;
            next = nextNode;
        }

    } // End Node class

    //Instance variables for RecursiveLinkedList
    private Node<E> head;
    private int numItems;

    /**
     * Creates an empty RecursiveLinkedList
     */
    public RecursiveLinkedList() {
        head = null;
        numItems = 0;
    }

    /**
     * Returns the data stored at positon index.
     * @param index
     * @return The data stored at position index.
     */
    public E get(int index) {
        if (index < 0 || index >= numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    /*
     * Helper method that retrieves the Node<E> stored at
     * the specified index.
     */
    private Node<E> getNode(int index) {
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * Removes and returns the data stored at the specified index.
     * @param index The position of the data to remove.
     * @return The data previously stored at index position.
     */
    public E remove(int index) {
        if (index < 0 || index >= numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        if (index == 0){
            return removeFirst();
        } else {
            Node<E> before = getNode(index - 1);
            return removeAfter(before);
        }
    }

    /*
     * Helper method that removes the Node<E> after the
     * specified Node<E>. Returns the data that was
     * stored in the removed node.
     */
    private E removeAfter(Node<E> node) {
        Node<E> temp = node.next;
        if (temp != null) {
            node.next = temp.next;
            numItems--;
            return temp.data;
        } else {
            return null;
        }
    }

    /*
     * Helper method that removes the first Node<E> in
     * the Linked List.  Returns the data that was
     * stored in the removed node.
     */
    private E removeFirst() {
        Node<E> temp = head;
        if (head != null) {
            head = head.next;
        }

        if (temp != null) {
            numItems--;
            return temp.data;
        } else {
            return null;
        }
    }

    /**
     * Adds the data to the list at the specified index.
     * @param index The position to add the data.
     * @param anEntry The particular data to add to the list.
     */
    public void add(int index, E anEntry) {
        if (index < 0 || index > numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(anEntry);
        } else {
            Node<E> node = getNode(index - 1);
            addAfter(node, anEntry);
        }
    }

    /*
     * Helper method that adds anEntry to the first
     * position in the list.
     */
    private void addFirst(E anEntry) {
        head = new Node<>(anEntry, head);
        numItems++;
    }

    /*
     * Helper method that adds anEntry after the
     * specified Node<E> in the linked list.
     */
    private void addAfter(Node<E> before, E anEntry) {
        before.next = new Node<>(anEntry, before.next);
        numItems++;
    }

    /**
     * Add the specified data to the end of the list.
     * @param anEntry The data to add to this list.
     */
    public boolean add(E anEntry) {
        add(numItems, anEntry);
        return true;
    }

    /**
     * Returns the size of the list in terms of items stored.
     * @returns the number of items in the list.
     */
    public int size() {
        return numItems;
    }

    /**
     * Modifies the list so the specified index now
     * contains newValue (overwriting the old data).
     * @param index The position int he list to add data.
     * @param newValue The data to place in the list.
     * @return The previous data value stored at index.
     */
    public E set(int index, E newValue) {
        if (index < 0 || index >= numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        E result = node.data;
        node.data = newValue;
        return result;
    }

    /**
     * A string representation of the List.
     * @returns A string representation of the list.
     */
    public String toString() {
        String s = "[";
        Node<E> temp = head;
        for (int i = 0; i < numItems; i++) {
            s = s + temp.data.toString();
            if (i < numItems - 1) {
                s = s + ", ";
            }
            temp = temp.next;
        }
        s = s + "]";
        return s;
    }

    /**
     * A helper method for the max method that returns the maximum element in a linked list using recursion
     * @param currNode The Node storing the maximum value
     * @param compNode The Node that is being compared against the current maximum Node
     * @return returns the Node containg the element of highest value
     */

    public Node<E> maxcompare(Node<E> currNode, Node<E> compNode ){
        if(compNode == null){
            return currNode; // if the comparison node is null, it is the base case and it returns currNode
        }
        else {
            int comp = currNode.data.compareTo(compNode.data); //variable storing the comparision on the two Nodes
            if( comp <= 0){
                return maxcompare(compNode, compNode.next);
                // if the currNode's value is less than the compNode, so the current compNode is now the max Node
                // so we call maxcompare() again but this time we call maxcompare but use compNode as the current
                // node and compare it to the next node in the list
            }else {
                return maxcompare(currNode, compNode.next);
                //Same idea as before but currNode remains the maximum node, so we compare it to the next node in
                // the linked list.
            }
        }
    }
    /**
     * Return the maximum element in the list using
     * compareTo() method of Comparable.
     *
     * @return maximum element of the list
     **/
    public E max(){
        if( numItems == 0){
            return null;
            // in case the length of the linked list is 0.
        }
        return maxcompare(head, head.next).data;
        // calls the helper method on the start of the linked list and the nex t
    }

    /**
     * Remove all elements that match element using the
     * equals() operator to determine a match.
     * (Don't use ==).
     *
     * @param element The element that should be removed
     **/

    public void removeAll(E element){
        if( numItems == 0){
            return;
            // in case the linked list is empty
        }
        compareRemove(head, element);
        //calls the helper method on the start of the linked list

    }

    /**
     * a helper method for removing all instances of a given element through recursion
     * @param currNode The current Node being checked for the element as well as the next Node
     * @param element The element being removed from the linked list
     */
    public void compareRemove(Node<E> currNode, E element){
        if(currNode.next == null && currNode.data.equals(element)) {
            head = null;
            numItems--;
            return;
            // This if statement stops the exception when head.next == null and head.data.equals(element) is true
        }
        else if(currNode.next == null ) {
            return;
            // the base case for the method when currNode.next is null, when all instances of the element have been deleted
        }
        else {
            if(currNode.data.equals(element)){
                head = currNode.next;
                numItems--;
                compareRemove(head, element);
                // if the head of the linked list is the element we're trying to remove and moves the head to the next node
            }

            else if (currNode.next.data.equals(element)){
                currNode.next = currNode.next.next;
                numItems--;
                compareRemove(currNode, element);
                //looks at the next node in the linked list and removes the Node if it contains the element

            }
            else {
                compareRemove(currNode.next, element);
                //recursively checks the next node in the linked list
            }
        }
    }


    /**
     * Duplicate each element of the list
     *
     * For example, the list [ 0 1 2 ] duplicated becomes
     * [ 0 0 1 1 2 2 ]
     **/
    public void duplicate(){
        if( numItems == 0){
            return;
        }
        duplicate(head);
    }

    /**
     * a helper method for duplicate for duplicating everything in the linked list
     * @param currNode the current Node being duplicated
     */
    public void duplicate(Node<E> currNode){
        if(currNode.next == null) {
            addAfter(currNode,currNode.data);
            return;
            //The base case for when the next Node is equal to null
        }
        else {
            addAfter(currNode,currNode.data);
            duplicate(currNode.next.next);
            //Adds a copy of the currNode after it, and calls it on the next non-copied Node in the linked list
        }
    }

    /**
     * Here are a couple short tests. You should
     * should make sure to thoroughly test your code.
     */
    public static void main(String[] args) {
        RecursiveLinkedList<String> l = new RecursiveLinkedList<String>();
       l.add("Paul");
       l.add("Janis");
       l.add("Thu");
       l.add("Thu");
       l.add("Will");
       System.out.println("The List is :" + l);
       System.out.println(l.max());
       l.removeAll("Thu");
       System.out.println("The List is :" + l);
       System.out.println(l.max());
       l.duplicate();
       System.out.println("The List is :" + l);
    }

}
