package org.example.javafx_flexmusic.services;

import org.example.javafx_flexmusic.Commands.Commands;
import org.example.javafx_flexmusic.client.SocketConnection;
import org.example.javafx_flexmusic.db.entity.Playlist;
import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.db.entity.UserSession;
import org.example.javafx_flexmusic.tools.JsonSerializer;

import java.io.File;
import java.util.List;

public class PlaylistService {

    private final SocketConnection connection;

    public PlaylistService(SocketConnection connection) {
        this.connection = connection;
    }

    public void savePlaylist(Playlist playlist, File file) throws Exception {
        connection.sendCommand(Commands.SAVE_PLAYLIST);
        if (connection.receiveConfirmation()) {
            connection.sendFile(file);
            String url = connection.readLine();
            playlist.setImage(url);
            String playlistJson = JsonSerializer.serialize(playlist);
            connection.writeLine(playlistJson);
        }
    }

    public List<Playlist> getAllPlaylists() throws Exception {
        connection.sendCommand(Commands.GET_ALL_PLAYLISTS);
        if(connection.receiveConfirmation()) {
            User currentUser = UserSession.getInstance().getCurrentUser();
            String jsonCurrentUser = JsonSerializer.serialize(currentUser);
            connection.writeLine(jsonCurrentUser);
            String playlistsJson = connection.readLine();
            if(!playlistsJson.isEmpty()) {
                List<Playlist> playlists = JsonSerializer.parseStringToList(playlistsJson, Playlist.class);
                return playlists;
            }
        }
        return null;
    }

    public void createDefaultPlaylist() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setName("Любимые");
        playlist.setUserId(UserSession.getInstance().getCurrentUser().getId());
        File favoriteImage = new File("src/main/resources/org/example/javafx_flexmusic/images/image-favorite-playlist-gray.png");
        savePlaylist(playlist, favoriteImage);
    }
}
