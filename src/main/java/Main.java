import org.w3c.dom.css.CSSStyleDeclaration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {


    public static void main(String[] args) {






        // Initialise grid object
        Grid grid = new Grid();

        //Declare scanner for user input
        Scanner myObj = new Scanner(System.in);




        String input = "";



        System.out.println("\nPlease select whether you would like to start a 'new game' or continue playing a previously 'saved game'");


        int continuePlaying = 0;

        while (!input.equals("new game") && !input.equals("saved game")) {

            input = myObj.nextLine();
            input.trim();// Read user input// Read user input



            if (input.equals("saved game"))
            {
                continuePlaying = grid.loadGame();
               // System.out.println("\nYou chose to replay a saved game");
                if (continuePlaying == 1){
                                    System.out.println("\nInitial Board State: ");
                                    grid.printInitialBoardState();
                    System.out.println("\nCurrent grid state:");
                                }
                break;

            }
            else if (input.equals("new game"))
            {
                //Set the variable to continue playing eqaul to one
                continuePlaying = 1;

                // Call a function to get a randomised array of ints from 1-9
                int[] randomisedOrder = getRandomisedArray();

                // Call a function to get the users inputted difficulty
                String difficulty = getDifficulty();


                // Call the function to solve the sudoku grid
                grid.solveSudoku(grid.getGrid(), 0, 0, randomisedOrder);



                // Function to remove the correct amount of numbers based on the difficulty
                grid.removeNumbers(difficulty);


                System.out.println("\nInitial Board State: ");
                break;
            }
            System.out.println("\nPlease input a valid command:");
        }



        //Only if a viable game/file was loaded or new game was selected does the program to continue
if (continuePlaying == 1) {

    // Print the sudoku grid
    grid.print();


    // Get the amount of remaining places from the grid and output them to the user
    int remainingPlaces = grid.remainingPlaces(grid.getGrid());
    System.out.println("\nRemaining Places: " + remainingPlaces);


    // Print the list of input commands to the user
    displayControls();


    // Declare Scanner
    // Scanner myObj = new Scanner(System.in);


    // String
    input = "";
    String keyword;


    // Continue getting user input until they have selected a valid option
    while (!input.equals("quit") && !input.equals("save")) {
        System.out.println("\nPlease input a command:");
        input = myObj.nextLine();
        input.trim();// Read user input// Read user input

        // Keyword is set to the first word of the command
        keyword = input.split(",")[0];

        // If the user input equals 'quit' then break the loop
        if (keyword.equals("quit")) {
            break;
        } else if (keyword.equals("save")) {
            grid.saveGame();
            System.out.println("\nYou have Saved the game");
            break;
        } else if (keyword.equals("display controls")) {
            displayControls();

        } else if (keyword.equals("input")) {
            grid.inputValue(grid.getGrid(), input);

            System.out.println("\nYou have Inputted a value");
        } else if (keyword.equals("undo")) {
            grid.undo();
            //System.out.println("\nYou used undo");
        } else if (keyword.equals("redo")) {
            grid.redo();
            //System.out.println("\nYou used redo");
        } else if (keyword.equals("check grid")) {
            grid.checkGrid();
            //System.out.println("\nYou used check grid");
        } else {
            System.out.println("\nYour input was not valid");
        }


    }
    System.out.println("\nYou have quit the game");


    //grid.printStack();

}

    }





    // function to display the controls to the user via the possible input commands
    private static void displayControls() {

        System.out.println("\nINPUT COMMANDS:\n'display controls'  " +
                "           - Displays this message\n'input,<row>,<column>,<value>'" +
                " - Inputs a value into the grid, row[0-8], col[0-8], value [1-9]\n'quit'        " +
                "                 - ends the current game\n'save game'     " +
                "            - saves the current game\n'undo'            " +
                "             - Undoes the most recent move\n'redo'         " +
                "                - Redoes the most recent move\n'check grid'                   - Checks whether sudoku solution is correct");
    }


    // Function to get the users selected difficulty
    private static String getDifficulty() {

        // Declare Scanner
        Scanner myObj = new Scanner(System.in);
        System.out.println("\nPlease select one of the follwing difficulty levels:\nEasy\nMedium\nHard\n");

        String difficulty = "";

        // Continue getting user input until they have selected a valid option
        while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard") && !difficulty.equals("test")) {

            difficulty = myObj.nextLine();
            difficulty.trim();// Read user input// Read user input

            if (difficulty.equals("easy") || difficulty.equals("medium") || difficulty.equals("hard") || difficulty.equals("test")) {
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
        int[] randomisedOrder = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int i = 0;

        for (Integer number : order) {
            randomisedOrder[i] = number.intValue();
            i++;
        }


        return randomisedOrder;
    }


}



