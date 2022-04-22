import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {




    public static void main(String[] args) {


        // Initialise grid object
        Grid grid = new Grid();

        // Call a function to get a randomised array of ints from 1-9
        int[] randomisedOrder = getRandomisedArray();

        // Call a function to get the users inputted difficulty
        String difficulty = getDifficulty();


        // Call the function to solve the sudoku grid
        grid.solveSudoku(grid.getGrid(), 0 ,0 , randomisedOrder);




       // Function to remove the correct amount of numbers based on the difficulty
        grid.removeNumbers(difficulty);


        System.out.println("\nInitial Board State: ");
        // Print the sudoku grid
        grid.print(grid.getGrid());


       // Get the amount of remaining places from the grid
        int remainingPlaces = grid.remainingPlaces();

        System.out.println("\nRemaining Places: " + remainingPlaces);

    }





    // Function to get the users selected difficulty
    private static String getDifficulty() {

        // Declare Scanner
        Scanner myObj = new Scanner(System.in);
        System.out.println("\nPlease select one of the follwing difficulty levels:\nEasy\nMedium\nHard\n");

        String difficulty = "";

        // Continue getting user input until they have selected a valid option
        while (!difficulty.equals("easy") && !difficulty.equals("medium")  && !difficulty.equals("hard"))
        {

            difficulty = myObj.nextLine();
            difficulty.trim();// Read user input// Read user input

            if (difficulty.equals("easy")  || difficulty.equals("medium")  || difficulty.equals("hard")){
                break;
            }
            System.out.println("Please input a valid option ('easy', 'medium' or 'hard') ");

        }

        // Print out the users selected difficulty
        System.out.println("You have selected " + difficulty);



        return difficulty;
    }


    // Return an array from 1-9 in a randomised order
    private static int[] getRandomisedArray() {
        // Create an arraylist with numbers from 1-9
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            order.add(i);
        }
        // randomise their order
        Collections.shuffle(order);
        int[] randomisedOrder = {1,2,3,4,5,6,7,8,9};

        int i =0;

        for (Integer number: order) {
            randomisedOrder[i] = number.intValue();
            i++;
        }


        return randomisedOrder;
    }

}




