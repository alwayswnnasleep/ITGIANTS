package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; // Правильный импорт
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.StageSwitcher;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private FlowPane playlistContainer;
    @FXML
    Pane shadows_pane, create_playlist_pane;
    @FXML
    private ImageView place_image_playlist; // ImageView для выбора изображения
    @FXML
    private TextField playlist_name_textfield;
    @FXML
    Label username_label;

    @FXML
    Pane logout_confirmation;

    @FXML
    ImageView logout_ico;

    @FXML
    Button yes_button, no_button, create_playlist_button, confirm_create_playlist_button, close_button_playlist;
    @FXML
    public void handleSelectImageButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg, *.gif)", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) place_image_playlist.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            Image image = new Image(file.toURI().toString());
            place_image_playlist.setImage(image);
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        logout_confirmation.setVisible(false);
        String username = UserSession.getInstance().getCurrentUser().getUsername();
        username_label.setText(username);
    }

    @FXML
    public void handleLogoutIconClick() {
        logout_confirmation.setVisible(true);
    }

    @FXML
    public void handleYesButtonClick() {
        UserSession.getInstance().logout();
        StageSwitcher.switchStage((Stage) yes_button.getScene().getWindow(), "/org/example/javafx_flexmusic/RegistrationScene.fxml");
    }

    @FXML
    public void handleNoButtonClick() {
        logout_confirmation.setVisible(false);
    }

    @FXML
    public void handleCreatePlaylistButton() {
        setVisibility(true);
    }

    @FXML
    public void handleCloseCreatingPlaylistButton() {
        setVisibility(false);
    }

    @FXML
    public void handleConfirmCreatePlaylistButton() {
        createPlaylist();
    }
    private void setVisibility(boolean flag){
        shadows_pane.setVisible(flag);
        create_playlist_pane.setVisible(flag);
    }
    private void clearFields() {
        place_image_playlist.setImage(null); // Очищаем изображение
        playlist_name_textfield.clear(); // Очищаем текстовое поле
    }
    private void createPlaylist() {

        Image playlistImage = place_image_playlist.getImage();
        String playlistName = playlist_name_textfield.getText();

        VBox newPlaylistBox = new VBox();
        newPlaylistBox.setAlignment(javafx.geometry.Pos.CENTER);
        newPlaylistBox.setPrefHeight(240.0);
        newPlaylistBox.setPrefWidth(220.0);
        newPlaylistBox.getStyleClass().add("backgroundColorPane");

        ImageView newImageView = new ImageView(playlistImage);
        newImageView.setFitHeight(200.0);
        newImageView.setFitWidth(200.0);
        newImageView.setPickOnBounds(true);
        newImageView.setPreserveRatio(true);

        Label newLabel = new Label(playlistName);
        newLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        javafx.scene.text.Font font = new javafx.scene.text.Font(22.0);
        newLabel.setFont(font);

        newPlaylistBox.getChildren().addAll(newImageView, newLabel);

        playlistContainer.getChildren().add(newPlaylistBox);
        setVisibility(false);
        clearFields();
    }
}