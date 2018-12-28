import java.io.*;
import java.util.*;

/**
 * Maze.java
 * 
 * @author Paul Reich and John Mullan
 * modified from an existing file written by Eric Alexander
 * 
 * Generates and solves a maze from a given text file.
 */
public class Maze {
    private ArrayList<ArrayList<MazeSquare>> rowList;
    private int w, h;
    private int startRow, startCol, endRow, endCol;

    /**
     * This class represents an individual square in the maze.
     */ 
    private class MazeSquare {
        private int r, c;
        private boolean top, bottom, left, right,
                start, end, visited;
 
        /**
         * This is the constructor for the MazeSquare object.
         *
         * @param r the row that the MazeSquare occupies
         * @param c the column that the MazeSquare occupies
         * @param top a boolean representing whether or not the MazeSquare has a top boundary
         * @param bottom a boolean representing whether or not the MazeSquare has a bottom boundary
         * @param left a boolean representing whether or not the MazeSquare has a left boundary
         * @param right a boolean representing whether or not the MazeSquare has a right boundary
         * @param start a boolean representing whether or not the MazeSquare is the start square of the maze
         * @param end a boolean representing whether or not the MazeSquare is the end square of the maze
         */
        private MazeSquare(int r, int c,
                           boolean top, boolean bottom, boolean left, boolean right,
                           boolean start, boolean end) {
            this.r = r;
            this.c = c;
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
            this.start = start;
            this.end = end;
            boolean visited = false;
        }
        
        /*
         * a method that returns whether or not a MazeSquare has been visited
         * 
         * @return returns a boolean that expresses whether or not the MazeSquare has been visited
         */
        boolean isVisited() {
            return visited;
        }
        void visit() {
            visited = true;
        }
        boolean hasTopWall() {
            return top;
        }
        boolean hasBottomWall() {
            return bottom;
        }
        boolean hasLeftWall() {
            return left;
        }
        boolean hasRightWall() {
            return right;
        }
        boolean isStart() {
            return start;
        }
        boolean isEnd() {
            return end;
        }
        int getRow() {
            return r;
        }
        int getCol() {
            return c;
        }
    }

    /**
     * Construct a new Maze
     */
    public Maze() {
        rowList = new ArrayList<ArrayList<MazeSquare>>();
    }

