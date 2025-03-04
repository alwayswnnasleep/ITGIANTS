package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.example.javafx_flexmusic.db.entity.Track;

import java.util.Objects;

public class MediaPlayerController  {

    private MediaPlayer mediaPlayer;
    private Media media;
    private MediaView mediaView;

    @FXML
    private Button play_button, next_button, prev_button, repeat_button, shuffle_button, volume_button;
    @FXML
    private Label current_time_label, title_label, artist_label;
    @FXML
    private Pane pane_volume_slider;
    @FXML
    private ImageView playPauseIcon;
    @FXML
    private Slider time_slider;
    @FXML
    private Slider volume_slider;

    public void setTrack(Track track) {
        if (track == null) {
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        String url = track.getUrl();
        media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        initializeTimeSlider();
        initializeVolumeSlider();
        initializeInformationLabel(track);
        togglePlayPause();
    }

    private void initializeInformationLabel(Track track) {
        title_label.setText(track.getTitle());
        artist_label.setText(track.getArtist());
    }

    @FXML
    private void initializeVolumeSlider() {
        volume_slider.setMin(0);
        volume_slider.setMax(1);
        volume_slider.setValue(0.5);
        volume_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });
    }

    private void initializeTimeSlider() {
        mediaPlayer.setOnReady(() -> {
            time_slider.setMin(0);
            time_slider.setMax(mediaPlayer.getTotalDuration().toSeconds());
        });

        final boolean[] isSeeking = {false};
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSeeking[0]) {
                time_slider.setValue(newValue.toSeconds());
                this.current_time_label.setText(formatDuration(newValue));
            }
        });

        time_slider.setOnMousePressed(event -> isSeeking[0] = true);
        time_slider.setOnMouseReleased(event -> {
            isSeeking[0] = false;
            mediaPlayer.seek(Duration.seconds(time_slider.getValue()));
        });
    }

    public void togglePlayPause() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playPauseIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/javafx_flexmusic/images/icon-play-gray.png"))));
        } else {
            mediaPlayer.play();
            playPauseIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/javafx_flexmusic/images/icon-pause-gray.png"))));
        }
    }

    @FXML
    private void handlePlayButtonClick() {
        togglePlayPause();
    }

    @FXML
    private void handleVolumeButtonClick() {
        pane_volume_slider.setVisible(!pane_volume_slider.isVisible());
    }

    public void stopMusic() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private String formatDuration(javafx.util.Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}