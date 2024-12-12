package Lab4;

import Lab4.controllers.MenuControl;
import Lab4.controllers.NumberInput;
import Lab4.controllers.PanelControl;
import Lab4.controllers.SudokuControl;
import Lab4.models.MenuModel;
import Lab4.models.SudokuModel;
import Lab4.views.MenuView;
import Lab4.views.NumberInputView;
import Lab4.views.PanelView;
import Lab4.views.SudokuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SudokuApp extends Application
{
    @Override
    public void start(Stage stage)
    {
        SudokuModel sudokuModel = new SudokuModel();
        MenuModel menuModel = new MenuModel();

        SudokuControl sudokuControl = new SudokuControl(sudokuModel);
        MenuControl menuControl = new MenuControl(menuModel, sudokuControl, stage);
        NumberInput numberInput = new NumberInput(sudokuControl);
        PanelControl panelControl = new PanelControl(sudokuControl);

        SudokuView sudokuView = new SudokuView(sudokuControl, stage);
        MenuView menuView = new MenuView(menuControl, stage);
        PanelView controlPanelView = new PanelView(panelControl);
        NumberInputView numberInputView = new NumberInputView(numberInput);

        BorderPane root = new BorderPane();
        root.setTop(menuView);
        root.setCenter(sudokuView);
        root.setLeft(controlPanelView);
        root.setRight(numberInputView);

        Scene scene = new Scene(root, 600, 700);
        stage.setTitle("Best Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    public static void main() {
        launch();
    }
}