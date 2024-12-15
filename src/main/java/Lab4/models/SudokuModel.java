package Lab4.models;

import java.util.*;
import java.util.stream.IntStream;

public class SudokuModel
{
    private final int SIZE = 9;
    private final int GRID_SIZE = 3;
    private boolean initialization = false;

    private final Tile[][] board;
    private final Tile[][] boardSolution;
    private final Set<Tile> presetMoves = new HashSet<>();
    private final Set<Tile> previousMoves = new HashSet<>();

    public SudokuModel()
    {
        this.board = new Tile[SIZE][SIZE];
        this.boardSolution = new Tile[SIZE][SIZE];
        newGame(Difficulty.MEDIUM);
        initialization = true;
    }

    public Tile getTile(int row, int col)
    {
        return board[row][col];
    }

    public void setTile(Tile tile, int value)
    {
        if (presetMoves.contains(tile))
            return;
        tile.setNumber(value);
        previousMoves.add(tile);
    }

    /**
     * Clear all moves done by the user
     */
    public void clearMoves()
    {
        previousMoves.forEach(x -> x.setNumber(0));
        previousMoves.clear();
    }

    public Tile[][] getBoard()
    {
        return board;
    }

    public void setBoard(int[][] board)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] != 0)
                    presetMoves.add(this.board[i][j]);
                this.board[i][j].setNumber(board[i][j]);
            }
        }
    }

    public boolean isGameOver()
    {
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile.getNumber() == 0) {
                    return false;
                }
            }
        }
        return true;
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
                        this.board[i][j].setNumber(0);
                        this.boardSolution[i][j].setNumber(0);
                    } else {
                        this.board[i][j] = new Tile();
                        this.boardSolution[i][j] = new Tile();
                    }
                })
        );

        switch (difficulty)
        {
            case EASY:
                fillBoard(0, 0);
                removeNumbers(25);
                break;
            case MEDIUM:
                fillBoard(0, 0);
                removeNumbers(45);
                break;
            case HARD:
                fillBoard(0, 0);
                removeNumbers(65);
                break;
        }
        solutionBacktrackMethod(0, 0);
    }

    /**
     * Has the game been finished?
     * @return Game has been finished
     */
    public boolean hasWon() { return isBoardPlayable(false); }
    public boolean isPlayable() { return isBoardPlayable(true); }

    /**
     * Is the board still playable?
     * @param partial Checks playability or for a win
     * @return Playability of the board
     */
    private boolean isBoardPlayable(boolean partial)
    {
        for (int i = 0; i < SIZE; i++)
        {
            if (!isAreaPlayable(i, partial, true))
                return false;
            System.out.println("Row valid");
            if (!isAreaPlayable(i, partial, false))
                return false;
            System.out.println("Col valid");
        }

        for (int gridRow = 0; gridRow < GRID_SIZE; gridRow++)
            for (int gridCol = 0; gridCol < GRID_SIZE; gridCol++)
                if (!isGridPlayable(gridRow, gridCol, partial))
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
    private boolean isAreaPlayable(int index, boolean filter, boolean isRow)
    {
        int[] values = new int[SIZE];

        for (int i = 0; i < SIZE; i++)
            values[i] = isRow ? board[index][i].getNumber() : board[i][index].getNumber();

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

    private boolean isGridPlayable(int gridRow, int gridCol, boolean filter)
    {
        int[] values = new int[SIZE];
        int index = 0;

        for (int row = 0; row < GRID_SIZE; row++)
            for (int col = 0; col < GRID_SIZE; col++)
                values[index++] = board[gridRow * GRID_SIZE + row][gridCol * GRID_SIZE + col].getNumber();

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

    /**
     * Gives the user a hint by setting a correct tile to an unsolved tile
     */
    public void giveHint()
    {
        if (hasWon()) {
            return;
        }
        List<int[]> emptyTiles = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col].getNumber() == 0) {
                    emptyTiles.add(new int[]{row, col});
                }
            }
        }
        if (emptyTiles.isEmpty()) {
            return;
        }
        Collections.shuffle(emptyTiles);
        for (int[] position : emptyTiles) {
            int row = position[0];
            int col = position[1];
            int correctValue = boardSolution[row][col].getNumber();
            if (isPlacementValid(correctValue, row, col)) {
                setTile(board[row][col], correctValue);
                return;
            }
        }
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

        for (int num : getRandomizedNumbers()) {
            if (isPlacementValid(num, row, col)) {
                board[row][col].setNumber(num);
                if (fillBoard(row, col + 1)) return true;
                board[row][col].setNumber(0);
            }
        }
        return false;
    }

    /**
     * Generates an array of randomized numbers from 1 to 9
     * @return An array of numbers in random order
     */
    private int[] getRandomizedNumbers()
    {
        return new Random().ints(1, 10).distinct().limit(9).toArray();
    }

    /**
     * Checks if placing a number at a specific position is valid
     * @param num The number to place
     * @param row The row index
     * @param col The column index
     * @return True if the placement is valid, false otherwise
     */
    private boolean isPlacementValid(int num, int row, int col)
    {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i].getNumber() == num || board[i][col].getNumber() == num) {
                return false;
            }
        }

        int GridRowStart = (row / GRID_SIZE) * GRID_SIZE;
        int GridColStart = (col / GRID_SIZE) * GRID_SIZE;
        for (int i = 0; i < GRID_SIZE; i++)
            for (int j = 0; j < GRID_SIZE; j++)
                if (board[GridRowStart + i][GridColStart + j].getNumber() == num)
                    return false;
        return true;
    }

    /**
     * Removes a specified number of tiles from the board by setting them to zero
     * @param count The number of tiles to remove
     */
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
            } while (board[row][col].getNumber() == 0);
            board[row][col].setNumber(0);
        }
    }

    /**
     * A Sudoku solver that solves by backtracking
     * @param row Initial row
     * @param col Initial column
     * @return Has the board been solved?
     */
    private boolean solutionBacktrackMethod(int row, int col)
    {
        if (row == SIZE)
            return true;
        if (col == SIZE)
            return solutionBacktrackMethod(row + 1, 0);
        if (board[row][col].getNumber() != 0)
            return solutionBacktrackMethod(row, col + 1);

        for (int num = 1; num <= SIZE; num++)
        {
            if (!isPlacementValid(num, row, col)) continue;
            boardSolution[row][col].setNumber(num);
            if (solutionBacktrackMethod(row, col + 1)) return true;
            boardSolution[row][col].setNumber(0);
        }
        return false;
    }
}
