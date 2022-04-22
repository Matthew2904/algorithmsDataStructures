import java.util.ArrayList;
import java.util.Random;

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
            if ((difficulty.equals("easy") && i == 1 )||(difficulty.equals("medium") && i == 40))
            {
                break;
            }
            // Remove those filled positions from the grid
            grid[row][col] = 0;

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
                            grid[inputValues[0]][inputValues[1]] = inputValues[2];
                            System.out.println();
                            print();
                            System.out.println();
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
              System.out.println(row + " " + col + " " + num);
              break;
          }
          else{
              grid[row][col] = num;
          }

        }


        if (safe == true){
            System.out.println("Grid Correct");
        }
        else{
            System.out.println("Grid Incorrect At End");
        }


    }
}
