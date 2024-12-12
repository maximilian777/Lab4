package Lab4.views;

import javafx.scene.control.Alert;

public class Notification
{
    public static void notify(Alert.AlertType alertType, String header, String content)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle("Best Sudoku");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
