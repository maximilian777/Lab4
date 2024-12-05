package Lab4.controllers;

public class NumberInput
{
    private final SudokuControl sudokuControl;

    public NumberInput(SudokuControl sudokuControl)
    {
        this.sudokuControl = sudokuControl;
    }

    public void setValue(int value)
    {
        sudokuControl.setValue(value);
    }
}
