package org.example.javafx_flexmusic.db.entity;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    Integer id;
    Integer userId;
    String name;
    String image;
}