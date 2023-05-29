module com.hospital {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.controlsfx.controls;
    requires java.sql;

    opens com.hospital to javafx.base,javafx.fxml,javafx.graphics;
}