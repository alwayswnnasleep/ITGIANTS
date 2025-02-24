package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.client.Client;
import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.AuthUtils;
import org.example.javafx_flexmusic.tools.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationSceneController implements Initializable {

    private boolean isSignIn = true;
    private boolean isSignUp = false;

    @FXML
    Label sign_in_label, sign_up_label;
    @FXML
    Separator sign_in_separator, sign_up_separator;
    @FXML
    Pane username_pane;
    @FXML
    CheckBox stay_logged_checkbox;

    @FXML
    private Button back_button, sign_in_button;
    @FXML
    private TextField username_or_email_field, username_field;
    @FXML
    private PasswordField password_field;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        isSignIn = true;
        isSignUp = false;
        username_pane.setVisible(false);
        username_pane.setManaged(false);
    }

    private void switchMode(boolean isSignInMode) {
        sign_in_separator.setVisible(isSignInMode);
        sign_up_separator.setVisible(!isSignInMode);
        username_pane.setVisible(!isSignInMode);
        username_pane.setManaged(!isSignInMode);

        isSignIn = isSignInMode;
        isSignUp = !isSignInMode;

        sign_in_button.setText(isSignInMode ? "SIGN IN" : "SIGN UP");
    }

    @FXML
    private void handleSignInLabelClick() {
        switchMode(true);
    }

    @FXML
    private void handleSignUpLabelClick() {
        switchMode(false);
    }

    @FXML
    private void handleSignButtonClick() {
        Client client = new Client();
        String usernameOrEmail = username_or_email_field.getText().trim();
        String password = password_field.getText().trim();
        String username;
        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            return;
        }
        if (isSignIn) {
            User user = User.createForSignIn(usernameOrEmail, password);
            User loggedUser = client.loginUser(user);
            if(loggedUser != null) {
                UserSession.getInstance().setCurrentUser(loggedUser);
                if(stay_logged_checkbox.isSelected()) {
                    AuthUtils.saveUserSession();
                } else {
                    AuthUtils.notSaveUserSession();
                }
                System.out.println("Logged: " + loggedUser);
            }
        }
        if (isSignUp) {
            username = username_field.getText();
            User user = User.createForSignUp(username, usernameOrEmail, password);
            User registeredUser = client.registerUser(user);
            if(registeredUser != null) {
                UserSession.getInstance().setCurrentUser(registeredUser);
                if(stay_logged_checkbox.isSelected()) {
                    AuthUtils.saveUserSession();
                    System.out.println("U stay logged in system: " + registeredUser);
                } else {
                    AuthUtils.notSaveUserSession();
                    System.out.println("U not stay logged, but reg: " + registeredUser);
                }
            }
        }
    }

    @FXML
    private void handleBackButtonClick() {
        SceneSwitcher.switchScene((Stage) back_button.getScene().getWindow(), "/org/example/javafx_flexmusic/main.fxml");
    }
}
