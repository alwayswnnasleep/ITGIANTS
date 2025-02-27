package org.example.javafx_flexmusic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import org.example.javafx_flexmusic.client.Client;
import org.example.javafx_flexmusic.db.entity.Track;
import org.w3c.dom.ls.LSOutput;

import java.net.URL;
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
    private ImageView refresh_icon, search_icon;
    @FXML
    private TextField search_field;

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
        Task<List<Track>> task = new Task<List<Track>>() {
            @Override
            protected List<Track> call() throws Exception {
                Client client = new Client();
                return client.getAllTracks();
            }

            @Override
            protected void succeeded() {
                trackList.clear();
                trackList.addAll(getValue());
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                exception.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    private void handleRefreshIconClick() {
        loadTracks();
    }

    @FXML
    private void handleTableViewClick() {
        Track selectedTrack = table_tracks.getSelectionModel().getSelectedItem();
        if (selectedTrack != null) {
            System.out.println("Выбран трек: " + selectedTrack.getTitle());
        }
    }

    @FXML
    public void handleSearchIconClick() {
        String currentText = search_field.getText();
        if(currentText.isEmpty()) {
            loadTracks();
            return;
        }
        Task<List<Track>> searchTask = new Task<List<Track>>() {
            @Override
            protected List<Track> call() throws Exception {
                Client client = new Client();
                return client.searchTracks(currentText);
            }

            @Override
            protected void succeeded() {
                trackList.clear();
                trackList.addAll(getValue());
            }

            @Override
            protected void failed() {
                Throwable exception = getException();
                exception.printStackTrace();
            }
        };
        new Thread(searchTask).start();
        System.out.println("Текущий текст: " + currentText);
    }
}