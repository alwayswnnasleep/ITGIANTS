package org.example.javafx_flexmusic.models;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class MediaPlayerSingleton {
    private static MediaPlayer mediaPlayer;

    private MediaPlayerSingleton() { }

    public static MediaPlayer getInstance(Media media) {
        if (mediaPlayer == null) {
            synchronized (MediaPlayerSingleton.class) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer(media);
                }
            }
        }
        return mediaPlayer;
    }

    public static MediaPlayer getCurrentPlayer() {
        return mediaPlayer;
    }

    public static void resetInstance() {
        synchronized (MediaPlayerSingleton.class) {
            mediaPlayer = null;
        }
    }
}