package org.example.javafx_flexmusic.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.models.MusicPlayer;
import org.example.javafx_flexmusic.tools.AuthUtils;


public class Main extends Application {

    private static MusicPlayer player;

    public static void main(String[] argv) {
        AuthUtils.loadUserSession();
        System.out.println(UserSession.getInstance().getCurrentUser());
        launch(argv);
    }
    @Override

    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafx_flexmusic/main.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root, 1000, 750);
            String css = this.getClass().getResource("/org/example/javafx_flexmusic/Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setTitle("FLEXMUSIC");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}