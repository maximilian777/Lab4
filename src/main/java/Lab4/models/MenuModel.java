package Lab4.models;

import Lab4.utils.FileManager;

import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.util.ArrayList;
import java.io.File;
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
    public void saveGame(Stage stage, Tile[][] sudokuBoard) throws Exception
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku save file", "*.sudoku"));

        File file = fileChooser.showSaveDialog(stage);

        if (file == null)
            return;
        List<int[]> x = new ArrayList<>();
        for (Tile[] row : sudokuBoard) {
            int[] rowValues = new int[row.length];
            for (int i = 0; i < row.length; i++) {
                rowValues[i] = row[i].getNumber();
            }
            x.add(rowValues);
        }

        FileManager.saveBoard(x.toArray(new int[0][]), file.getAbsolutePath());
    }

    /**
     * Opens a file dialog to allow the user to load a saved Sudoku game from a file
     * @param stage Stage used to display the file chooser dialog.
     * @return A 2D integer array representing the loaded Sudoku board
     */
    public int[][] loadGame(Stage stage) throws Exception
    {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku save file (*.sudoku)", "*.sudoku"));
        File file = fileChooser.showOpenDialog(stage);
        if (file == null)
            return null;
        return FileManager.loadBoard(file.getAbsolutePath());
    }
}
