package Lab4.controllers;

public class SudokuControlPanelController
{
    private final SudokuControl sudokuControl;

    public SudokuControlPanelController(SudokuControl sudokuControl) {
        this.sudokuControl = sudokuControl;
    }

    /**
     * Is the board still playable?
     * @return Playability of the board
     */
    public boolean isBoardPlayable() {
        return sudokuControl.isValid();
    }

    /**
     * Gives a hint to the user, by inserting a correct answer to a tile.
     */
    public void giveHint() { sudokuControl.giveHint(); }
}
