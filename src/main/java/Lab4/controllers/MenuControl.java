package Lab4.controllers;

import javafx.stage.Stage;
import Lab4.models.Difficulty;
import Lab4.models.MenuModel;

public class MenuControl
{
    private final MenuModel menuModel;
    private final SudokuControl sudokuControl;
    private final Stage stage;

    public MenuControl(MenuModel menuModel, SudokuControl sudokuControl, Stage stage)
    {
        this.menuModel = menuModel;
        this.sudokuControl = sudokuControl;
        this.stage = stage;
    }

    /**
     * Gets an informational string
     * @return The help string
     */
    public String help() { return menuModel.help(); }

    /**
     * Creates a board
     * @param difficulty The difficulty of the board
     */
    public void newGame(Difficulty difficulty) { sudokuControl.newGame(difficulty); }

    /**
     * Saves the board
     */
    public void saveGame() throws Exception { menuModel.saveGame(stage, sudokuControl.getBoard()); }

    /**
     * Loads a board
     */
    public void loadGame() throws Exception
    {
        int[][] board = menuModel.loadGame(stage);
        sudokuControl.setBoard(board);
    }

    /**
     * Is this board playable?
     * @return The playability of the board
     */
    public boolean isBoardPlayable() { return sudokuControl.isPlayable(); }

    /**
     * Clears all moves by user
     */
    public void clearMoves() { sudokuControl.clearMoves(); }
}
