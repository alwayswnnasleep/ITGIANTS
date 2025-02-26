package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.javafx_flexmusic.db.entity.Track;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private BorderPane border_pane;
    @FXML
    private TableColumn<Track, String> column_track, column_artist;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        try {
            column_track.setCellValueFactory(new PropertyValueFactory<>("title"));
            column_artist.setCellValueFactory(new PropertyValueFactory<>("artist"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
