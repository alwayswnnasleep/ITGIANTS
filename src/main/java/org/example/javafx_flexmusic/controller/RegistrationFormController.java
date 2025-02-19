package org.example.javafx_flexmusic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegistrationFormController {
    @FXML
    private Button arrow_back; // Кнопка назад
    @FXML
    private void handleBackButtonClick() {
        switchScene("/org/example/javafx_flexmusic/main.fxml"); // Путь к главному FXML файлу
    }
    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newSceneRoot = loader.load();
            Scene newScene = new Scene(newSceneRoot);

            Stage stage = (Stage) arrow_back.getScene().getWindow(); // Получаем текущее окно
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
