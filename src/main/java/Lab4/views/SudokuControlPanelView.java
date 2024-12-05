package Lab4.views;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import Lab4.controllers.SudokuControlPanelController;

public class SudokuControlPanelView extends VBox
{
    public SudokuControlPanelView(SudokuControlPanelController controller)
    {
        setAlignment(Pos.CENTER);
        setStyle("-fx-padding: 15px;");

        Button check = new Button("Check");
        check.setOnAction(_ -> {
            if (controller.isBoardPlayable())
                Notification.notify(Alert.AlertType.INFORMATION, "Hint", "Your board looks good so far!");
            else Notification.notify(Alert.AlertType.ERROR, "Hint", "Your board is not correct.");
        });

        Button hint = new Button("Hint");
        hint.setOnAction(_ -> controller.giveHint());

        getChildren().addAll(check, hint);
    }
}
