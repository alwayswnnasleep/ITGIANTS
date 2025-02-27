package org.example.javafx_flexmusic.client;

import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.services.TrackService;
import org.example.javafx_flexmusic.services.UserService;
import org.example.javafx_flexmusic.tools.FileSelector;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class Client {

    private final String serverIp = "192.168.43.178";
    private final int serverPort = 12345;
    private final SocketConnection connection = new SocketConnection();
    private final TrackService trackService = new TrackService(connection);
    private final UserService userService = new UserService(connection);

    public void postTrack(Track track, File file) {
        try {
            connection.connect(serverIp, serverPort);
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

    public List<Track> getAllTracks() {
        try {
            connection.connect(serverIp, serverPort);
            List<Track> trackList = trackService.getAllTracks();
            connection.disconnect();
            return trackList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Track> searchTracks(String text) {
        try {
            connection.connect(serverIp,serverPort);
            List<Track> trackList = trackService.searchTracks(text);
            connection.disconnect();
            return trackList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}