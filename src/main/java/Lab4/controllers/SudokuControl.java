package Lab4.controllers;

import Lab4.models.Difficulty;
import Lab4.models.SudokuModel;
import Lab4.models.SudokuTile;

public class SudokuControl
{
    private final SudokuModel sudokuModel;
    private SudokuTile tile;

    public SudokuControl(SudokuModel sudokuModel)
    {
        this.sudokuModel = sudokuModel;
    }

    /**
     * Sets a number to the highlighted tile
     * @param tile The highlighted tile
     * @return Can you set the tile?
     */
    public boolean setTile(SudokuTile tile)
    {
        if (sudokuModel.isGameOver())
            return false;
        this.tile = tile;
        return true;
    }

    /**
     * Return a tile at a specified row and column
     * @param row The row
     * @param col The column
     * @return The tile at the row and column
     */
    public SudokuTile getTileAt(int row, int col) { return sudokuModel.getTile(row, col); }

    public void setValue(int value)
    {
        if (tile != null)
            sudokuModel.setTileObject(tile, value);
        tile = null;
    }


    SudokuTile[][] getSudokuBoard() { return sudokuModel.getSudokuBoard(); }

    void setSudokuBoard(int[][] sudokuBoard) { sudokuModel.setSudokuBoard(sudokuBoard); }

    void newGame(Difficulty difficulty) { sudokuModel.newGame(difficulty); }

    /**
     * Is the board playable?
     * @return Playability of the board
     */
    public boolean isValid() { return sudokuModel.isValid(); }

    /**
     * Clears all the users' previous moves
     */
    public void clearMoves() {
        sudokuModel.clearMoves();
    }

    /**
     * Has the board won?
     * @return a Win
     */
    public boolean hasWon() {
        return sudokuModel.hasWon();
    }

    /**
     * Gives a hint to the player, by inserting a correct answer to a tile.
     */
    public void giveHint() { sudokuModel.giveHint(); }
}