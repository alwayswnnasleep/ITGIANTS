package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.client.Client;
import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.AuthUtils;
import org.example.javafx_flexmusic.tools.StageSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    private static final String VISIBILITY_OFF_ICON_PATH = "/org/example/javafx_flexmusic/images/icon-visibility-off-gray.png";
    private static final String VISIBILITY_ON_ICON_PATH = "/org/example/javafx_flexmusic/images/icon-visibility-gray.png";

    private boolean isSignIn = true;
    private boolean visibilityPassword = false;

    @FXML
    private Label sign_in_label, sign_up_label;
    @FXML
    private Separator sign_in_separator, sign_up_separator;
    @FXML
    private Pane username_pane;
    @FXML
    private CheckBox stay_logged_checkbox;
    @FXML
    private Button back_button, sign_button;
    @FXML
    private TextField username_or_email_field, username_field, password_text_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private ImageView visibility_off_icon;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        resetToSignInMode();
    }

    private void resetToSignInMode() {
        isSignIn = true;
        visibilityPassword = false;
        username_pane.setVisible(false);
        username_pane.setManaged(false);
        password_text_field.setVisible(false);
        updateVisibilityIcon();
    }

    private void clearFields() {
        username_field.clear();
        username_or_email_field.clear();
        password_field.clear();
        password_text_field.clear();
    }

    private void switchMode(boolean isSignInMode) {
        clearFields();
        resetPasswordVisibility();

        sign_in_separator.setVisible(isSignInMode);
        sign_up_separator.setVisible(!isSignInMode);

        username_pane.setVisible(!isSignInMode);
        username_pane.setManaged(!isSignInMode);

        isSignIn = isSignInMode;
        sign_button.setText(isSignInMode ? "ВХОД" : "РЕГИСТРАЦИЯ");
    }

    private void resetPasswordVisibility() {
        visibilityPassword = false;
        password_field.setVisible(true);
        password_text_field.setVisible(false);
        updateVisibilityIcon();
    }

    private void updateVisibilityIcon() {
        String imagePath = visibilityPassword ? VISIBILITY_ON_ICON_PATH : VISIBILITY_OFF_ICON_PATH;
        visibility_off_icon.setImage(new Image(getClass().getResourceAsStream(imagePath)));
    }

    private boolean areFieldsValid() {
        if (isSignIn) {
            return !username_or_email_field.getText().trim().isEmpty() && !password_field.getText().trim().isEmpty();
        }
        return !username_field.getText().trim().isEmpty() &&
                !username_or_email_field.getText().trim().isEmpty() &&
                !password_field.getText().trim().isEmpty();
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
        if (!areFieldsValid()) {
            return;
        }

        Client client = new Client();
        String usernameOrEmail = username_or_email_field.getText().trim();
        String password = password_field.getText().trim();

        if (isSignIn) {
            handleSignIn(client, usernameOrEmail, password);
        } else {
            handleSignUp(client, usernameOrEmail, password);
        }
    }

    private void handleSignIn(Client client, String usernameOrEmail, String password) {
        User user = User.createForSignIn(usernameOrEmail, password);
        User loggedUser = client.loginUser(user);

        if (loggedUser != null) {
            handleSuccessfulLogin(loggedUser);
        }
    }

    private void handleSignUp(Client client, String usernameOrEmail, String password) {
        String username = username_field.getText().trim();
        User user = User.createForSignUp(username, usernameOrEmail, password);
        User registeredUser = client.registerUser(user);

        if (registeredUser != null) {
            handleSuccessfulLogin(registeredUser);
        }
    }

    private void handleSuccessfulLogin(User user) {
        UserSession.getInstance().setCurrentUser(user);
        if (stay_logged_checkbox.isSelected()) {
            AuthUtils.saveUserSession();
        } else {
            AuthUtils.notSaveUserSession();
        }
        StageSwitcher.switchStage((Stage) sign_button.getScene().getWindow(), "/org/example/javafx_flexmusic/main.fxml");
    }

    @FXML
    private void handleBackButtonClick() {
        StageSwitcher.switchStage((Stage) back_button.getScene().getWindow(), "/org/example/javafx_flexmusic/main.fxml");
    }

    @FXML
    private void handleVisibilityOffIconClick() {
        visibilityPassword = !visibilityPassword;
        updateVisibilityIcon();

        if (visibilityPassword) {
            password_text_field.setText(password_field.getText());
            password_field.setVisible(false);
            password_text_field.setVisible(true);
        } else {
            password_field.setText(password_text_field.getText());
            password_text_field.setVisible(false);
            password_field.setVisible(true);
        }
    }
}