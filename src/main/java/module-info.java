module jdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.sql;

    opens jdbc to javafx.fxml;

    exports jdbc;
}
