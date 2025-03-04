package org.example.javafx_flexmusic.db.entity;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Radio {
    Integer id;
    String url;
    String name;
    String image;
}
