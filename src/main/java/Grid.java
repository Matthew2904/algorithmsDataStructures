import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Grid {

    // Decclare some attributes of a grid
    private int N = 9;
    private int grid[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

    private int initialBoardState[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };


    // Initialise an empty string
    Stack<String> undoStack = new Stack<String>();
    Stack<String> redoStack = new Stack<String>();



    //Getter Methods
    public int[][] getGrid() {
        return grid;
    }

    public int getN() {
        return N;
    }





    /* Takes a partially filled-in grid and attempts
            to assign values to all unassigned locations in
            such a way to meet the requirements for
            Sudoku solution (non-duplication across rows,
            columns, and boxes) */
    public boolean solveSudoku(int grid[][], int row, int col, int[] randomisedOrder)
    {
        int randomNumber;

		/*if we have reached the 8th
		row and 9th column (0
		indexed matrix) ,
		we are returning true to avoid further
		backtracking	 */
        if (row == N - 1 && col == N)
            return true;

        // Check if column value becomes 9 ,
        // we move to next row
        // and column start from 0
        if (col == N) {
            row++;
            col = 0;
        }

        // Check if the current position
        // of the grid already
        // contains value >0, we iterate
        // for next column
        if (grid[row][col] != 0)
            return solveSudoku(grid, row, col + 1, randomisedOrder);

        for (int num = 0; num < 9; num++) {

            randomNumber = randomisedOrder[num];

            // Check if it is safe to place
            // the num (1-9) in the
            // given row ,col ->we move to next column
            if (isSafe(grid, row, col, randomNumber)) {

				/* assigning the num in the current
				(row,col) position of the grid and
				assuming our assigned num in the position
				is correct */
                grid[row][col] = randomNumber;

                // Checking for next
                // possibility with next column
                if (solveSudoku(grid, row, col + 1, randomisedOrder))
                    return true;
            }
			/* removing the assigned num , since our
			assumption was wrong , and we go for next
			assumption with diff num value */
            grid[row][col] = 0;
        }
        return false;
    }




    /* A utility function to print grid */
    public void print()
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }

    public void printInitialBoardState()
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(initialBoardState[i][j] + " ");
            System.out.println();
        }
    }




    // Check whether it will be legal
    // to assign num to the
    // given row, col
    public  boolean isSafe(int[][] grid, int row, int col,
                          int num)
    {

        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= 8; x++)
            if (grid[row][x] == num)
                return false;

        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= 8; x++)
            if (grid[x][col] == num)
                return false;

        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int startRow = row - row % 3, startCol
                = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }



    public void removeNumbers(String difficulty){

        int col;
        int row;

        // Create an array filled with 60 (random) removed indexes
        int randomIndexes[] = new int[60];

        // Create new Random object
        Random randomNum = new Random();


        // Remove the random indexes values from the grid
        for (int i =0; i < 60; i++){



            // constrain the random number between indexes 0-8
            randomIndexes[i] = randomNum.nextInt(80);



            // Get the row and column of the specific index
            row = randomIndexes[i]/9;
            col =  randomIndexes[i]%9;

            /* Break the loop at 20 or 40 if easy or medium is were the
               Selected difficulty
            */
            if ((difficulty.equals("easy") && i == 20 )||(difficulty.equals("medium") && i == 40) ||(difficulty.equals("test") && i == 1))
            {
                break;
            }
            // Remove those filled positions from the grid
            grid[row][col] = 0;

        }

        for (int i  =0; i < 9; i++)
        {
            for (int x =0; x < 9; x++){
                initialBoardState[i][x] = grid[i][x];
            }
        }


    }



    // Function to return the amount of remaining Places to the user
    public int remainingPlaces(int[][] grid)
    {
        int counter = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                // if the number equals 0 incremnt the counter
                if (grid[i][j] == 0) {
                    counter++;
                }

            }
        }
        return counter;
    }




    // Function to input a value to a index in the grid
    public void inputValue(int[][] grid, String input) {


        // split the input by comma
        int inputValues[] = new int[3];

        String values[] = input.split(",");

        // check the array has 4 items in length
        if (values.length == 4)
        {
            for (int i = 0; i < 3; i++)
            {
                // Try to convert the input values to ints if it is not possible then print error message
                try
                {
                    inputValues[i] = Integer.parseInt(values[i+1]);

                   // System.out.println("Input Values[" + i + "]: " + inputValues[i]);

                    // Make sure input value is between 1 and 9
                    if ((inputValues[i] > 8 || inputValues[i] < 0 ) && (i == 0 || i ==1))
                    {
                        System.out.println("\nPlease enter a valid input from the input commands");
                        break;
                    }

                    // if all of the values get validated change the value of the position
                    if (i == 2)
                    {
                        if (inputValues[i] < 10 && inputValues[i] > 0)
                        {
                            // If the position in the initial board state eqauls 0 then
                            //  allow thew position to be altered
                            if (initialBoardState[inputValues[0]][inputValues[1]] == 0 )
                            {
                                // Add the command to the undo stack
                                undoStack.add(input);

                                // Clear the redo Stack
                                redoStack.clear();
                                // Set the position in the grid to the input value
                                grid[inputValues[0]][inputValues[1]] = inputValues[2];

                                // Print out the grid after every input
                                System.out.println();
                                print();
                                System.out.println();
                            }
                            // ot
                            else
                            {
                                System.out.println("Input Value: Row " + inputValues[0] + " Col " + inputValues[1] + " cannot be altered");

                            }



                        }
                        else
                        {
                            System.out.println("\nPlease enter a valid input from the input commands");
                        }

                    }

                }
                catch (NumberFormatException ex)
                {
                    System.out.println("Please enter a valid input from the input commands");
                    break;

                }
            }

        }

        // if the above input validation passes then input the value into the section of the grid

    }







    //function to check if the users soltion is correct
    public void checkGrid() {

        // For every index in the grid
        // check that the number isSafe;
        int row;
        int col;
        int num;

        boolean safe = true;


        for (int i =0; i < 81; i++)
        {
            row = i/9;
            col = i % 9;

            num = grid[row][col];

          grid[row][col] = 0;
          safe =  isSafe(grid, row, col, num);
          if (safe == false)
          {
              //System.out.println(row + " " + col + " " + num);
              grid[row][col] = num;
              break;
          }
          else{
              grid[row][col] = num;
          }

        }


        if (safe == true){
            System.out.println("Well done, You successfully completed the sudoku");
        }
        else{
            System.out.println("Unfortunately your solution is incorrect, keep trying ;)");
        }


    }









    public void printStack(){

        System.out.println(undoStack);
    }










    public void undo(){


        if(undoStack.size() > 0 )
        {

        // Set the input command to the top item of the stack
        String inputCommand = undoStack.pop();

        // Get an array of the inputCommand
        String values[] = inputCommand.split(",");

        // Set the Values
        int row = Integer.parseInt(values[1]);
        int col = Integer.parseInt(values[2]);
        int num = Integer.parseInt(values[3]);


        // if the size of the undoStack is larger than or equal to one after
        // popping the last input command


        String currentCommand[];


        int currentRow;
        int currentCol;
        int currentNum;

        int previousValue =0;



  // if the stack originally contained two or more items then iterate over the remaining
  // inputs into the stack to see if they had previously altered that position
if (undoStack.size() > 0 ) {
    for (int i = (undoStack.size() - 1); i >= 0; i--) {
        currentCommand = undoStack.get(i).split(",");

        currentRow = Integer.parseInt(currentCommand[1]);
        currentCol = Integer.parseInt(currentCommand[2]);
        currentNum = Integer.parseInt(currentCommand[3]);

        // If the current row and column in the loop equal the last altered
        // position then change the position to the previous value and break the loop;
        if ((row == currentRow) && (col == currentCol)) {

            grid[row][col] = currentNum;
            System.out.println("Undo: Replaced " + num + " with most previous value " + currentNum);
            previousValue = 1;
            redoStack.add(inputCommand);
            break;
        }


    }
}

        // in the case that no previous alteration to the grid position was made then
        // It should be restored to the value of the position store within the initial board state
        if (previousValue == 0)
        {
            grid[row][col] = initialBoardState[row][col];
            int number = initialBoardState[row][col];
            System.out.println("\nUndo: Row " + row + " Col " + col +" has been returned to its initial board state  "  + number);
            redoStack.add(inputCommand);
            //printInitialBoardState();
        }


    }

else{
    System.out.println("Nothing to Undo");
        }



// Print the current grid
print();
    }


    public void redo(){


        if (redoStack.size() > 0){
            // Set the input command to the top item of the redo stack
            // Pop the input command at the top of the redoStack
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


            System.out.println("Redo: Row " + row + " Col " + col +" =" + num + " replaced "  + currentNum + "\n");
            print();
        }
        {

            System.out.println("Redo: nothing to redo");
        }


    }




    public void saveGame(){

        // Declare Scanner
        Scanner myObj = new Scanner(System.in);

        String filename = "";

        System.out.println("\nPlease input a filename (note that if you enter a name of a file already in folder it will be overwritten:");
        filename = myObj.nextLine(); // Get the user input
        filename.trim(); // trim the user input


        // Initialise a string 4 data structures (grid, IBS, undoStack, redoStack)
        String gridString = "";
        String ibsString = "";
        String undoString = "";
        String redoString = "";

        // Append every position in the current state of the grid to a string followed by a comma
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

            // Append the value with a comma to the file contents variable
                gridString = gridString + grid[row][col] + ",";

            }
        }



        // Append every position in the initial board state to a string followed by a comma
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                // Append the value with a comma to the file contents variable
                ibsString = ibsString + initialBoardState[row][col] + ",";

            }
        }


        // Append every input command on the undoStack onto the undoString variable
        for (int i = 0; i < undoStack.size(); i++){
            redoString = redoString  + undoStack.get(i) + ",";
        }

        // Append every input command on the redoStack onto the redoString variable
        for (int i = 0; i < redoStack.size(); i++){
            undoString = undoString  + redoStack.get(i) + ",";
        }


        try {
            // Create or overwrite file
            FileWriter myWriter = new FileWriter(filename + ".txt");

            // Write to the file
            myWriter.write(gridString +  "\n" +  ibsString + "\n" + undoString + "\n" +redoString);
            myWriter.close();
            System.out.println("Saved the game to file: " + filename + ".txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }

}
