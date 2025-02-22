package org.example.javafx_flexmusic.services;

import org.example.javafx_flexmusic.client.SocketConnection;
import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.Commands.Commands;
import org.example.javafx_flexmusic.tools.JsonSerializer;

import java.io.File;

public class TrackService {

    private final SocketConnection connection;

    public TrackService(SocketConnection connection) {
        this.connection = connection;
    }

    public void postTrack(File file, Track track) throws Exception {
        connection.sendCommand(Commands.POST_TRACK);
        if (connection.receiveConfirmation()) {
            connection.sendFile(file);
            String url = connection.readLine();
            track.setUrl(url);
            String trackJson = JsonSerializer.serialize(track);
            connection.writeLine(trackJson);
        }
    }

    public void getAllTracks() throws Exception {
        connection.sendCommand(Commands.GET_ALL_TRACKS);
    }
}
