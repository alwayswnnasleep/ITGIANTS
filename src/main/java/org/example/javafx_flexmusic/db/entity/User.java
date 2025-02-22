package org.example.javafx_flexmusic.db.entity;

import lombok.*;
import org.example.javafx_flexmusic.tools.ValidationUtils;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User  {
    private Integer id;
    private String username;
    private String password;
    private String email;

    public static User createForSignIn(String usernameOrEmail, String password) {
        User user = new User();
        if (ValidationUtils.isValidEmail(usernameOrEmail)) {
            user.setEmail(usernameOrEmail);
        } else {
            user.setUsername(usernameOrEmail);
        }
        user.setPassword(password);
        return user;
    }

    public static User createForSignUp(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
