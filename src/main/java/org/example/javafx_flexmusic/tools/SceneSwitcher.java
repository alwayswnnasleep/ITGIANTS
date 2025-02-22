package org.example.javafx_flexmusic.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
    public static void switchScene(Stage stage, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
            Parent newSceneRoot = loader.load();
            Scene newScene = new Scene(newSceneRoot);
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
