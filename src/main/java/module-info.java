module Lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens Lab4 to javafx.fxml;
    exports Lab4;
    exports Lab4.views;
    opens Lab4.views to javafx.fxml;
}