    /**
     * Load Maze in from given file
     *
     * @param fileName the name of the file containing the Maze structure
     */
    public void load(String fileName) {

        // Create a scanner for the given file
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        // First line of file is "w h"
        String[] lineParams = scanner.nextLine().split(" ");
        w = Integer.parseInt(lineParams[0]);
        h = Integer.parseInt(lineParams[1]);

        // Second line of file is "startCol startRow"
        lineParams = scanner.nextLine().split(" ");
        startCol = Integer.parseInt(lineParams[0]);
        startRow = Integer.parseInt(lineParams[1]);

        // Third line of file is "endCol endRow"
        lineParams = scanner.nextLine().split(" ");
        endCol = Integer.parseInt(lineParams[0]);
        endRow = Integer.parseInt(lineParams[1]);

        // Read the rest of the lines (L or | or _ or -)
        String line;
        int rowNum = 0;
        boolean top, bottom, left, right;
        boolean start, end;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            rowList.add(new ArrayList<MazeSquare>());

            // Loop through each cell, creating MazeSquares
            for (int i = 0; i < line.length(); i++) {
                // For top, check row above, if there is one
                if (rowNum > 0) {
                    top = rowList.get(rowNum-1).get(i).hasBottomWall();
                } else {
                    top = true;
                }

                // For right, check cell to the right, if there is one
                if (i < line.length() - 1 ) {
                    char nextCell = line.charAt(i+1);
                    if (nextCell == 'L' || nextCell == '|') {
                        right = true;
                    } else {
                        right = false;
                    }
                } else {
                    right = true;
                }

                // For left and bottom, switch on the current character
                switch (line.charAt(i)) {
                    case 'L':
                        left = true;
                        bottom = true;
                        break;
                    case '_':
                        left = false;
                        bottom = true;
                        break;
                    case '|':
                        left = true;
                        bottom = false;
                        break;
                    case '-':
                        left = false;
                        bottom = false;
                        break;
                    default:
                        left = false;
                        bottom = false;
                }

                // Check to see if this is the start or end spot
                start = startCol == i && startRow == rowNum;
                end = endCol == i && endRow == rowNum;

                // Add a new MazeSquare
                rowList.get(rowNum).add(new MazeSquare(rowNum, i, top, bottom, left, right, start, end));
            }

            rowNum++;
        }
    }
    
    /**
     * Returns the MazeSquare object that is oriented in a particular direction with respect to the specified MazeSquare
     *
     * @param s the MazeSquare that will be used for reference when determining neighbors
     * @param direction the direction relative to s that the neighbor will be found
     * @return the specified neighbor of s as a MazeSquare object, as determined by the direction relative to s
     */
    
    public MazeSquare getNeighbor(MazeSquare s, String direction) {
        int rownumber = s.getRow();
        int colnumber = s.getCol();
        
        if(direction == "left"){
            return rowList.get(rownumber).get(colnumber - 1);
        }

        
       if(direction == "right"){
            return rowList.get(rownumber).get(colnumber + 1);
            }
        

        if(direction == "top"){
            return rowList.get(rownumber - 1).get(colnumber);
        }

            

        if(direction == "bottom"){
            return rowList.get(rownumber + 1).get(colnumber);
        }

        else {
            return null;
        }
                    
            
    }
    /**
     * Returns a solution to the maze, if one exists, in the form of an LLStack of MazeSquare objects
     * 
     * @return a solution to the maze, if one exits, in the form of an LLStack of MazeSquare objects
     */
    public LLStack<MazeSquare> getSolution()  {
        LLStack<MazeSquare> solutionStack = new LLStack<MazeSquare>(); //represents the current part of a working solution
        MazeSquare startSquare = rowList.get(startRow).get(startCol);
        solutionStack.push(startSquare);
        startSquare.visited = true;
        while(solutionStack.isEmpty() == false ) {
            MazeSquare t = solutionStack.peek(); //initializes an instance of the MazeSquare class that acts as the current MazeSquare that is being examined as a potential part of the solution
            t.visit();
            if(t.isEnd() == true) {
                return solutionStack;
            }
            else if(t.hasLeftWall() == false && getNeighbor(t, "left").isVisited() == false){
                solutionStack.push(getNeighbor(t, "left"));
            }else if(t.hasRightWall() == false && getNeighbor(t, "right").isVisited() == false){
                solutionStack.push(getNeighbor(t, "right"));
            }else if(t.hasTopWall() == false && getNeighbor(t, "top").isVisited() == false){
                solutionStack.push(getNeighbor(t, "top"));
            }else if(t.hasBottomWall() == false && getNeighbor(t, "bottom").isVisited() == false){
                solutionStack.push(getNeighbor(t, "bottom"));
            }else {
                solutionStack.pop();
            }  
        } 
        if(solutionStack.size( )== 0) {
            return null;
        }
        return null;
    }
             
    /**
     * Print the Maze to the Console
     */
    public void print() {
	
	// YOUR CODE WILL GO HERE:
	// Before printing, use your getSolution() method
	//  to get the solution to the Maze.
	
	    LLStack stack = getSolution();
	    if(stack == null) {
	        System.out.println("There is no solution to this maze");
            
	    }
	        
        ArrayList<MazeSquare> currRow;
        MazeSquare currSquare;

        // Print each row of text based on top and left
        for (int r = 0; r < rowList.size(); r++) {
            currRow = rowList.get(r);

            // First line of text: top wall
            for (int c = 0; c < currRow.size(); c++) {
                System.out.print("+");
                if (currRow.get(c).hasTopWall()) {
                    System.out.print("-----");
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println("+");

            // Second line of text: left wall then space
            for (int c = 0; c < currRow.size(); c++) {
                if (currRow.get(c).hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("     ");
            }
            System.out.println("|");

            // Third line of text: left wall, then space, then start/end/sol, then space
            for (int c = 0; c < currRow.size(); c++) {
                currSquare = currRow.get(c);

                if (currSquare.hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }

                System.out.print("  ");

                if (currSquare.isStart() && currSquare.isEnd()) {
                    System.out.print("SE ");
                } else if (currSquare.isStart() && !currSquare.isEnd()) {
                    System.out.print("S  ");
                } else if (!currSquare.isStart() && currSquare.isEnd()) {
                    System.out.print("E  ");
		        // If currSquare is part of the solution, marked with *
                } else if(stack != null && stack.contains(currSquare)){
		            System.out.print("*  "); 
		        }
                else {
                    System.out.print("   ");
                }
            }
            System.out.println("|");

            // Fourth line of text: same as second
            for (int c = 0; c < currRow.size(); c++) {
                if (currRow.get(c).hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("     ");
            }
            System.out.println("|");
        }

        // Print last row of text as straight wall
        for (int c = 0; c < rowList.get(0).size(); c++) {
            System.out.print("+-----");
        }
        System.out.println("+");
    }

    // This main program acts as a simple unit test for the
    // load-from-file and print-to-System.out Maze capabilities.
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Maze mazeFile");
            System.exit(1);
        }
        Maze maze = new Maze();
        maze.load(args[0]);
        maze.print();
    }
}
