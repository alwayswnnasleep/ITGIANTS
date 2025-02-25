package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.StageSwitcher;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    Label username_label;

    @FXML
    Pane logout_confirmation;

    @FXML
    ImageView logout_ico;

    @FXML
    Button yes_button, no_button;

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
        StageSwitcher.switchStage((Stage) yes_button.getScene().getWindow(),"/org/example/javafx_flexmusic/RegistrationScene.fxml" );
    }

    @FXML
    public void handleNoButtonClick() {
        logout_confirmation.setVisible(false);
    }
}
