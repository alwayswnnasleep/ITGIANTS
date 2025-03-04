package org.example.javafx_flexmusic.controller.manager;

import org.example.javafx_flexmusic.controller.MediaPlayerController;

public class ControllerManager {

    private static ControllerManager instance;
    private MediaPlayerController mediaPlayerController;

    private ControllerManager() {
    }

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    public MediaPlayerController getMediaPlayerController() {
        return mediaPlayerController;
    }

    public void setMediaPlayerController(MediaPlayerController mediaPlayerController) {
        this.mediaPlayerController = mediaPlayerController;
    }
}