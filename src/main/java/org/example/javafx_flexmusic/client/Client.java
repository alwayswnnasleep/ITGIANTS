package org.example.javafx_flexmusic.client;

import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.services.TrackService;
import org.example.javafx_flexmusic.services.UserService;
import org.example.javafx_flexmusic.tools.FileSelector;

import java.io.File;
import java.sql.Connection;

public class Client {

    private final String serverIp = "127.0.0.1";
    private final int serverPort = 12345;
    private final SocketConnection connection = new SocketConnection();
    private final TrackService trackService = new TrackService(connection);
    private final UserService userService = new UserService(connection);

    public void postTrack() {
        try {
            connection.connect(serverIp, serverPort);

            File file = FileSelector.selectMp3File();
            Track track = new Track();
            // TODO: Заполнить трек данными (название, артист и т.д.)
            trackService.postTrack(file, track);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User loginUser(User user) {
        try {
            connection.connect(serverIp, serverPort);
            User loggedUser = userService.loginUser(user);
            if(loggedUser != null) {
                return loggedUser;
            }
            connection.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User registerUser(User user) {
        try {
            connection.connect(serverIp, serverPort);

            User createdUser = userService.registerUser(user);
            if (createdUser != null) {
                return createdUser;
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAllTracks() {
        try {
            connection.connect(serverIp, serverPort);

            trackService.getAllTracks();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}