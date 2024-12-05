package Lab4.models;

import Lab4.utils.FileManager;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuModel
{
    /**
     * Gets an informational string
     * @return The help string
     */
    public String help()
    {
        return """
                Sudoku is a 9x9 grid based game, where the objective is to create a sequence of numbers from 1-9. The rules are as follows:
                1. Every digit must be vertically unique.
                2. Every digits must be horizontally unique.
                3. Every 3x3 cluster must have all the numbers in the sequence.
               """;
    }

    /**
     * Lets the user pick a path when opening a file (using file chooser), in which they can save their current game
     * @param stage The stage to open the file chooser to
     * @param sudokuBoard Board to Save
     */
    public void saveGame(Stage stage, SudokuTile[][] sudokuBoard) throws Exception
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku save file", "*.sudoku"));

        File file = fileChooser.showSaveDialog(stage);

        if (file == null)
            return;
        List<int[]> x = new ArrayList<>();
        for (SudokuTile[] row : sudokuBoard) {
            int[] rowValues = new int[row.length];
            for (int i = 0; i < row.length; i++) {
                rowValues[i] = row[i].getValue(); // Extract integer value from each SudokuTile
            }
            x.add(rowValues); // Add the row of integers to the list
        }

        FileManager.saveSudoku(x.toArray(new int[0][]), file.getAbsolutePath());
    }

    /**
     * Opens a file chooser and lets the user select a game to load
     * @param stage The stage to open the file chooser to
     * @return The board read from the file
     */
    public int[][] loadGame(Stage stage) throws Exception
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku save file (*.sudoku)", "*.sudoku"));

        File file = fileChooser.showOpenDialog(stage);

        if (file == null)
            return null;
        return FileManager.loadSudoku(file.getAbsolutePath());
    }
}
