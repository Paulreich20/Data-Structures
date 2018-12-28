import java.io.*;
import java.util.*;
/**
 * Creates WordCountMap and contains methods to create WordCloudMap
 * as well as alphabetical and frequency sorting methods
 *
 * @author Joey Cook
 * @author Paul Reich
 * With Help from Andrew Lin
 * Much of this assignment was coded together with Joey
 */
public class WordCounter{
    private File file;
    private WordCountMap BST = new WordCountMap();
    /**
     * loads in file
     *
     * @param fileName is the filenpath that is passed through the commandline
     */
    public void load(String fileName) {
        // arraylists to store stopwords and lines from the chosen file
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> stopArray = new ArrayList<>();
        String line = null;
        File wordsofstopping = new File("StopWords.txt");
        Scanner scanner = null;

        try {
            FileReader filereader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(filereader);
            // process of reading in the chosen file
            while ((line = bufferedReader.readLine()) != null) {
                // in every line of text makes everything lowercase and deletes any non alphabetical characters
                line = line.toLowerCase();
                line = line.replaceAll("[^a-zA-Z]", " ");
                lines.add(line);                        //scanner returns boolean,
            }


            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            scanner = new Scanner(wordsofstopping);
            while (scanner.hasNextLine()) {
                //takes in the words contained within StopWords.txt
                String stopWords1 = scanner.nextLine();
                stopArray.add(stopWords1);
            }

        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        for (int i = 0; i < lines.size(); i++) {
            //partitions words and doesn't add words that are found in StopWords.txt
            String[] splitWords = lines.get(i).split("\\s+"); //partitions the lines
            for (int j = 0; j < splitWords.length; j++) {
                if (stopArray.contains(splitWords[j])|| splitWords[j].equals(""))  {
                }
                else {
                    BST.incrementCount(splitWords[j]);
                }
            }
        }
    }

    /**
     * * prints out an alphabetized list of the words in the given text file with their counts
     */
    public void alphabetical(){
        //creates an array of words ordered alphabetically
        ArrayList<WordCount> arr = BST.getWordCountsByWord();
        for(int i = 0; i < arr.size(); i++) {
            //prints out the array in order
            System.out.println(arr.get(i).word + ": " + arr.get(i).count);
        }
    }

    /**
     * prints out a descending count organized list of the words in the given text file with their counts
     */
    public void frequency() {
        //creates an array of words ordered by frequency
        ArrayList<WordCount> arr = BST.getWordCountsByCount();
        for(int i = 0; i < arr.size(); i++) {
            //prints out the array
            System.out.println(arr.get(i).word + ": " + arr.get(i).count);
        }
    }
    /**
     * returns array of words by count
     *
     * @return arr
     */
    public ArrayList<WordCount> cloudFrequency() {
        ArrayList<WordCount> arr = BST.getWordCountsByCount();
        return arr;
    }
    /**
     * creates a WordCloupMap based off of most frequently used words in given text file
     *
     * @param size  # of words input by the command line
     * @param sortedList the sortedArrayList
     * @param name the name of the chosen file
     * @param htmlName  htmlname the htmlname of the document
     */
    public void cloud(int size,ArrayList<WordCount> sortedList,String name,String htmlName){
        ArrayList<WordCount> sizedList = new ArrayList<WordCount>();
        for(int i = 0;i<size;i++){
            sizedList.add(sortedList.get(i));
        }
        WordCloudMaker cloud = new WordCloudMaker();
        cloud.createWordCloudHTML(name,sizedList,htmlName);
    }
    /**
     *  creates WordCountMap and then runs frequency, alphabetical, or cloud methods based off of commandline input
     */
    public static void main(String[] args) {
        WordCounter counter = new WordCounter();
        counter.load(args[1]);
        //loads the words of a given textfile into an array
        if (args[0].equals("frequency")) {
            counter.frequency();
        }

        else if(args[0].equals("alphabetical")){
            counter.alphabetical();
        }
        else if(args[0].equals("cloud")) {
            // checks for index value of "." in args[1]
            int endIndex = args[1].indexOf(".");
            // retrieves name of args[1]

            String Name = args[1].substring(0, endIndex);


            // creates html name
            String htmlName = Name + ".html";


            // calls Cloud on counter
            counter.cloud(Integer.parseInt(args[2]), counter.cloudFrequency(),Name, htmlName);

        }
    }
}
