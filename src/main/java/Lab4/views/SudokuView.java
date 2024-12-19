package Lab4.views;

import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import Lab4.controllers.SudokuControl;
import Lab4.models.Tile;

public class SudokuView extends GridPane {
    private final SudokuControl controller;
    private final Stage stage;

    public SudokuView(SudokuControl controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        drawBoard();
    }

    private void drawBoard() {
        setStyle("-fx-padding: 15px;");

        int SECTIONS_PER_ROW = 3;
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                GridPane section = createSection();

                int SECTION_SIZE = 3;
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        Tile tile = controller.getTileAt(srow * SECTION_SIZE + row, scol * SECTION_SIZE + col);
                        Label tileLabel = createTileLabel(tile);

                        tileLabel.setOnMouseClicked(event -> handleTileClick(tile));
                        section.add(tileLabel, col, row);
                    }
                }
                add(section, scol, srow);
            }
        }
    }

    private GridPane createSection() {
        GridPane section = new GridPane();
        section.prefWidthProperty().bind(stage.widthProperty().divide(3));
        section.prefHeightProperty().bind(stage.heightProperty().divide(3));
        section.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        return section;
    }

    private Label createTileLabel(Tile tile) {
        Label label = new Label();
        label.setFont(Font.font("monospace", 24));
        label.setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-alignment: center;");
        label.prefWidthProperty().bind(this.widthProperty().divide(9));
        label.prefHeightProperty().bind(this.heightProperty().divide(9));
        label.textProperty().bind(tile.numberProperty().asString().map(num -> num.equals("0") ? "" : num));

        tile.immutableProperty().addListener((obs, wasImmutable, isImmutable) -> {
            if (isImmutable) {
                label.setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-alignment: center;");
            } else {
                label.setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-alignment: center;");
            }
        });

        return label;
    }

    private void handleTileClick(Tile tile) {
        if (tile.isImmutable()) {
            return;
        }

        if (controller.setTile(tile)) {
            if (controller.hasWon()) {
                notifyUser(Alert.AlertType.INFORMATION, "Results", "You won!");
            } else if (!controller.isPlayable() && controller.isGameOver()) {
                notifyUser(Alert.AlertType.ERROR, "Results", "You lost. :(");
            }
        }
    }

    private void notifyUser(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
