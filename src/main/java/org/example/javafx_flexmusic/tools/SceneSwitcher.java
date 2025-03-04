package org.example.javafx_flexmusic.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.javafx_flexmusic.controller.AbstractController;

public class SceneSwitcher {
    public static Parent loadScene(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
        Parent root = loader.load();
        if (loader.getController() instanceof AbstractController) {
            ((AbstractController) loader.getController()).initMediaPlayerController();
        }
        return root;
    }
}
