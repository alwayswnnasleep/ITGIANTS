package org.example.javafx_flexmusic.models;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.Objects;


public class MediaPlayerManager {

    private final MediaPlayer mediaPlayer;
    private Media media;
    private MediaView mediaView;
    private final ImageView playPauseIcon;
    private final Label currentTime;


    public MediaPlayerManager(Slider timeSlider, Slider volumeSlider, ImageView playPauseIcon, Label currentTime) {
        media = new Media("https://www.dropbox.com/scl/fi/r11w627ovtlkptkkqq44n/.mp3?rlkey=cont73wv15ns8rsygoywh9p4x&st=5q6tz24q&dl=1");
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        this.playPauseIcon = playPauseIcon;
        this.currentTime = currentTime;

        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(0.5);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });

        // Инициализация слайдера времени
        mediaPlayer.setOnReady(() -> {
            timeSlider.setMin(0);
            timeSlider.setMax(mediaPlayer.getTotalDuration().toMillis());
        });

        final boolean[] isSeeking = {false};
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSeeking[0]) {
                timeSlider.setValue(newValue.toMillis());
                this.currentTime.setText(formatDuration(newValue));
            }
        });

        timeSlider.setOnMousePressed(event -> isSeeking[0] = true);
        timeSlider.setOnMouseReleased(event -> {
            isSeeking[0] = false;
            mediaPlayer.seek(javafx.util.Duration.millis(timeSlider.getValue()));
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

    private String formatDuration(javafx.util.Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}