package Lab4.views;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import Lab4.controllers.PanelControl;

public class PanelView extends VBox
{
    public PanelView(PanelControl controller)
    {
        setAlignment(Pos.CENTER);
        setStyle("-fx-padding: 15px;");
        Button check = new Button("Check");
        check.setOnAction(_ -> {
            if (controller.isBoardPlayable())
                Notification.notify(Alert.AlertType.INFORMATION, "Hint", "The board is playable");
            else Notification.notify(Alert.AlertType.ERROR, "Hint", "The board is not playable");
        });

        Button hint = new Button("Hint");
        hint.setOnAction(_ -> controller.giveHint());

        getChildren().addAll(check, hint);
    }
}
