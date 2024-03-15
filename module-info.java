module oop_final {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens application to javafx.fxml;
    exports application to javafx.graphics;
}
