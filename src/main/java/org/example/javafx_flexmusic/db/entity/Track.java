package org.example.javafx_flexmusic.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Track {

    private Integer id;
    private Integer user_id;
    private Integer last;
    private String title;
    private String url;
    private String genre;
    private String artist;


}