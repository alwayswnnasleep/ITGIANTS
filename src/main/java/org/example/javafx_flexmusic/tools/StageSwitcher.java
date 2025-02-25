package org.example.javafx_flexmusic.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageSwitcher {
    public static void switchStage(Stage stage, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(StageSwitcher.class.getResource(fxmlFile));
            Parent newSceneRoot = loader.load();
            Scene newScene = new Scene(newSceneRoot);
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
