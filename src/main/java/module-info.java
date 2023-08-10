module com.example.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.proyecto to javafx.fxml;
    exports com.example.proyecto;
    exports vista;
}