import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Grid {

    // Grid attribute to store the current state of the grid
    private int grid[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                             { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };


    // Initial Board State to store the first interactable version of the grid
    private int initialGridState[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                          { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };


    // Declare an undo and redo stack
    Stack<String> undoStack = new Stack<String>();
    Stack<String> redoStack = new Stack<String>();



    /*   Getter Methods   */


    /* @Name: getGrid
    *  @Return: grid - current state of the grid
    * */
    public int[][] getGrid() {
        return grid;
    }


    /* @Name: getInitialGridState
     *  @Return: initialGridState - Initial grid state after removing numbers
     * */
    public int[][] getInitialGridState() {
        return initialGridState;
    }





    /* @Name: createSudoku
     * @Return: boolean  -  does the input board have a possible sudoku
     * @Description: recursively calls itself to find a sodoku solution to the grid
     * */
    public boolean createSudoku(int grid[][], int row, int col, int[] randomisedOrder)
    {
        // Declare integer for a random number
        int randomNumber;

        // Once the recursive loop has exceeded
        // every position in the grid return true
        if (row == 8 && col == 9)
        {
            return true;
        }


        // Once the recursive loop has exceeded
        // every position in a row, set the column
        // to 0 and increment the row
        if (col == 9)
        {
            row++;
            col = 0;
        }


        /** For nine numbers in order of the
         *  randomised order array check if the current number
         *  can be correctly placed in the sudoku grid
         *  if it can, call this function with the next column else
         *  continue iterating the loop
         */
        for (int num = 0; num < 9; num++)
        {

            // the current random number is the num index of randomised order
            randomNumber = randomisedOrder[num];

           /* If checkPosition returns ture then continue recursive loop else set
              else restore the postion to 0 and return false
            */
            if (checkPosition(grid, row, col, randomNumber))
            {

				// Set the current position to the current randomNumber
                grid[row][col] = randomNumber;

                // Recursively call createSudoku with incremented column
                if (createSudoku(grid, row, col + 1, randomisedOrder))
                {    return true;
                }
            }
            // Reset the current position to 0
            grid[row][col] = 0;
        }
        return false;
    }




    /* @Name: print
     * @Return: void
     * @Description: Prints the current state of the gird
     * */
    public void print()
    {
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }



    /* @Name: printInitialGridState
     * @Return: void
     * @Description: Prints the initial state of the grid
     * */
    public void printInitialGridState()
    {
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                System.out.print(initialGridState[row][col] + " ");
            }
            System.out.println();
        }
    }




    /* @Name: checkPostion
     * @Return: boolean
     * @Description: checks whether the current number fits in the grid position without violating any rules of sudoku
     * */
    public boolean checkPosition(int[][] grid, int row, int col,
                                  int num)
    {

        /* Check if the number repeated in its row
           return false if it is found */
        for (int i = 0; i <= 8; i++)
        {
            if (grid[row][i] == num)
            {
                return false;
            }

        }

       /* Check if the number repeated in its column
           return false if it is found */
        for (int i = 0; i <= 8; i++)
        {
            if (grid[i][col] == num)
            {
                return false;
            }

        }


        // Initialise variables for the starting indexes of the miniSquare
        int miniSquareRow = row - row % 3;
        int miniSquareCol = col - col % 3;



        /* Check if the number repeated in its mini-square
           return false if it is found */

        for (int i = 0; i < 3; i++)
        {
            for (int x = 0; x < 3; x++)
            {
                // set the row and col to the respective indexes plus the miniSquare values
                if (grid[miniSquareRow + i][miniSquareCol + x] == num)
                {
                    return false;
                }

            }

        }

        return true;
    }



    /* @Name: removeNumbers
     * @Return: void
     * @Description: Sets values of indexes based on a random array to 0 based on the difficulty
     * */
    public void removeNumbers(String difficulty){

        int col;
        int row;

        // Declare array with 60 values
        int randomIndexes[] = new int[60];

        // Declare random object
        Random randomNum = new Random();


        // Remove the random indexes values from the grid
        for (int i =0; i < 60; i++)
        {

            // constrain the random number between indexes 0-80
            randomIndexes[i] = randomNum.nextInt(80);


            // Get the row and column of the specific index
            row = randomIndexes[i]/9;
            col =  randomIndexes[i]%9;

            // Break the loop at 20 or 40 if easy or medium
            if ((difficulty.equals("easy") && i == 20 )||(difficulty.equals("medium") && i == 40) ||(difficulty.equals("test") && i == 2))
            {
                break;
            }

            // Remove those filled positions from the grid
            grid[row][col] = 0;

        }

        // Set every value of the initialgridstate to the current grid
        for (int i = 0; i < 9; i++)
        {
            for (int x =0; x < 9; x++){
                initialGridState[i][x] = grid[i][x];
            }
        }


    }


    /* @Name: remainingPlaces
     * @Return: int
     * @Description: Function to return the amount of remaining Places to the user
     * */
    public int remainingPlaces(int[][] grid)
    {
        // initialise counter
        int counter = 0;

        // Loop grid and increment where it equals 0
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                // if the number equals 0 increment the counter
                if (grid[row][col] == 0) {
                    counter++;
                }

            }
        }
        return counter;
    }




    /* @Name: inputValue
     * @Return: int - if the grid has been solved correclty or not
     * @Description: validates an input value and executes the command, adds command to undo Stack and clears redo stack
     * */
    public int inputValue(int[][] grid, String input) {

        int solved = 0;

        int inputValues[] = new int[3];

        // split the input by comma
        String values[] = input.split(",");

        // check the array has 4 items in length
        if (values.length == 4)
        {
            for (int i = 0; i < 3; i++)
            {
                // Try to convert the input values to integers, if it is not possible then print error message
                try
                {
                    inputValues[i] = Integer.parseInt(values[i+1]);

                    // Make sure input value is between 1 and 9 for the row and column
                    if ((inputValues[i] > 8 || inputValues[i] < 0 ) && (i == 0 || i ==1))
                    {
                        System.out.println("\nPlease enter a valid input from the input commands");
                        break;
                    }

                    // if the other values have been validated
                    if (i == 2)
                    {
                        // check if the value attempting to be inputted into the position is valid (between 1-9)
                        if (inputValues[i] < 10 && inputValues[i] > 0)
                        {
                            // If the position in the initial board state equals 0 then
                            //  allow the position to be altered
                            if (initialGridState[inputValues[0]][inputValues[1]] == 0 )
                            {
                                // Add the command to the undo stack
                                undoStack.add(input);

                                // Clear the redo Stack
                                redoStack.clear();

                                // Set the position in the grid to the input value
                                grid[inputValues[0]][inputValues[1]] = inputValues[2];

                                // Print out the grid after every valid input
                                System.out.println();
                                print();
                                System.out.println();

                                System.out.println("\nInput Value: Position(" + inputValues[0] + "," + inputValues[1]  + ") set to " + inputValues[2]);

                                // If there are no remaining places check the grid to see if it is correct
                                if (remainingPlaces(grid) == 0){
                                    solved = checkGrid();
                                }


                            }
                            // display error message that that postion cannot be altered
                            else
                            {
                                System.out.println("Input Value: Row " + inputValues[0] + " Col " + inputValues[1] + " cannot be altered");

                            }
                        }
                        // display error message to enter a valid input
                        else
                        {
                            System.out.println("\nPlease enter a valid input from the input commands");
                        }

                    }

                }
                // display error message to enter a valid input
                catch (NumberFormatException ex)
                {
                    System.out.println("Please enter a valid input from the input commands");
                    break;

                }
            }

        }
        // display error message to enter a valid input
        else
        {
            System.out.println("Please enter a valid input from the input commands");
        }
        return solved;

    }







    /* @Name: checkGrid
     * @Return: int - whether the grid has solved correctly or not
     * @Description: checks whether the user has completed the sudoku grid successfully
     * */
    public int checkGrid() {

        int num;

        boolean safe = true;

        int flag =0;

        // For every index in the grid
        // check that the number isSafe;
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {

                // Get the number of the position
                num = grid[row][col];

                // Set the position to 0
                grid[row][col] = 0;


                safe =  checkPosition(grid, row, col, num);

                // Set the position back to its value
                grid[row][col] = num;


                // if the position has a value that conflicts with soduko rules then break from the loop
                if (safe == false)
                {
                    flag = 1;
                    break;
                }


            }

            if (flag == 1)
            {
                break;
            }
        }

        // Print a message based on whether the sudoku is correct or incorrect
        if (safe == true){
            System.out.println("Well done, You successfully completed the sudoku");
            return 1;
        }
        else{
            System.out.println("\nUnfortunately your solution is incorrect, keep trying;)");

        }

        return 0;
    }





    /* @Name: undo
     * @Return: void
     * @Description: Undoes the users last input
     * */
    public void undo(){

        // If the undoStack has at least one item
        if(undoStack.size() > 0 )
        {

            // Set the input command to the top item of the stack and pop it from the stack
            String inputCommand = undoStack.pop();

            // Get an array of the inputCommand
            String values[] = inputCommand.split(",");

            // Set the Values
            int row = Integer.parseInt(values[1]);
            int col = Integer.parseInt(values[2]);
            int num = Integer.parseInt(values[3]);


            String currentCommand[];


            int currentRow;
            int currentCol;
            int currentNum;

            int previousValue =0;



            // if the stack originally contained two or more items then iterate over the remaining
            // inputs into the stack to see if they had previously altered that position
            if (undoStack.size() > 0 )
            {
                for (int i = (undoStack.size() - 1); i >= 0; i--)
                {
                    currentCommand = undoStack.get(i).split(",");

                    currentRow = Integer.parseInt(currentCommand[1]);
                    currentCol = Integer.parseInt(currentCommand[2]);
                    currentNum = Integer.parseInt(currentCommand[3]);

                    // If the current row and column in the loop equal the last altered
                    // position then change the position to the previous value and break the loop;
                    if ((row == currentRow) && (col == currentCol))
                    {

                        grid[row][col] = currentNum;
                        System.out.println("\nUndo: Position(" + row + "," + col +") Replaced " + num + " with most previous value " + currentNum+ "\n");
                        previousValue = 1;
                        redoStack.add(inputCommand);
                        break;
                    }


                }
            }

            // in the case that no previous alteration to the grid position was made then
            // It should be restored to the value of the position store within the initial board state (0)
            if (previousValue == 0)
            {
                int previousNumber = grid[row][col];
                grid[row][col] = initialGridState[row][col];
                int number = initialGridState[row][col];
                System.out.println("\nUndo: Position(" + row + "," + col +") has been returned to its initial board state "  + number  + " From " + previousNumber + "\n");

                // Add the input command to the redoStack
                redoStack.add(inputCommand);
            }


        }

        else
        {
            System.out.println("\nNothing to Undo\n");
        }


        // Print the current grid
        print();

    }









    /* @Name: redo
     * @Return: void
     * @Description: To execute the command/ input  on top of the redo stack
     * */
    public void redo(){

        // If the redo Stack has at least one item
        if (redoStack.size() > 0)
        {

            // Set the input command to the top item of the redo stack and pop it
            String inputCommand = redoStack.pop();

            //Push it onto the top of the undoStack
            undoStack.push(inputCommand);


            // Get an array of the inputCommand
            String values[] = inputCommand.split(",");

            // Set the Values
            int row = Integer.parseInt(values[1]);
            int col = Integer.parseInt(values[2]);
            int num = Integer.parseInt(values[3]);


            int currentNum = grid[row][col];

            grid[row][col] = num;

            // Print message for redo information
            System.out.println("\nRedo: Position(" + row + "," + col +") got Redone from input" + num + " to "  + currentNum + "\n");
            print();
        }
        // Else print message that there is nothing to redo
        else {

            System.out.println("Redo: nothing to redo");
        }


    }



    /* @Name: saveGame
     * @Return: void
     * @Description: To write the four dataStructures (grid, IBS, undoStack, redoStack) to a file
     * */
    public void saveGame(){

        // Declare Scanner
        Scanner myObj = new Scanner(System.in);

        String filename = "";

        System.out.println("\nPlease input a filename (note that if you enter a name of a file already in folder it will be overwritten) or 'quit' to exit the game without saving:");
        filename = myObj.nextLine(); // Get the user input
        filename.trim(); // trim the user input


        if (!filename.equals("quit")) {

            // Initialise a string 4 data structures (grid, IBS, undoStack, redoStack)
            String gridString = "";
            String ibsString = "";
            String undoString = "";
            String redoString = "";

            // Append every value in the current state of the grid to a string followed by a comma
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    gridString = gridString + grid[row][col] + ",";

                }
            }


            // Append every position in the initial board state to a string followed by a comma
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {

                    ibsString = ibsString + initialGridState[row][col] + ",";

                }
            }

            // Append every input command on the undoStack onto the undoString variable
            for (int i = 0; i < undoStack.size(); i++) {
                undoString = undoString + undoStack.get(i) + "/";
            }

            // Append every input command on the redoStack onto the redoString variable
            for (int i = 0; i < redoStack.size(); i++) {
                redoString = redoString + redoStack.get(i) + "/";
            }

            // Try to write it to a file or print a error message
            try {
                // Create or overwrite a file based on the users input
                FileWriter file = new FileWriter(filename + ".txt");

                // Write the dataStructures to the file
                file.write(gridString + "\n" + ibsString + "\n" + undoString + "\n" + redoString);

                // cloe the file
                file.close();

                // Print Success message
                System.out.println("Saved the game to file: " + filename + ".txt");

            } catch (IOException e) {
                // Print error message
                System.out.println("An error occurred.");
            }

        }

    }


    /* @Name: loadGame
     * @Return: int
     * @Description: To read the four dataStructures (grid, IBS, undoStack, redoStack) from a file
     *               into strings and call methods to put them back into their dataStructures
     * */
    public int loadGame(){


        // New Scanner Object
        Scanner myObj = new Scanner(System.in);
        String input = "";
        int foundFile =0;


        // While the user still wants to try and load a previous game
        while (!input.equals("quit")) {

            //output instructions
            System.out.println("Enter the name of a previous game to load or enter 'quit' to stop the current game:\n");

            // Get input
            input = myObj.nextLine();

            //if the user input equals quit then break the loop
            if (input.equals("quit")){
                break;
            }
            // Else attempt to read in the data from the file
            else{
                try {
                    // read a file based on the users input
                    File fileReader = new File(input + ".txt");
                    Scanner myReader = new Scanner(fileReader);

                    // Set foundFile to 1 to show a reading from the file is successful
                    foundFile = 1;
                    String data =  "";

                    // Call the respective function to place each string back in its data structure
                    for(int i =0; i < 4; i++)
                    {
                        data = myReader.nextLine();

                        if (i==0)
                        {
                            // Load in the current state of the grid
                            loadCurrentGrid(data);
                        }
                        else if (i==1)
                        {
                            // Load in the Initial state of the grid
                            loadInitialGridState(data);
                        }
                        else if (i==2)
                        {
                            // Load in the undo Stack
                            loadUndoStack(data);
                        }
                        else if (i==3)
                        {
                            // Load in the redo Stack
                            loadRedoStack(data);
                        }
                    }


                    // Close the file
                    myReader.close();

                    // Break from the loop
                    break;
                }
                // In the case that no file can be found
                catch (FileNotFoundException e)
                {
                    // Print error message
                    System.out.println("\nAn error occurred loading the file:\n");

                }
            }

        }

        return foundFile;
    }



    /* @Name: loadInitialGridState
     * @Return: void
     * @Description: split the data string and place the value in the initialGridSting
     * */
    private void loadInitialGridState(String data)
    {

        // Get array from data split by comma
        String[] values = data.split(",");

        int row;
        int col;

        // Place every value back in the initial grid state
        for (int i = 0; i < 81; i++){

            row = i/9;
            col = i%9;

            initialGridState[row][col] = Integer.parseInt(values[i]);
        }

    }





    /* @Name: loadCurrentGrid
     * @Return: void
     * @Description: split the data string and place the value in the current grid
     * */
    private void loadCurrentGrid(String data)
    {
        // Get array from data split by comma
        String[] values = data.split(",");

        int row;
        int col;

        // Place every value back in the initial grid state
        for (int i = 0; i < 81; i++){

            row = i/9;
            col = i%9;

            grid[row][col] = Integer.parseInt(values[i]);
        }
    }


    /* @Name: loadUndoStack
     * @Return: void
     * @Description: split the data string and place every input / command on the undoStack
     * */
    private void loadUndoStack(String data) {

        // Get array from data split by slash
        String values[] = data.split("/");

        // Place every input / command on the undoStack
        for (int i = 0; i < values.length; i++)
        {
            undoStack.add(values[i]);
        }
    }


    /* @Name: loadRedoStack
     * @Return: void
     * @Description: split the data string and place every input / command on the redoStack
     * */
    private void loadRedoStack(String data)
    {

        // Get array from data split by slash
        String values[] = data.split("/");

        // Place every input / command on the redoStack
        for (int i = 0; i < values.length; i++)
        {
            redoStack.add(values[i]);
        }
    }


}
