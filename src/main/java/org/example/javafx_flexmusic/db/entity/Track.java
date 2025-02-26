package org.example.javafx_flexmusic.db.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Track implements Serializable {

    private Integer id;
    private Integer user_id;
    private String duration;
    private String title;
    private String url;
    private String genre;
    private String artist;


}