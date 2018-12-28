/**
 * MazeSquare.java
 * A helper class that works in conjunction with the Maze class.
 * Represents a single square within a rectangular maze
 *
 * @author Paul Reich & John Mullan
 * Note: help and advice was given in the writing of this class from Elliot Pickens
 */
public class MazeSquare {
    private boolean bottom;
    private boolean top;
    private boolean right;
    private boolean left;
    private boolean start;
    private boolean end;
    private int x;
    private int y;
    private char c;

	/**
	 * Constructor for the MazeSquare class
	 *
	 * @param c the character in fileName that dictates the boundary properties of a particular MazeSquare object (L,-,_, or |)
	 * @param x the x-coordinate of the maze square
	 * @param y the y-coordinate of the maze square
	 * @param startX the x-coordinate of the square in which the maze starts
	 * @param startY the y-coordinate of the square in which the maze starts
	 * @param finishX the x-coordinate of the square in which the maze finishes
	 * @param finishY the y-coordinate of the square in which the maze finishes
	 * @param xDim the dimension of the maze in terms of x-coordinates (equal to the maze's width)
	 */
    public MazeSquare(char c, int x, int y, int startX, int startY, int finishX, int finishY, int xDim) {

        this.x = x;
        this.y = y;
        this.c = c;


        if( x == startX && y == startY){
            start = true;
        } else if(x == finishX  && y == finishY ) {
            end = true;
        } else {
            start = false;
            end = false;
        }
        if(y == 0){
            top = true;

        }else {
            top = false;
        }
         if(x == xDim -1){
            right = true;
        }else{
            right = false;
         }
        if(c == 'L') {
            bottom = true;
            left = true;
        }else if(c == '|'){
            left = true;
            bottom = false;
        }else if(c == '_'){
            left = false;
            bottom = true;
        }
        else if (c == '-') {
            left = false;
            bottom = false;
        }

    }
    /**
     * Gets information as to whether or not a particular MazeSquare object is left-most relative to other MazeSquare objects in its row
     * @return a boolean describing whether or not a MazeSquare object is left-most relative to other MazeSquare objects in its row
     */
    public boolean getLeft() {
        return left;
    }
    
    /**
     * Gets information as to whether or not a particular MazeSquare object is bottom-most relative to other MazeSquare objects in its column
     * @return a boolean describing whether or not a MazeSquare object is bottom-most relative to other MazeSquare objects in its column
     */    
    public boolean getBottom(){
        return bottom;
    }

    /**
     * Gets information as to whether or not a particular MazeSquare object is top-most relative to other MazeSquare objects in its column
     * @return a boolean describing whether or not a MazeSquare object is top-most relative to other MazeSquare objects in its column
     */    
    public boolean getTop() {
        return top;
    }
    
    /**
     * Gets information as to whether or not a particular MazeSquare object is right-most relative to other MazeSquare objects in its row
     * @return a boolean describing whether or not a MazeSquare object is right-most relative to other MazeSquare objects in its row
     */
    public boolean getRight() {
        return right;
    }
    
    /**
     * Gets information as to whether or not a particular MazeSquare object is the starting square of the maze
     * @return a boolean describing whether or not a MazeSquare object is the starting square of the maze
     */
    public boolean isStart() {
        return start;
    }
    
    /**
     * Gets information as to whether or not a particular MazeSquare object is the finish square of the maze
     * @return a boolean describing whether or not a MazeSquare object is the finish square of the maze
     */
    public boolean isEnd() {
        return end;
    }
    
    /**
     * Gets the x-coordinate of the MazeSquare object(increments from left to right starting at zero)
     * @return the x-coordinate of the MazeSquare object in the form of an int
     */
    public int getX(){
        return x;
    }
    
    /**
     * Gets the y-coordinate of the MazeSquare object(increments from top to bottom starting at zero)
     * @return the y-coordinate of the MazeSquare object in the form of an int
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the character that dictates the boundary properties of a particular MazeSquare object (L,-,_, or |)
     * @return the character that dictates the boundary properties of a particular MazeSquare object (L,-,_, or |)
     */
    public char getCharacter() {
        return c;
    }
}
	

