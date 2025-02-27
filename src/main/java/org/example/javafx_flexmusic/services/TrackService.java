package org.example.javafx_flexmusic.services;

import org.example.javafx_flexmusic.client.SocketConnection;
import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.Commands.Commands;
import org.example.javafx_flexmusic.tools.JsonSerializer;

import java.io.File;
import java.util.List;

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

    public List<Track> getAllTracks() throws Exception {
        connection.sendCommand(Commands.GET_ALL_TRACKS);
        if (connection.receiveConfirmation()) {
            String jsonTrackList = connection.readLine();
            List<Track> trackList = JsonSerializer.parseStringToTrackList(jsonTrackList);
            return trackList;
        }
        return null;
    }

    public List<Track> searchTracks(String text) throws Exception {
        connection.sendCommand(Commands.SEARCH_TRACKS);
        if(connection.receiveConfirmation()) {
            connection.writeLine(text);
            String jsonTrackList = connection.readLine();
            List<Track> trackList = JsonSerializer.parseStringToTrackList(jsonTrackList);
            return trackList;
        }
        return null;
    }
}
