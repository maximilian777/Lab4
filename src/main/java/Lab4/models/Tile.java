package Lab4.models;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.Serializable;

public class Tile extends Label implements Serializable
{
    private int number;

    Tile()
    {
        setFont(Font.font(20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
        setPrefSize(40, 40);
    }

    /**
     * Label size values
     * @param width The width
     * @param height The height
     */
    public void bind(ObservableValue width, ObservableValue height)
    {
        prefWidthProperty().bind(width);
        prefHeightProperty().bind(height);
    }

    public void setNumber(int num)
    {
        this.number = num;
        setText(number == 0 ? "" : String.valueOf(number));
    }

    public int getNumber() {
        return number;
    }
}