package Lab4.models;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuModel
{
    private final int SIZE = 9;
    private final int GRID_SIZE = 3;
    private boolean initialization = false;

    private final SudokuTile[][] sudokuBoard;
    private final SudokuTile[][] boardSolution;
    private final Set<SudokuTile> previousMoves = new HashSet<>();
    private final Set<SudokuTile> presetMoves = new HashSet<>();

    public SudokuModel()
    {
        this.sudokuBoard = new SudokuTile[SIZE][SIZE];
        this.boardSolution = new SudokuTile[SIZE][SIZE];
        newGame(Difficulty.MEDIUM);
        initialization = true;
    }

    public SudokuTile getTile(int row, int col)
    {
        return sudokuBoard[row][col];
    }

    public void setTileObject(SudokuTile tile, int value)
    {
        if (presetMoves.contains(tile))
            return;
        tile.setValue(value);
        previousMoves.add(tile);
    }

    /**
     * Clear all moves done by the user
     */
    public void clearMoves()
    {
        previousMoves.forEach(x -> x.setValue(0));
        previousMoves.clear();
    }

    public boolean isGameOver()
    {
        for (SudokuTile[] row : sudokuBoard) {
            for (SudokuTile tile : row) {
                if (tile.getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public SudokuTile[][] getSudokuBoard()
    {
        return sudokuBoard;
    }

    public void setSudokuBoard(int[][] board)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] != 0)
                    presetMoves.add(sudokuBoard[i][j]);
                sudokuBoard[i][j].setValue(board[i][j]);
            }
        }
    }

    /**
     * Initializes a new game
     * @param difficulty Sets the difficulty
     */
    public void newGame(Difficulty difficulty)
    {
        IntStream.range(0, SIZE).forEach(i ->
                IntStream.range(0, SIZE).forEach(j -> {
                    if (initialization) {
                        this.sudokuBoard[i][j].setValue(0);
                        this.boardSolution[i][j].setValue(0);
                    } else {
                        this.sudokuBoard[i][j] = new SudokuTile();
                        this.boardSolution[i][j] = new SudokuTile();
                    }
                })
        );

        switch (difficulty)
        {
            case EASY:
                fillBoard(0, 0);
                removeNumbers(20);
                break;
                case MEDIUM:
                    fillBoard(0, 0);
                    removeNumbers(40);
                    break;
                    case HARD:
                        fillBoard(0, 0);
                        removeNumbers(60);
                        break;
        }
        solveBacktrack(0, 0);
    }

    /**
     * Has the game been finished?
     * @return Game has been finished
     */
    public boolean hasWon() { return isBoardPlayable(false); }
    public boolean isValid() { return isBoardPlayable(true); }

    /**
     * Is the board still playable?
     * @param partial Checks playability or for a win
     * @return Playability of the board
     */
    private boolean isBoardPlayable(boolean partial)
    {
        for (int i = 0; i < SIZE; i++)
        {
            if (!isRegionValid(i, partial, true))
                return false;
            System.out.println("row valid");
            if (!isRegionValid(i, partial, false))
                return false;
            System.out.println("col valid");
        }

        for (int gridRow = 0; gridRow < GRID_SIZE; gridRow++)
            for (int gridCol = 0; gridCol < GRID_SIZE; gridCol++)
                if (!isSubgridValid(gridRow, gridCol, partial))
                    return false;
        return true;
    }

    /**
     * Checks if a row or column is valid
     * @param index Row or column
     * @param filter Check for validity or for a win
     * @param isRow Checks a row or column
     * @return Validity of the region
     */
    private boolean isRegionValid(int index, boolean filter, boolean isRow)
    {
        int[] values = new int[SIZE];

        for (int i = 0; i < SIZE; i++)
            values[i] = isRow ? sudokuBoard[index][i].getValue() : sudokuBoard[i][index].getValue();

        Arrays.sort(values);

        if (filter) {
            Set<Integer> uniqueValues = new HashSet<>();
            for (int value : values) {
                if (value != 0 && !uniqueValues.add(value)) {
                    return false;
                }
            }
            return true;
        }
        Arrays.sort(values);
        return Arrays.equals(values, IntStream.rangeClosed(1, 9).toArray());
    }

    private boolean isSubgridValid(int gridRow, int gridCol, boolean filter)
    {
        int[] values = new int[SIZE];
        int index = 0;

        for (int row = 0; row < GRID_SIZE; row++)
            for (int col = 0; col < GRID_SIZE; col++)
                values[index++] = sudokuBoard[gridRow * GRID_SIZE + row][gridCol * GRID_SIZE + col].getValue();

        Arrays.sort(values);

        if (filter) {
            // Check for duplicates among non-zero values using a HashSet
            Set<Integer> uniqueValues = new HashSet<>();
            for (int value : values) {
                if (value != 0 && !uniqueValues.add(value)) {
                    return false; // Duplicate found
                }
            }
            return true; // No duplicates found
        }

        // Check if the subgrid contains exactly the numbers 1 through 9
        Arrays.sort(values);
        return Arrays.equals(values, IntStream.rangeClosed(1, 9).toArray());
    }

    /**
     * Gives the user a hint by setting a correct tile to an unsolved tile
     */
    public void giveHint()
    {
        if (hasWon())
            return;

        Random randNum = new Random();
        int r, c;
        do
        {
            r = randNum.nextInt(SIZE);
            c = randNum.nextInt(SIZE);
        } while (sudokuBoard[r][c].getValue() != 0);

        setTileObject(getTile(r, c), boardSolution[r][c].getValue());
    }

    /**
     * Fills the board up with specified rows and columns
     * @param row The row
     * @param col The column
     * @return Is the board filled?
     */
    private boolean fillBoard(int row, int col)
    {
        if (row == SIZE)
            return true;

        if (col == SIZE)
            return fillBoard(row + 1, 0);

        int[] randNums = getRandomizedNumbers();
        for (int num : randNums)
        {
            if (!isValidPlacement(num, row, col))
                continue;
            sudokuBoard[row][col].setValue(num);
            if (fillBoard(row, col + 1))
                return true;
            sudokuBoard[row][col].setValue(0);
        }
        return false;
    }

    /**
     * Returns a list of a randomized sequence ranging from 1-9
     * @return The list of randomized numbers
     */
    private int[] getRandomizedNumbers()
    {
        return new Random().ints(1, 10)
                .distinct()
                .limit(9)
                .sorted()
                .toArray();
    }

    private boolean isValidPlacement(int num, int row, int col)
    {
        for (int i = 0; i < SIZE; i++) {
            if (sudokuBoard[row][i].getValue() == num || sudokuBoard[i][col].getValue() == num) {
                return false;
            }
        }

        int subGridRowStart = (row / GRID_SIZE) * GRID_SIZE;
        int subGridColStart = (col / GRID_SIZE) * GRID_SIZE;
        for (int i = 0; i < GRID_SIZE; i++)
            for (int j = 0; j < GRID_SIZE; j++)
                if (sudokuBoard[subGridRowStart + i][subGridColStart + j].getValue() == num)
                    return false;
        return true;
    }

    private void removeNumbers(int count)
    {
        Random randNum = new Random();
        for (int i = 0; i < count; i++)
        {
            int row, col;
            do
            {
                row = randNum.nextInt(SIZE);
                col = randNum.nextInt(SIZE);
            } while (sudokuBoard[row][col].getValue() == 0);
            sudokuBoard[row][col].setValue(0);
        }
    }

    private boolean solveBacktrack(int row, int col)
    {
        if (row == SIZE)
            return true;
        if (col == SIZE)
            return solveBacktrack(row + 1, 0);
        if (sudokuBoard[row][col].getValue() != 0)
            return solveBacktrack(row, col + 1);

        for (int num = 1; num <= SIZE; num++)
        {
            if (!isValidPlacement(num, row, col))
                continue;
            boardSolution[row][col].setValue(num);
            if (solveBacktrack(row, col + 1))
                return true;
            boardSolution[row][col].setValue(0);
        }
        return false;
    }
}
