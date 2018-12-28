import java.util.*;
import java.lang.*;

 // Read me
 //Paul Reich HW2
 // CS 201 Data Structures
public class Welcome {
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         System.out.println("What is your name?");
         StringBuilder name = new StringBuilder(scanner.nextLine());  // Input for the user's name
         System.out.println("What is your integer?");
         try {
             int number = scanner.nextInt();                        //Input for the user's chosen number
             System.out.println("Welcome " + name);
             System.out.println("Your name backwards is " + name.reverse());          // Reverses user's name
             if (number > 0) {
                 for (int i = 1; i <= number; i++) {                        // For loop for positive triangle
                     StringJoiner pyramid = new StringJoiner(",");
                     for (int j = 1; j <= i; j++) {
                         pyramid.add("" + i);
                     }
                     System.out.println(pyramid);
                 }
             } else if (number < 0) {
                 for (int i = -1; i >= number; i--) {                    //For loop for negative triangle
                     StringJoiner pyramid = new StringJoiner(",");
                     for (int j = -1; j >= i; j--) {
                         pyramid.add("" + i);
                     }
                     System.out.println(pyramid);
                 }
             } else {
                 System.out.println("Cannot print a triangle of height 0");
             }
         } catch (InputMismatchException e) {                           // Catches when a user inputs a non-integer
             System.out.println("You must enter an integer.");
             System.exit(1);
         }
     }
 }


