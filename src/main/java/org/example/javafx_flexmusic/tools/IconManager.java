package org.example.javafx_flexmusic.tools;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class IconManager {

    private final Map<Button, IconState> buttonIconMap = new HashMap<>();

    public void addButton(Button button, String whiteIconPath, String grayIconPath) {
        buttonIconMap.put(button, new IconState(whiteIconPath, grayIconPath));
    }

    public void selectButton(Button selectedButton) {
        buttonIconMap.forEach((button, iconState) -> {
            ImageView imageView = (ImageView) button.getGraphic();
            if (button == selectedButton) {
                imageView.setImage(new Image(getClass().getResourceAsStream(iconState.whiteIconPath)));
            } else {
                imageView.setImage(new Image(getClass().getResourceAsStream(iconState.grayIconPath)));
            }
        });
    }

    private static class IconState {
        final String whiteIconPath;
        final String grayIconPath;

        IconState(String whiteIconPath, String grayIconPath) {
            this.whiteIconPath = whiteIconPath;
            this.grayIconPath = grayIconPath;
        }
    }
}
