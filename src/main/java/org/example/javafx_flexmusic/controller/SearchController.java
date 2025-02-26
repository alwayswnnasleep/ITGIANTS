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
            loadTracks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTracks() {
        List<Track> hardcodedTracks = Arrays.asList(
                new Track(1, 1, "3:30", "Song 1", "http://example.com/song1", "Genre 1", "Artist 1"),
                new Track(2, 1, "4:15", "Song 2", "http://example.com/song2", "Genre 2", "Artist 2"),
                new Track(3, 1, "2:45", "Song 3", "http://example.com/song3", "Genre 3", "Artist 3")
        );

        // Добавляем треки в список
        trackList.clear();
        trackList.addAll(hardcodedTracks);
    }


//    private void loadTracks() {
//        Task<List<Track>> task = new Task<List<Track>>() {
//            @Override
//            protected List<Track> call() throws Exception {
//                Client client = new Client();
//                return client.getAllTracks();
//            }
//
//            @Override
//            protected void succeeded() {
//                trackList.clear();
//                trackList.addAll(getValue());
//            }
//
//            @Override
//            protected void failed() {
//                Throwable exception = getException();
//                exception.printStackTrace();
//            }
//        };
//        new Thread(task).start();
//    }

    @FXML
    private void handleRefreshIconClick() {
        loadTracks();
    }
}