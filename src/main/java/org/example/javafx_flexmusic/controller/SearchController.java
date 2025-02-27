package org.example.javafx_flexmusic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.example.javafx_flexmusic.client.Client;
import org.example.javafx_flexmusic.db.entity.Track;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    private ObservableList<Track> trackList = FXCollections.observableArrayList();

    @FXML
    private BorderPane border_pane;
    @FXML
    private TableView<Track> table_tracks;
    @FXML
    private TableColumn<Track, String> column_track, column_artist, column_genre, column_duration;
    @FXML
    private ImageView refresh_icon;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        try {

            column_track.setCellValueFactory(new PropertyValueFactory<>("title"));
            column_artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
            column_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            column_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

            table_tracks.setItems(trackList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}