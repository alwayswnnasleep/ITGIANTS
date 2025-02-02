package org.example;

import java.net.URL;


public class Track {
    private String title;
    private String artist;
    private String album;
    private URL url;

    Track(String title, String artist, String album, URL url) throws Exception {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.url = url;
    }

    String getTitle() {
        return title;
    }

    String getArtist() {
        return artist;
    }

    String getAlbum() {
        return album;
    }

    URL getUrl() {
        return url;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setArtist(String artist) {
        this.artist = artist;
    }

    void setAlbum(String album) {
        this.album = album;
    }

    void setUrl(URL url) {
        this.url = url;
    }
}
