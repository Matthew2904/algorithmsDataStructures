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


        // Initialise variables
        String input = "";
        int continuePlaying = 0;
        int solved = 0;

        // Display Instructions
        System.out.println("\nPlease select whether you would like to start a 'new game' or continue playing a previously 'saved game'");


        // While the input is not 'new game' or 'saved game'
        while (!input.equals("new game") && !input.equals("saved game"))
        {

            // Ge the users input and trim it
            input = myObj.nextLine();
            input.trim();


            // If the user has chosen to load a saved game call grid.loadGame and break from the loop
            if (input.equals("saved game"))
            {
                // Call a function to load in a previously saved game
                continuePlaying = grid.loadGame();

                // if the coninue playing eqauls one
                if (continuePlaying == 1)
                {
                    // Print out the initial Grid
                    System.out.println("\nInitial Grid State: ");
                    grid.printInitialGridState();

                    // Print out header for current grid - print function is called below
                    System.out.println("\nCurrent grid state:");
                }

                break;

            }
            // If the user has chosen to play a new game
            else if (input.equals("new game"))
            {
                //Set the variable to continue playing eqaul to one
                continuePlaying = 1;

                // Call a function to get a randomised array of ints from 1-9
                int[] randomisedOrder = getRandomisedArray();

                // Call a function to get the users inputted difficulty
                String difficulty = getDifficulty();


                // Call the function to create the sudoku grid
                grid.createSudoku(grid.getGrid(), 0, 0, randomisedOrder);


                // Function to remove the correct amount of numbers based on the difficulty
                grid.removeNumbers(difficulty);

                // Print out header for Initial grid - print function is called below
                System.out.println("\nInitial Grid State: ");
                break;
            }

            // If the loop is still continuing at this point the input must of been wrong
            // display error message
            System.out.println("\nPlease input a valid command:");
        }



        //Only if a viable game/file was loaded or new game was selected does the program to continue
        if (continuePlaying == 1)
        {

            // Print the sudoku grid
            grid.print();


            // Get the amount of remaining places from the grid and output them to the user
            int remainingPlaces = grid.remainingPlaces(grid.getGrid());
            System.out.println("\nRemaining Places: " + remainingPlaces);


            // Print the list of input commands to the user
            displayControls();


            // reset input and declare keyword
            input = "";
            String keyword;


            // Continue getting user input until they have selected a valid option
            while (!input.equals("quit") && !input.equals("save"))
            {
                System.out.println("\nPlease input a command:");

                // Get the users input and trim it
                input = myObj.nextLine();
                input.trim();


                // Keyword is set to the first word of the command (by splitting by comma)
                keyword = input.split(",")[0];

                // If the user's keyword input equals 'quit' then break the loop
                if (keyword.equals("quit"))
                {
                    break;
                }
                // If the user's keyword input equals 'save' then call a function to save the game and break the loop
                else if (keyword.equals("save"))
                {
                    grid.saveGame();
                    break;
                }
                // If the user's keyword input equals 'display controls' then call a function to display the controls
                else if (keyword.equals("display controls"))
                {
                    displayControls();
                }
                // If the user's keyword input equals 'input' then call a function to handle input to the grid
                else if (keyword.equals("input"))
                {
                   solved =  grid.inputValue(grid.getGrid(), input);
                   if (solved == 1){
                       // Display instruction to user


                       while(!input.equals("save") || !input.equals("quit")) {

                           System.out.println("\nEnter 'save'  to save the current game or 'quit' to end it:");
                           input = myObj.nextLine();

                           // If input equals save then call the save function and break
                           if (input.equals("save"))
                           {
                               grid.saveGame();
                               break;
                           }
                           // If input equals save then  break
                           else if (input.equals("quit"))
                           {
                               break;
                           }



                       }

                       break;
                   }
                }
                // If the user's keyword input equals 'undo' then call a function to undo their last input to the grid
                else if (keyword.equals("undo"))
                {
                    grid.undo();
                }
                // If the user's keyword input equals 'redo' then call a function to redo what they last undid
                else if (keyword.equals("redo"))
                {
                    grid.redo();
                }
                // Else display a message to enter a valid input
                else
                {
                    System.out.println("\nYour input was not valid");
                }
            }

            // Display message to show they have quit the game
            System.out.println("\nYou have quit the game");
        }

    }




    /* @Name: displayControls
     * @Return: void
     * @Description: function to display the controls to the user via the possible input commands
     * */
    private static void displayControls() {

        System.out.println("\nINPUT COMMANDS:" +
                "\n'display controls'             - Displays this message" +
                "\n'input,<row>,<column>,<value>' - Inputs a value into the grid, row[0-8], col[0-8], value [1-9]" +
                "\n'quit'                         - ends the current game" +
                "\n'save game'                    - saves the current game" +
                "\n'undo'                         - Undoes the most recent move" +
                "\n'redo'                         - Redoes the most recent move" );
    }





    /* @Name: getDifficulty
     * @Return: String  - String of difficulty ("easy","medium","hard")
     * @Description: function to display the controls to the user via the possible input commands
     * */
    private static String getDifficulty() {

        // Declare Scanner
        Scanner myObj = new Scanner(System.in);

        // Display instructions
        System.out.println("\nPlease select one of the following difficulty levels:\nEasy\nMedium\nHard\n");

        String difficulty = "";

        // Continue getting user input until they have selected a valid option
        while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard") && !difficulty.equals("test")) {

            // Get users input and trim it
            difficulty = myObj.nextLine();
            difficulty.trim();

            // if the input equals one of the valid inputs break from the loop
            if (difficulty.equals("easy") || difficulty.equals("medium") || difficulty.equals("hard") || difficulty.equals("test")) {
                break;
            }
            // Display Instructions
            System.out.println("Please input a valid option ('easy', 'medium' or 'hard') ");

        }

        // Print out the users selected difficulty
        System.out.println("You have selected " + difficulty);


        return difficulty;
    }




    /* @Name: getRandomisedArray
     * @Return: String  - Array Of integers -
     * @Description: create an array from 1-9 in a randomised order
     * */
    private static int[] getRandomisedArray()
    {
        // Create an arraylist with numbers from 1-9
        ArrayList<Integer> order = new ArrayList<>();


        for (int i = 1; i <= 9; i++)
        {
            order.add(i);
        }

        // Use the collections shuffle method
        Collections.shuffle(order);

        int randomisedOrder[] = new int[9];

        int i = 0;

        // convert the Integer arrayList to integer array
        for (Integer number : order) {
            randomisedOrder[i] = number.intValue();
            i++;
        }

        return randomisedOrder;
    }


}



