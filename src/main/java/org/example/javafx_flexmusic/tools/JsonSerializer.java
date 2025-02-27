package org.example.javafx_flexmusic.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.javafx_flexmusic.db.entity.Track;

import java.util.ArrayList;
import java.util.List;

public class JsonSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static List<Track> parseStringToTrackList(String jsonTracks) throws Exception {
        List<Track> tracks = new ArrayList<>();
        String[] trackJsons = jsonTracks.trim().split("\\s+");

        for (String trackJson : trackJsons) {
            Track track = JsonSerializer.deserialize(trackJson, Track.class);
            tracks.add(track);
        }
        return tracks;
    }
}
