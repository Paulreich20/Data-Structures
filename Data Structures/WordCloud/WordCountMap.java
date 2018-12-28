import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;
import java.io.*;
/**
 * A class that includes a BST and a methods that can visit that BST in a certain order
 * and store that traversal in an array.
 * @author Eric Alexander
 * @author Joey Cook
 * @author Paul Reich
 */
public class WordCountMap {
    // root of BST
    private BSTNode root;
    private class BSTNode {
        // BSTNode's word, count as well as its left and right nodes
        private String word;
        private int count;
        private BSTNode left;
        private BSTNode right;
        /**
         * gives access to a BSTNode's word and or count
         * @param word the BSTNode's word
         * @param count the BSTNode's count
         */
        public BSTNode(String word, int count) {
            this.word = word;
            this.count = count;
            left = null;
            right = null;
        }
        /**
         * method to convert node to string
         */
        public String toString() {
            return String.format("{%s -> %s}", word, count);
        }
    }
    /**
     * sets WordCountMap's root to null
     */
    public WordCountMap() {
        root = null;
    }

    /**
     * Insert given word-count pair.
     * If word is already in the tree, replace its count.
     *
     * @param word the word to be inserted
     * @param count the count to be associated with word
     */
    public void insert(String word , int count) {
        root = insert(root, word, count);
    }

    /**
     * Helper method to insert()
     * Inserts given word-count pair to tree
     * rooted at given node.
     *
     * @param n root of subtree to be inserted into
     * @param word the word to be inserted
     * @param count count to be associated with word
     * @return the new root of the given subtree
     */
    private BSTNode insert(BSTNode n, String word, int count) {
        if (n == null) {
            return new BSTNode(word, count);
        } else if (word.compareTo(n.word) < 0) {
            n.left = insert(n.left, word, count);
            return n;
        } else if (word.compareTo(n.word) > 0) {
            n.right = insert(n.right, word, count);
            return n;
        } else {
            n.count = count;
            return n;
        }
    }

    /**
     * Retrieve the count associated with given word
     * (or null if it isn't present).
     *
     * @param word the word to be looked up
     * @return the count associated with word
     */
    public int lookup(String word) {
        return lookup(root, word);
    }

    /**
     * Helper method to lookup().
     * Looks up word within subtree rooted at given node.
     *
     * @param n the root of the subtree to be searched
     * @param word the word to be looked up
     * @return the count associated with word
     */
    private int lookup(BSTNode n, String word) {
        if (n == null) {
            return 0;
        } else if (word.compareTo(n.word) < 0) {
            return lookup(n.left, word);
        } else if (word.compareTo(n.word) > 0) {
            return lookup(n.right, word);
        } else {
            return n.count;
        }
    }



    /**
     * Helper method for delete().
     * Returns node in given subtree with smallest word.
     *
     * @param n the root of the subtree
     * @return the node with the smallest word
     */
    private BSTNode smallest(BSTNode n) {
        if (n.left == null) {
            return n;
        } else {
            return smallest(n.left);
        }
    }





    /**
     * If the specified word is already in this WordCountMap, then its
     * count is increased by one. Otherwise, the word is added to this map
     * with a count of 1.
     *
     * @param n the given node
     * @param word the given word that will be either inserted or have its count incremented
     */
    public void incrementCount(BSTNode n, String word) {
        // if word is in tree find word in tree
        if (lookup(word) != 0 ) {
            while (n != null) {
                // if word is present increment its count
                if (n.word.compareTo(word) == 0) {
                    n.count++;
                    return;
                    // search left subtree
                } else if (n.word.compareTo(word) > 0) {
                    n = n.left;
                    // search right subtree
                } else if (n.word.compareTo(word) < 0) {
                    n = n.right;
                }
            }
        }
        // if the word is not in the tree, insert it in the tree and give it a count of 1
        else {
            insert(word, 1);
        }
    }
    /**
     * If the specified word is already in this WordCountMap, then its
     * count is increased by one. Otherwise, the word is added to this map
     * with a count of 1.
     *
     * @param word the given word that will be either inserted or have its count incremented
     */
    public void incrementCount(String word) {
        incrementCount(root, word);
    }

    /**
     * Returns an ArrayList of WordCount objects sorted by the frequency they appear in a text file
     * WordCountMap, sorted in decreasing order by count.
     * @return An array of WordCount objects
     */
    public ArrayList<WordCount> getWordCountsByCount() {
        ArrayList<WordCount> arr= getWordCountsByWord();
        Collections.sort(arr);
        return arr;
    }

    /**
     * Returns a list of WordCount objects myWordCountArray, one per word stored in this
     * WordCountMap, sorted alphabetically by word.
     * @return returns an ArrayList of WordCount objects
     */
    public ArrayList<WordCount> getWordCountsByWord() {
        ArrayList<WordCount> myWordCountArray = new ArrayList<WordCount>();
        alphabettraversal(root, myWordCountArray);
        return myWordCountArray;
    }
    /**
     * method to traverse BST and add WordCount objects to the ArrayList
     *
     * @param n a given BSTNode
     * @param myWordCountArray an array of wordcount objects
     */
    public void alphabettraversal(BSTNode n, ArrayList<WordCount> myWordCountArray){
        // if both children are null add new WordCount
        if(n.left == null && n.right == null){
            myWordCountArray.add(new WordCount(n.word, n.count));
            return;
        } else {
            // if right child is null, call alphabettraversal on n.left and add new WordCount
            if(n.right == null){ // only left child
                alphabettraversal(n.left, myWordCountArray);
                myWordCountArray.add(new WordCount(n.word, n.count));
                return;
            }
            // if left child is null, call alphabettraversal on n.right and add new WordCount
            if(n.left == null){ // only right child
                myWordCountArray.add(new WordCount(n.word, n.count));
                alphabettraversal(n.right, myWordCountArray);
                return;
            }
            // if has no children, call alphabettraversal on n.left, then add word count, then perform alphabettraversal on n.right
            else {
                alphabettraversal(n.left, myWordCountArray);
                myWordCountArray.add(new WordCount(n.word, n.count));
                alphabettraversal(n.right, myWordCountArray);
                return;
            }
        }
    }
}
