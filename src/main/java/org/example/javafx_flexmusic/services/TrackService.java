package org.example.javafx_flexmusic.services;

import org.example.javafx_flexmusic.client.SocketConnection;
import org.example.javafx_flexmusic.db.entity.Track;
import org.example.javafx_flexmusic.Commands.Commands;
import org.example.javafx_flexmusic.tools.JsonSerializer;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
            List<Track> trackList = parseTracks(jsonTrackList);
            return trackList;
        }
        return null;
    }

    private List<Track> parseTracks(String jsonTracks) throws Exception {
        List<Track> tracks = new ArrayList<>();
        String[] trackJsons = jsonTracks.trim().split("\\s+"); // Разделяем по пробелам

        for (String trackJson : trackJsons) {
            Track track = JsonSerializer.deserialize(trackJson, Track.class); // Десериализуем каждый объект
            tracks.add(track);
        }

        return tracks;
    }
}
