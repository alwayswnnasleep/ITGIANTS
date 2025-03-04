package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.client.Client;
import org.example.javafx_flexmusic.db.entity.Playlist;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.FileSelector;
import org.example.javafx_flexmusic.tools.StageSwitcher;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController extends AbstractController implements Initializable {

    private List<Playlist> playlists;
    private File selectedPlaylistImage;

    @FXML
    private FlowPane playlistContainer;
    @FXML
    private Pane shadows_pane, create_playlist_pane, logout_confirmation;
    @FXML
    private ImageView place_image_playlist, logout_icon;
    @FXML
    private TextField playlist_name_textfield;
    @FXML
    private Label username_label;
    @FXML
    private Button yes_button, no_button, create_playlist_button, confirm_create_playlist_button, close_button_playlist;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        initPlaylistContainer();
        updatePlaylists();
        logout_confirmation.setVisible(false);
        String username = UserSession.getInstance().getCurrentUser().getUsername();
        username_label.setText(username);
    }

    private void initPlaylistContainer() {
        playlistContainer.setHgap(10);
        playlistContainer.setVgap(10);
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
    public void handleSelectImageButton() {
        selectedPlaylistImage = FileSelector.selectFile("Select image", "png", "png");
        if (selectedPlaylistImage != null) {
            Image image = new Image(selectedPlaylistImage.toURI().toString());
            place_image_playlist.setImage(image);
        }
    }

    private void setVisibility(boolean flag) {
        shadows_pane.setVisible(flag);
        create_playlist_pane.setVisible(flag);
    }

    private void clearFields() {
        place_image_playlist.setImage(null);
        playlist_name_textfield.clear();
    }

    private void displayPlaylists() {
        playlistContainer.getChildren().clear();
        for (Playlist playlist : playlists) {
            VBox playlistCard = createPlaylistCard(playlist);
            playlistContainer.getChildren().add(playlistCard);
        }
    }


    private VBox createPlaylistCard(Playlist playlist) {
        VBox playlistCard = new VBox();
        playlistCard.setAlignment(Pos.CENTER);
        playlistCard.setPrefSize(220, 240);
        playlistCard.setSpacing(10);
        playlistCard.getStyleClass().add("playlist-card");
        ImageView playlistImageView = new ImageView();
        playlistImageView.setFitWidth(200);
        playlistImageView.setFitHeight(200);
        playlistImageView.setPreserveRatio(true);
        playlistImageView.setPickOnBounds(true);
        if (playlist.getImage() != null && !playlist.getImage().isEmpty()) {
            try {
                Image image = new Image(playlist.getImage(), true);
                playlistImageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Ошибка загрузки изображения: " + e.getMessage());
            }
        }
        Label playlistNameLabel = new Label(playlist.getName());
        playlistNameLabel.setFont(new Font(22));
        playlistNameLabel.setTextFill(Color.WHITE); // Белый цвет текста
        playlistCard.getChildren().addAll(playlistImageView, playlistNameLabel);
        return playlistCard;
    }

    @FXML
    private void handleConfirmCreatePlaylistButtonClick() {
        Client client = new Client();
        String playlistName = playlist_name_textfield.getText();
        if (playlistName == null || playlistName.isEmpty() || selectedPlaylistImage == null) {
            System.err.println("Название плейлиста не может быть пустым");
            return;
        }
        Playlist playlist = new Playlist();
        playlist.setUserId(UserSession.getInstance().getCurrentUser().getId());
        playlist.setName(playlistName);
        client.savePlaylist(playlist, selectedPlaylistImage);
        updatePlaylists();
        setVisibility(false);
        clearFields();
    }

    private void updatePlaylists() {
        Client client = new Client();
        playlists = client.getAllPlaylists();
        if (playlists != null && !playlists.isEmpty()) {
            displayPlaylists();
        }
    }
}