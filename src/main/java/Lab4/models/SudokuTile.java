package Lab4.models;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.Serializable;

public class SudokuTile extends Label implements Serializable
{
    private int value;

    SudokuTile()
    {
        setFont(Font.font(20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
        setPrefSize(40, 40);
    }

    /**
     * Values to bind the label size to
     * @param w The width
     * @param h The height
     */
    public void bind(ObservableValue w, ObservableValue h)
    {
        prefWidthProperty().bind(w);
        prefHeightProperty().bind(h);
    }

    /**
     * Set the label internal value
     * @param value
     */
    public void setValue(int value)
    {
        this.value = value;
        setText(value == 0 ? "" : String.valueOf(value));
    }

    /**
     * Gets the internal label value
     * @return The label value
     */
    public int getValue() {
        return value;
    }
}