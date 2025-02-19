module org.example.javafx_flexmusic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens org.example.javafx_flexmusic to javafx.fxml;
    exports org.example.javafx_flexmusic;
    exports org.example.javafx_flexmusic.client;
    opens org.example.javafx_flexmusic.client to javafx.fxml;
    exports org.example.javafx_flexmusic.models;
    opens org.example.javafx_flexmusic.models to javafx.fxml;
}