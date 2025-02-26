package org.example.javafx_flexmusic.controller;

import com.mpatric.mp3agic.Mp3File;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.example.javafx_flexmusic.client.Client;
import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.FileSelector;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UploadTrackController implements Initializable {

    private List<String> genreList = Arrays.asList("Hip-Hop", "Pop", "Rock", "Jazz", "Classical", "Electronic", "Country");
    private File file;
    private Track track;

    @FXML
    ProgressIndicator progress_indicator;

    @FXML
    private ComboBox<String> genre_comboBox;

    @FXML
    private Button select_file_button;

    @FXML
    private TextField title_track_field;

    @FXML
    private Button upload_button;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        genre_comboBox.setItems(FXCollections.observableArrayList(genreList));
    }

    @FXML
    private void handleUploadButtonClick() throws Exception {
        track = new Track();
        if (title_track_field.getText().isEmpty() || file == null || genre_comboBox.getValue() == null) {
            return;
        }

        track.setUser_id(UserSession.getInstance().getCurrentUser().getId());
        track.setTitle(title_track_field.getText());
        track.setArtist(UserSession.getInstance().getCurrentUser().getUsername());
        track.setGenre(genre_comboBox.getValue());
        int lengthInSeconds = (int) new Mp3File(file).getLengthInSeconds();
        String duration = String.format("%02d:%02d", lengthInSeconds / 60, (lengthInSeconds % 60)-1);
        track.setDuration(duration);

        Task<Void> uploadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Client client = new Client();
                client.postTrack(track, file);
                return null;
            }
        };

        progress_indicator.setVisible(true);
        uploadTask.setOnSucceeded(event -> {
            progress_indicator.setVisible(false);
            clearFields();
        });
        new Thread(uploadTask).start();
    }

    private String formatDuration(Duration duration) {
        long minutes = (long) duration.toMinutes();
        long seconds = (long) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    public void handleSelectFileButtonClick() {
        file = FileSelector.selectMp3File();
        if(file != null) {
           select_file_button.setText(file.getName());
        }
    }


    private void clearFields() {
    title_track_field.clear();
    genre_comboBox.setValue(null);
    genre_comboBox.setPromptText("Жанр");
    file = null;
    select_file_button.setText("Выбрать файл");
    }
}