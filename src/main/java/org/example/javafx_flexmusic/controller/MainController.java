package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.IconManager;
import org.example.javafx_flexmusic.tools.StageSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private MediaPlayerController mediaManager;
    private IconManager iconManager;

    private static final String PROFILE_ICON_WHITE_PATH = "/org/example/javafx_flexmusic/images/icon-profile-white.png";
    private static final String PROFILE_ICON_GRAY_PATH = "/org/example/javafx_flexmusic/images/icon-profile-gray.png";
    private static final String MUSIC_ICON_WHITE_PATH = "/org/example/javafx_flexmusic/images/icon-music-white.png";
    private static final String MUSIC_ICON_GRAY_PATH = "/org/example/javafx_flexmusic/images/icon-music-gray.png";
    private static final String RADIO_ICON_WHITE_PATH = "/org/example/javafx_flexmusic/images/icon-radio-white.png";
    private static final String RADIO_ICON_GRAY_PATH = "/org/example/javafx_flexmusic/images/icon-radio-gray.png";
    private static final String UPLOAD_ICON_WHITE_PATH = "/org/example/javafx_flexmusic/images/icon-upload-white.png";
    private static final String UPLOAD_ICON_GRAY_PATH = "/org/example/javafx_flexmusic/images/icon-upload-gray.png";


    @FXML
    private TableView<Track> table_tracks;
    @FXML
    private TableColumn<Track, Void> column_play;
    @FXML
    private ImageView playPauseIcon, profile_icon;
    @FXML
    private Button music_button, profile_button, radio_button, upload_button;
    @FXML
    private Pane pane_volume_slider;
    @FXML
    private Slider slider_time;
    @FXML
    private Slider slider_volume;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private BorderPane border_pane;

    private void initializeIconManager() {
        iconManager = new IconManager();
        iconManager.addButton(music_button, MUSIC_ICON_WHITE_PATH, MUSIC_ICON_GRAY_PATH);
        iconManager.addButton(profile_button, PROFILE_ICON_WHITE_PATH, PROFILE_ICON_GRAY_PATH);
        iconManager.addButton(radio_button, RADIO_ICON_WHITE_PATH, RADIO_ICON_GRAY_PATH);
        iconManager.addButton(upload_button, UPLOAD_ICON_WHITE_PATH, UPLOAD_ICON_GRAY_PATH);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/org/example/javafx_flexmusic/SearchScene.fxml"));
            border_pane.setCenter(view);
            initializeIconManager();
            mediaManager = new MediaPlayerController(slider_time, slider_volume, playPauseIcon, currentTimeLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void playMusic() {
        mediaManager.togglePlayPause();
    }

    @FXML
    private void setVisibleVolumePanel() {
        pane_volume_slider.setVisible(!pane_volume_slider.isVisible());
    }

    @FXML
    private void handleMusicButtonClick() throws Exception {
        iconManager.selectButton(music_button);
        AnchorPane view = FXMLLoader.load(getClass().getResource("/org/example/javafx_flexmusic/SearchScene.fxml"));
        border_pane.setCenter(view);
    }

    @FXML
    private void handleProfileButtonClick() throws Exception {
        if (UserSession.getInstance().getCurrentUser() == null) {
            StageSwitcher.switchStage((Stage) profile_button.getScene().getWindow(), "/org/example/javafx_flexmusic/RegistrationScene.fxml");
        } else {
            iconManager.selectButton(profile_button);
            AnchorPane view = FXMLLoader.load(getClass().getResource("/org/example/javafx_flexmusic/ProfileScene.fxml"));
            border_pane.setCenter(view);
        }
    }

    @FXML
    private void handleUploadButtonClick() throws Exception{
        if(UserSession.getInstance().getCurrentUser() == null) {
            StageSwitcher.switchStage((Stage) profile_button.getScene().getWindow(), "/org/example/javafx_flexmusic/RegistrationScene.fxml");
        } else {
            iconManager.selectButton(upload_button);
            AnchorPane view = FXMLLoader.load(getClass().getResource("/org/example/javafx_flexmusic/UploadTrackScene.fxml"));
            border_pane.setCenter(view);
        }
    }
}