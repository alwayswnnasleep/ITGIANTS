package org.example.javafx_flexmusic.controller;

import org.example.javafx_flexmusic.controller.manager.ControllerManager;

public abstract class AbstractController {

    protected MediaPlayerController mediaPlayerController;

    public void initMediaPlayerController() {
        this.mediaPlayerController = ControllerManager.getInstance().getMediaPlayerController();
    }
}