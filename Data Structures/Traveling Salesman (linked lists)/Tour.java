import java.util.*;
/**
 * Tour.java
 * This class creates a Tour of Points using a 
 * Linked List implementation.  The points can
 * be inserted into the list using two heuristics.
 *
 * @author Paul Reich and John Mullan
 * With Help from Andrew Lin
 * @author Eric Alexander, modified code 01-12-2018
 * @author Layla Oesper, modified code 09-22-2017
 */

public class Tour {
    /**
     * A helper class that defines a single node for use in a tour.
     * A node consists of a Point, representing the location of that
     * city in the tour, and a pointer to the next Node in the tour.
     */
    private class Node {
        private Point p;
        private Node next;


        /**
         * Constructor creates a new Node at the given Point newP
         * with an initial next value of null.
         *
         * @param newP the point to associate with the Node.
         */
        public Node(Point newP) {
            p = newP;
            next = null;
        }

        /**
         * Constructor creates a new Node at the given Point newP
         * with the specified next node.
         *
         * @param newP the point to associate with the Node.
         * @param nextNode the nextNode this node should point to.
         */
        public Node(Point newP, Node nextNode) {
            p = newP;
            next = nextNode;
        }

    } // End Node class


    // Tour class Instance variables
    private Node head;
    private int size; //number of nodes

    /**
     * Constructor for the Tour class.  By default sets head to null.
     */
    public Tour() {
        head = null;
        size = 0;
    }
    /**
     * Returns a string with information about the tour, namely the coordinates of each point
     * 
     * @return a string of all the points in the tour, expressed in coordinates
     */
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        Node currNode = head;
        //Iterate through all the nodes and add the string expression of their coordinates to sj
        while (currNode != null) {
            sj.add(currNode.p.toString());
            currNode = currNode.next;
        }
        return sj.toString();
    }
    
    /**
     * Draws a graphical representation of the tour using the StdDraw class
     *
     */
    public void draw() {
        Node temp = head;
        while (temp.next != null) {
            temp.p.drawTo(temp.next.p);
            temp = temp.next;
        }
        if (temp.next == null) {
            temp.p.drawTo(head.p);
        }
    }
    
    /**
     * Returns the number of points in the tour
     *
     * @return an integer representing the number of points in the tour
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns the distance of the entire tour
     *
     * @return a double representing the distance of the entire tour
     */
    public double distance() {
        Node temp = head;
        double totalDistance = 0.0;
        //the case where no points exist in the tour
        if (temp.next == null){
            return totalDistance;
        }
        //increments the totalDistance by the distance between the current temporary point and the point immediately following it
        while (temp.next != null) {
            totalDistance += temp.p.distanceTo(temp.next.p);
            temp = temp.next;
        }
        //the case where the end of the LinkedList has been reached, which requires that the distance between the final and first point be added to the current totalDistance
        if(temp.next == null) {
            totalDistance += temp.p.distanceTo(head.p);
        }
        return totalDistance;
    }
    
    /**
     * Adds a point to a particular position in the LinkedList
     *
     * @param pos an integer denoting the position in the LinkedList
     * @param p the Point to be added to the LinkedList
     */
    public void add(int pos, Point p){
        Node temp = head;
        for(int i = 0; i <= pos -1;i++){
            temp = temp.next;
        }
        temp.next = new Node(p, temp.next);
    }
    
    /**
     * Adds the given point to the LinkedList using the insertNearest heuristic
     *
     * @param p the Point to be added to the LinkedList using the insertNearest heuristic
     */
    public void insertNearest(Point p) {
        Node chosenNode = new Node(p); //create a Node called chosenNode given the parameter p (this is the point to be added to the LinkedList)
        int ticker = 0; //represents the insertion point that presently best follows the insertNearest heuristic for the given p
        //the case that the LinkedList has no points already added
        if (head == null) {
            head = chosenNode;
            size=1;
        }
        else {
            Node currNode = head; //a Node called currNode that is considered with respect to the chosenNode as the nearest Node for insertion
            double nearestDistance = Double.MAX_VALUE;
            int position = 0; //represents the insertion point that is presently being considered for the given p
            while (currNode.next != null) {
                double distance = currNode.p.distanceTo(chosenNode.p);
                //the case where the distance between the currNode's point and the chosenNode's point is closer than the previously recorded nearestDistance
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    ticker = position;
                }
                currNode = currNode.next;
                position++;
            }
            size++;
        }
        add(ticker, p);
    }

    /**
     * Adds the given point p to the LinkedList using the insertSmallest heuristic
     * 
     * @param p the Point to be added to the LinkedList using the insertSmallest heuristic
     */
    public void insertSmallest(Point p) {
        Node chosenNode = new Node(p); //create a Node given the parameter p to be added to the LinkedList
        Node currNode = head; //create a node that is considered with respect to the chosenNode to make a determination if adding the chosenNode at a particular insertion point follows the insertSmallest heuristic
        int ticker = 0; //the insertion point that presently best represents the insertSmallest heuristic for the given Point p
        //the case that the LinkedList has no points
        if (head == null) {
            head = chosenNode;
            size = 1;
        }   else {
            int position = 0; //represents the insertion point that is presently being considered for the given p
            double minAddedDistance = Double.MAX_VALUE;
            while (currNode.next != null) {
                double distance1 = chosenNode.p.distanceTo(currNode.p); //the distance between the chosenNode's point and the currentNode's point 
                double distance2 = chosenNode.p.distanceTo(currNode.next.p); //the distance between the chosenNode's point and the point of the node presently following the currentNode
                double originalDistance = currNode.p.distanceTo(currNode.next.p); //the distance between the currentNode's point and the point of the node following the currentNode
                double addedDistance = distance1 + distance2 - originalDistance; //the distance added to the total tour if the chosenNode's point was to be inserted between the currentNode's point and the point of the node that follows the currentNode
                //the case where the presently considered addedDistance is less than the previously recorded minAddedDistance
                if (addedDistance < minAddedDistance) {
                    minAddedDistance = addedDistance;
                    ticker = position;
                }
                currNode = currNode.next;
                position++;
            }
            size++;
        }
        add(ticker, p);
    }

    /**
     * The main method as per convention in the writing of Java classes. In the Tour class, this method serves only as a test method for developers
     * 
     * @param args an array of Strings as is custom for the main method of a Java class
     */
    public static void main(String[] args) {
    }
}