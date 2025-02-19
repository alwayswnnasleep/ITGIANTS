package org.example.javafx_flexmusic.models;

import java.io.Serializable;

public class Track implements Serializable {
    private String title;
    private String artist;
    private String album;
    private String url;


    public Track() {}

    public Track(String title, String artist, String album, String url) throws Exception {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", url=" + url +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
