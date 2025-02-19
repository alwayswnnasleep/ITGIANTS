package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.models.Track;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    @FXML
    private TableView<Track> table_tracks; // Убедитесь, что тип указан
    @FXML
    private TableColumn<Track, ?> column_play, column_track, column_artist, column_album, column_duration;
    @FXML
    private ImageView playPauseIcon;
    @FXML
    private Button button_play, button_profile;
    @FXML
    private Button arrow_back;
    @FXML
    private Pane pane_volume_slider;
    @FXML
    private Slider slider_time; // Слайдер времени
    @FXML
    private Slider slider_volume; // Слайдер громкости
    @FXML
    private Label currentTimeLabel; // Метка для текущего времени

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        media = new Media("https://www.dropbox.com/scl/fi/evzt9ogogqu1fm7y0vnxx/0c904e9a-6647-477a-b94e-33f7e3405da7.mp3?rlkey=vl8z0eb3b1mxzrdxmwbkse72z&st=smfxysf7&dl=1");
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        // Инициализация слайдера громкости
        slider_volume.setMin(0);
        slider_volume.setMax(1);
        slider_volume.setValue(0.5); // Установить начальное значение

        slider_volume.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });

        // Инициализация слайдера времени
        mediaPlayer.setOnReady(() -> {
            slider_time.setMin(0);
            slider_time.setMax(mediaPlayer.getTotalDuration().toMillis());
        });

        final boolean[] isSeeking = {false};
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSeeking[0]) {
                slider_time.setValue(newValue.toMillis());
                currentTimeLabel.setText(formatDuration(newValue));
            }
        });

        slider_time.setOnMousePressed(event -> isSeeking[0] = true);
        slider_time.setOnMouseReleased(event -> {
            isSeeking[0] = false;
            mediaPlayer.seek(javafx.util.Duration.millis(slider_time.getValue()));
        });

        // Add the listener for the tableView skin property
        table_tracks.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            final TableHeaderRow header = (TableHeaderRow) table_tracks.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((o, oldVal, newVal) -> header.setReordering(false));
        });
    }

    @FXML
    private void playMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playPauseIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/javafx_flexmusic/images/icon-play.png"))));
            } else {
                mediaPlayer.play();
                playPauseIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/javafx_flexmusic/images/icon-pause.png"))));
            }
        }
    }

    @FXML
    private void setVisibleVolumePanel() {
        pane_volume_slider.setVisible(!pane_volume_slider.isVisible());
    }

    @FXML
    private void handleProfileButtonClick() {
        switchScene("/org/example/javafx_flexmusic/RegistrationForm.fxml");
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newSceneRoot = loader.load();
            Scene newScene = new Scene(newSceneRoot);

            Stage stage = (Stage) button_profile.getScene().getWindow(); // Получаем текущий Stage
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для форматирования времени
    private String formatDuration(javafx.util.Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}