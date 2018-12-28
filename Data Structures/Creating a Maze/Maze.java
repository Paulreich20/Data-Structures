import java.util.*;
import java.io.*;

/**
 * Maze.java
 * This class loads and prints a maze from a supplied text file
 *
 * @author Paul Reich & John Mullan
 * Note: help and advice was given in the writing of this class from Elliot Pickens
 */
public class Maze {
    private ArrayList<ArrayList<MazeSquare>> rowList;
    private ArrayList<MazeSquare> row;
    private int w;
    private int h;
    private int startX;
    private int startY;
    private int finishX;
    private int finishY;
    private File file;

    /**
     * Constructor for the Maze class
     */
    public Maze() {
        rowList = new ArrayList<ArrayList<MazeSquare>>();
    }

    /**
     * Load in a Maze from a given file
     *
     * @param fileName the name of the text file containing the maze
     */
   public void load(String fileName) {
        // Create a scanner to extract information about the maze from fileName
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
        // Second line of file is start coordinate
        String[] startCoordinate = scanner.nextLine().split(" ");
        startX = Integer.parseInt(startCoordinate[0]);
        startY = Integer.parseInt(startCoordinate[1]);
        // Third line of file is end coordinate
        String[] endCoordinate = scanner.nextLine().split(" ");
        finishX = Integer.parseInt(endCoordinate[0]);
        finishY = Integer.parseInt(endCoordinate[1]);
        
        //Iterates through each line in fileName starting after the end coordinates
        for(int i= 0; i < h; i++) {
            char[] line = scanner.nextLine().toCharArray();
            row = new ArrayList<MazeSquare>();
            //Iterates through each character within the aforementioned line in fileName and stores
            //the information in an ArrayList of MazeSquare objects
            for(int j = 0; j < w; j++) {
                String type = Character.toString(line[j]);
                row.add(new MazeSquare(line[j], j, i, startX, startY, finishX, finishY, w));
            }
            rowList.add(row);
        } 
    }

