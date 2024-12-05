package Lab4.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import Lab4.controllers.NumberInput;

public class NumberInputView extends VBox
{
    public NumberInputView(NumberInput numberInput)
    {
        setAlignment(Pos.CENTER);
        setStyle("-fx-padding: 15px;");

        Button one = new Button("1");
        one.setOnAction(_ ->  numberInput.setValue(1));

        Button two = new Button("2");
        two.setOnAction(_ -> numberInput.setValue(2));

        Button three = new Button("3");
        three.setOnAction(_ -> numberInput.setValue(3));

        Button four = new Button("4");
        four.setOnAction(_ -> numberInput.setValue(4));

        Button five = new Button("5");
        five.setOnAction(_ -> numberInput.setValue(5));

        Button six = new Button("6");
        six.setOnAction(_ -> numberInput.setValue(6));

        Button seven = new Button("7");
        seven.setOnAction(_ -> numberInput.setValue(7));

        Button eight = new Button("8");
        eight.setOnAction(_ -> numberInput.setValue(8));

        Button nine = new Button("9");
        nine.setOnAction(_ -> numberInput.setValue(9));

        Button clear = new Button("C");
        clear.setOnAction(_ -> numberInput.setValue(0));

        getChildren().addAll(one, two, three, four, five, six, seven, eight, nine, clear);
    }
}
