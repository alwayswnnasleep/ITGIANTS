package org.example.javafx_flexmusic.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MusicPlayer {

    private Media currentMedia;
    private MediaPlayer player;
    private MediaView mediaView;
    private Track currentTrack;


    // Инициализация
    public MusicPlayer(Track track) {
        if (track != null) {
            currentTrack = track;
            String url = currentTrack.getUrl();
            currentMedia = new Media(url);
            player = new MediaPlayer(currentMedia);
            mediaView = new MediaView(player);
        }
    }


    // Запустить трек
    public void play() {
        player.play();
    }

    // Музыкальная пауза
    public void pause() {
        player.pause();
    }

    // Установка новой громкости
    public void setVolume(double v) {
        player.setVolume(v);
    }

    // Возвращает продолжительность трека (можно перевести в мс)
    public Duration getTotalDuration() {
        return player.getTotalDuration();
    }

    // Перемотка трека к указанному времени
    public void seek(Duration duration) {
        player.seek(duration);
    }
}
