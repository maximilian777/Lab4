package Lab4.views;

import javafx.scene.control.*;
import javafx.stage.Stage;

import Lab4.controllers.MenuControl;
import Lab4.models.Difficulty;

public class MenuView extends MenuBar
{
    public MenuView(MenuControl controller, Stage stage)
    {
        Menu file = new Menu("File");
        Menu game = new Menu("Game");
        Menu help = new Menu("Help");

        MenuItem load = new MenuItem("Load game");
        MenuItem save = new MenuItem("Save game");
        MenuItem exit = new MenuItem("Exit");

        load.setOnAction(_ -> {
            try {
                controller.loadGame();
            } catch (Exception _) {
                Notification.notify(Alert.AlertType.ERROR, "Load save game", "The save game could not be loaded.");
            }
        });
        save.setOnAction(_ -> {
            try {
                controller.saveGame();
            } catch (Exception _) {
                Notification.notify(Alert.AlertType.ERROR, "Save save game", "The save game could not be saved.");
            }
        });
        exit.setOnAction(_ -> stage.close());

        file.getItems().addAll(load, save, exit);

        MenuItem newGame = new MenuItem("New game");
        Menu difficulty = new Menu("Select difficulty");

        ToggleGroup diffs = new ToggleGroup();
        RadioMenuItem easy  = new RadioMenuItem("Easy");
        RadioMenuItem medium = new RadioMenuItem("Medium");
        RadioMenuItem hard = new RadioMenuItem("Hard");

        easy.setToggleGroup(diffs);
        easy.setSelected(true);
        medium.setToggleGroup(diffs);
        hard.setToggleGroup(diffs);
        newGame.setOnAction(_ -> controller.newGame(getSelectedDifficulty(diffs)));

        difficulty.getItems().addAll(easy, medium, hard);
        game.getItems().addAll(newGame, difficulty);

        MenuItem clear = new MenuItem("Clear");
        MenuItem validBoard = new MenuItem("Is the board valid?");
        MenuItem instructions = new MenuItem("Instructions");

        clear.setOnAction(_ -> controller.clearMoves());
        validBoard.setOnAction(_ -> {
            if (controller.isBoardCorrect())
                Notification.notify(Alert.AlertType.INFORMATION, "Hint", "Your board looks good so far!");
            else Notification.notify(Alert.AlertType.ERROR, "Hint", "Your board is not correct.");
        });
        instructions.setOnAction(_ -> Notification.notify(Alert.AlertType.INFORMATION, "Instruktioner", controller.help()));

        help.getItems().addAll(clear, validBoard, instructions);

        getMenus().addAll(file, game, help);
    }

    private Difficulty getSelectedDifficulty(ToggleGroup group)
    {
        RadioMenuItem selectedItem = (RadioMenuItem)group.getSelectedToggle();
        return switch (selectedItem.getText()) {
            case "Easy" -> Difficulty.EASY;
            case "Medium" -> Difficulty.MEDIUM;
            case "Hard" -> Difficulty.HARD;
            default -> null;
        };
    }
}
