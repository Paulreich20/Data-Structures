/**
 * Creates WordCountMap and contains methods to create WordCloudMap
 * aswell as alphabetical and frequency sorting methods
 *
 * @author Joey Cook
 * @author Paul Reich
 * With Help from Andrew Lin
 * Much of this assignment was coded together with Joey
 */

/**
 * WordCount is an object that is useful in storing the information based in the ADT within an array.
 */
public class WordCount implements Comparable<WordCount> {
    public String word;
    public int count;
    /**
     * @param other the other WordCount object to be compared to
     * @return returns the difference between the counts of the two objects
     */
    public int compareTo(WordCount other){
        return other.count - count;
        //compareTo method returns the difference of two counts
    }
    public WordCount(String word, int count){
        this.word = word;
        this.count = count;
    }
    public String toString(){
        String z = word+": "+count;
        return z;
    }
}