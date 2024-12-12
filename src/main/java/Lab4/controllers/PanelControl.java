package Lab4.controllers;

public class PanelControl
{
    private final SudokuControl sudokuControl;

    public PanelControl(SudokuControl sudokuControl) {
        this.sudokuControl = sudokuControl;
    }

    /**
     * Is the board still playable?
     * @return Playability of the board
     */
    public boolean isBoardPlayable() {
        return sudokuControl.isPlayable();
    }

    /**
     * Gives a hint to the user, by inserting a correct answer to a tile.
     */
    public void giveHint() { sudokuControl.giveHint(); }
}
