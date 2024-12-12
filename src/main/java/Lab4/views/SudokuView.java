package Lab4.views;

import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import Lab4.controllers.SudokuControl;
import Lab4.models.Tile;

public class SudokuView extends GridPane
{
    private final SudokuControl controller;
    private final Stage stage;

    public SudokuView(SudokuControl controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        drawBoard();
    }

    private void drawBoard()
    {
        setStyle("-fx-padding: 15px;");

        int SECTIONS_PER_ROW = 3;
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++)
        {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++)
            {
                GridPane section = new GridPane();

                section.prefWidthProperty().bind(stage.widthProperty().divide(SECTIONS_PER_ROW));
                section.prefHeightProperty().bind(stage.heightProperty().divide(SECTIONS_PER_ROW));

                section.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                int SECTION_SIZE = 3;
                for (int row = 0; row < SECTION_SIZE; row++)
                {
                    for (int col = 0; col < SECTION_SIZE; col++)
                    {
                        var w = section.widthProperty().divide(SECTION_SIZE);
                        var h = section.heightProperty().divide(SECTION_SIZE);

                        Tile tile = controller.getTileAt(srow * SECTION_SIZE + row, scol * SECTION_SIZE + col);
                        tile.setFont(Font.font("monospace", 24));
                        tile.bind(w, h);
                        tile.setOnMouseClicked(_ -> {
                            if (controller.setTile(tile))
                                return;
                            if (controller.hasWon())
                                Notification.notify(Alert.AlertType.INFORMATION, "Results", "You won!");
                            else Notification.notify(Alert.AlertType.ERROR, "Results", "You lost. :(");
                        });

                        section.add(tile, col, row);
                    }
                }
                add(section, scol, srow);
            }
        }
    }
}
