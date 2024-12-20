package Lab4.models;

import java.util.*;
import java.util.stream.IntStream;

public class SudokuModel
{
    private final int SIZE = 9;
    private final int GRID_SIZE = 3;
    private boolean initialization = false;

    private final Tile[][] board;
    private final Set<Tile> presetMoves = new HashSet<>();
    private final Set<Tile> previousMoves = new HashSet<>();

    public SudokuModel()
    {
        this.board = new Tile[SIZE][SIZE];
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

    public void setBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j].setNumber(board[i][j]);
                boolean isPreset = board[i][j] != 0;
                this.board[i][j].setImmutable(isPreset);
                if (isPreset) {
                    presetMoves.add(this.board[i][j]);
                }
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
    public void newGame(Difficulty difficulty) {
        if (!initialization) {
            IntStream.range(0, SIZE).forEach(i ->
                    IntStream.range(0, SIZE).forEach(j -> {
                        this.board[i][j] = new Tile();
                    })
            );
            initialization = true;
        }
        resetImmutability();
        IntStream.range(0, SIZE).forEach(i ->
                IntStream.range(0, SIZE).forEach(j -> {
                    this.board[i][j].setNumber(0);
                })
        );
        fillBoard(0, 0);
        int tilesToRemove = removeTilesByDifficulty(difficulty);
        removeNumbers(tilesToRemove);
        markPresetTilesAsImmutable();
    }

    /**
     * Marks all preset tiles as immutable
     */
    private void markPresetTilesAsImmutable() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col].getNumber() != 0) {
                    board[row][col].setImmutable(true);
                }
            }
        }
    }

    /**
     * Resets immutability for all tiles
     */
    private void resetImmutability() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col].setImmutable(false);
            }
        }
    }

    /**
     * Determines the number of tiles to be removed based on the selected difficulty level
     * @param difficulty The selected difficulty level.
     * @return The number of tiles to be removed based on the given difficulty.
     * @throws IllegalArgumentException if the difficulty level is unknown.
     */
    private int removeTilesByDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 25;
            case MEDIUM -> 45;
            case HARD -> 65;
            default -> throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        };
    }

    /**
     * Has the game been finished?
     * @return Game has been finished
     */
    public boolean hasWon() {
        return isBoardPlayable(false) && isGameOver();
    }
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
            System.out.println("Row playable");
            if (!isAreaPlayable(i, partial, false))
                return false;
            System.out.println("Col playable");
        }

        for (int gridRow = 0; gridRow < GRID_SIZE; gridRow++)
            for (int gridCol = 0; gridCol < GRID_SIZE; gridCol++)
                if (!isGridPlayable(gridRow, gridCol, partial))
                    return false;
        return true;
    }

    /**
     * Checks if a row or column is playable
     * @param index Row or column
     * @param filter Check for playability or for a win
     * @param isRow Checks a row or column
     * @return Playability of the region
     */
    private boolean isAreaPlayable(int index, boolean filter, boolean isRow)
    {
        int[] numbers = new int[SIZE];

        for (int i = 0; i < SIZE; i++)
            numbers[i] = isRow ? board[index][i].getNumber() : board[i][index].getNumber();

        Arrays.sort(numbers);

        if (filter) {
            Set<Integer> uniqueValues = new HashSet<>();
            for (int value : numbers) {
                if (value != 0 && !uniqueValues.add(value)) {
                    return false;
                }
            }
            return true;
        }
        Arrays.sort(numbers);
        return Arrays.equals(numbers, IntStream.rangeClosed(1, 9).toArray());
    }

    private boolean isGridPlayable(int gridRow, int gridCol, boolean filter)
    {
        int[] numbers = new int[SIZE];
        int index = 0;

        for (int row = 0; row < GRID_SIZE; row++)
            for (int col = 0; col < GRID_SIZE; col++)
                numbers[index++] = board[gridRow * GRID_SIZE + row][gridCol * GRID_SIZE + col].getNumber();

        Arrays.sort(numbers);

        if (filter) {
            Set<Integer> uniqueNumbers = new HashSet<>();
            for (int number : numbers) {
                if (number != 0 && !uniqueNumbers.add(number)) {
                    return false;
                }
            }
            return true;
        }
        Arrays.sort(numbers);
        return Arrays.equals(numbers, IntStream.rangeClosed(1, 9).toArray());
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
            for (int num = 1; num <= SIZE; num++) {
                if (isPlacementValid(num, row, col)) {
                    setTile(board[row][col], num);
                    return;
                }
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
    private boolean solutionBacktrackMethod(int row, int col) {
        if (row == SIZE)
            return true;
        if (col == SIZE)
            return solutionBacktrackMethod(row + 1, 0);
        if (board[row][col].getNumber() != 0)
            return solutionBacktrackMethod(row, col + 1);

        for (int num = 1; num <= SIZE; num++) {
            if (!isPlacementValid(num, row, col)) continue;
            board[row][col].setNumber(num);
            if (solutionBacktrackMethod(row, col + 1)) return true;
            board[row][col].setNumber(0);
        }
        return false;
    }
}