    /**
     * Print the Maze to System.out
     */
    public void printmaze() {
        for (int c = 0; c < w; c++) {
                System.out.print("+-----");
        }
        System.out.print("+");
        System.out.println();
        //PRIMARY ITERATION: Iterates through each row of the maze. Each MazeSquare can be thought of having four distinct parts:
        //the upper, middle, lower, and lower border section. Note that only the middle section can house a start or a finish designator, and only the lower border section
        //can house a "bottom." All sections, however, can house left or right borders.
        // Let each of these sections be represented by the letters A, B, C, and D, respectively.
        for (int d = 0; d < h; d++) {
        	//SECONDARY ITERATION A: Iterates through each column of the maze and prints the A section of the MazeSquares in a particular row of MazeSquares
            for (int e = 0; e < w; e++) {
            	//CASE where a right wall should not be printed for the maze square
                if (rowList.get(d).get(e).getRight() == false) {
                	//SUB-CASE where a left wall should be printed for the maze square
                    if (rowList.get(d).get(e).getLeft() == true) {
                        System.out.print("|     ");
                    //SUB-CASE where a left wall should not be printed for the maze square
                    } else {
                        System.out.print("      ");
                    }
                }
                //CASE where a right wall should be printed for the maze square
                if (rowList.get(d).get(e).getRight() == true) {
                	//SUB-CASE where a left wall should be printed for the maze square
                    if (rowList.get(d).get(e).getLeft() == true) {
                        System.out.print("|     |");
                    //SUB-CASE where a left wall should not be printed for the maze square
                    } else {
                        System.out.print("      |");
                    }
                }
            }
            //Prints a new line, thus moving onto the B section of the MazeSquares in a particular row of MazeSquares
            System.out.println();
            
            //SECONDARY ITERATION B: Iterates through each column of the maze and prints the B section of the MazeSquares in a particular row of MazeSquares
            for (int e = 0; e < w; e++) {
            	//CASE where a right wall should be printed for the maze square
                if (rowList.get(d).get(e).getRight() == true) {
					//SUB-CASE where a left wall should be printed for the maze square
                    if (rowList.get(d).get(e).getLeft() == true) {
                    	//SUB-SUB-CASE where the start designator should be printed for the maze square
                        if (rowList.get(d).get(e).isStart() == true) {
                            System.out.print("|  S  |");
                        } 
                        //SUB-SUB-CASE where the finish designator should be printed for the maze square
                        else if (rowList.get(d).get(e).isEnd() == true) {
                            System.out.print("|  F  |");
                        //SUB-SUB-CASE where no designator should be printed for the maze square
                        } else {
                            System.out.print("|     |");
                        }
                    //SUB-CASE where a left wall should not be printed for the maze square
                    } else {
                    	//SUB-SUB-CASE where the start designator should be printed for the maze square
                        if (rowList.get(d).get(e).isStart() == true) {
                            System.out.print("   S  |");
                        //SUB-SUB-CASE where the finish designator should be printed for the maze square
                        } else if (rowList.get(d).get(e).isEnd() == true) {
                            System.out.print("   F  |");
                        //SUB-SUB-CASE where no designator should be printed for the maze square
                        } else {
                            System.out.print("      |");
                        }
                    }
                }
                //CASE where a right wall should not be printed for the maze square
                if (rowList.get(d).get(e).getRight() == false) {
                	//SUB-CASE where a left wall should be printed for the maze square
                    if (rowList.get(d).get(e).getLeft() == true) {
                    	//SUB-SUB-CASE where the start designator should be printed for the maze square
                        if (rowList.get(d).get(e).isStart() == true) {
                            System.out.print("|  S  ");
                        //SUB-SUB-CASE where the finish designator should be printed for the maze square
                        } else if (rowList.get(d).get(e).isEnd() == true) {
                            System.out.print("|  F  ");
                        //SUB-SUB-CASE where no designator should be printed for the maze square
                        } else {
                            System.out.print("|     ");
                        }
                    //SUB-CASE where a left wall should not be printed for the maze square
                    } else {
                    	//SUB-SUB-CASE where the start designator should be printed for the maze square
                        if (rowList.get(d).get(e).isStart() == true) {
                            System.out.print("   S  ");
                        //SUB-SUB-CASE where the finish designator should be printed for the maze square
                        } else if (rowList.get(d).get(e).isEnd() == true) {
                            System.out.print("   F  ");
                        //SUB-SUB-CASE where no designator should be printed for the maze square
                        } else {
                            System.out.print("      ");
                        }
                    }
                }
            }
            //Prints a new line, thus moving onto the C section of the MazeSquares in a particular row of MazeSquares
            System.out.println();
            
            //SECONDARY ITERATION C: Iterates through each column of the maze and prints the C section of the MazeSquares in a particular row of MazeSquares
            for (int e = 0; e < w; e++) {
            	//CASE where a right wall should be printed for the maze square
                if (rowList.get(d).get(e).getRight() == true) {
                	//SUB-CASE where a left wall should be printed for the maze square
                    if (rowList.get(d).get(e).getLeft() == true) {
                        System.out.print("|     |");
                    //SUB-CASE where a left wall should not be printed for the maze square
                    } else {
                        System.out.print("      |");
                    }
                //CASE where a right wall should not be printed for the maze square
                } else if (rowList.get(d).get(e).getRight() == false) {
                	//SUB-CASE where a left wall should be printed for the maze square
                    if (rowList.get(d).get(e).getLeft() == true) {
                        System.out.print("|     ");
                    //SUB-CASE where a left wall should not be printed for the maze square
                    } else if (rowList.get(d).get(e).getLeft() == false) {
                        System.out.print("      ");
                    }
                }
            }
            //Prints a new line, thus moving onto the D section of the MazeSquares in a particular row of MazeSquares
            System.out.println();
            
            //SECONDARY ITERATION D: Iterates through each column of the maze and prints the D section of the MazeSquares in a particular row of MazeSquares
            for (int e = 0; e < w; e++) {
            	//CASE where a right wall should be printed for the maze square
                if (rowList.get(d).get(e).getRight() == true) {
                	//SUB-CASE where a bottom wall should be printed for the maze square
                    if (rowList.get(d).get(e).getBottom() == true) {
                        System.out.print("+-----+");
                    //SUB-CASE where a bottom wall should not be printed for the maze square
                    } else {
                        System.out.print("+     +");
                    }
                }
                //CASE where a right wall should not be printed for the maze square
                else {
                	//SUB-CASE where a bottom wall should be printed for the maze square
                    if (rowList.get(d).get(e).getBottom() == true) {
                        System.out.print("+-----");                    
                    }
                    //SUB-CASE where a bottom wall should not be printed for the maze square
                    else {
                        System.out.print("+     ");
                    }
                }
            }
            //Prints a new line, thus exiting the D section of the MazeSquares
            System.out.println();
        }
    }


    /**
     * This main program houses the
     * load-from-file and print-to-System.out Maze capabilities
     * 
     * @param args an array of Strings that is conventionally used in the main method
     */
    public static void main(String[] args) {
        if (args.length != 1) {
           System.err.println("Usage: java Maze mazeFile");
           System.exit(1);
        }
		//Create an object m of the class Maze
		Maze m = new Maze();
    	m.load(args[0]);
       	m.printmaze();
    }
}
