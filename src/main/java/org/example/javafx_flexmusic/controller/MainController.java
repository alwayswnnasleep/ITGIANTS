package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.tools.SceneSwitcher;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    @FXML
    private TableView<Track> table_tracks;
    @FXML
    private TableColumn<Track, Void> column_play; // Колонка для кнопки Play
    @FXML
    private TableColumn<Track, String> column_track, column_artist, column_album, column_duration; // Остальные колонки
    @FXML
    private ImageView playPauseIcon;
    @FXML
    private Button button_play, button_profile, button_radio;
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
        // Настройка колонок

        column_track.setCellValueFactory(new PropertyValueFactory<>("title"));
        column_artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
//      column_album.setCellValueFactory(new PropertyValueFactory<>("album"));
//      column_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        media = new Media("https://www.dropbox.com/scl/fi/r11w627ovtlkptkkqq44n/.mp3?rlkey=cont73wv15ns8rsygoywh9p4x&st=5q6tz24q&dl=1");
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        slider_volume.setMin(0);
        slider_volume.setMax(1);
        slider_volume.setValue(0.5);

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
        SceneSwitcher.switchScene((Stage)button_profile.getScene().getWindow(),"/org/example/javafx_flexmusic/RegistrationScene.fxml" );
    }

    private String formatDuration(javafx.util.Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}