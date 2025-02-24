package org.example.javafx_flexmusic.tools;

import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.db.entity.UserSession;

import java.util.prefs.Preferences;

public class AuthUtils {

    private static final Preferences preferences = Preferences.userRoot().node("flexmusic").node("session");

    public static void loadUserSession() {
        try {
            String error = "";
            String currentUserJson = preferences.get("user", error);
            if (!currentUserJson.equals("null") && !currentUserJson.isEmpty()) {
                User currentUser = JsonSerializer.deserialize(currentUserJson, User.class);
                UserSession.getInstance().setCurrentUser(currentUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveUserSession() {
        try {
            User currentUser = UserSession.getInstance().getCurrentUser();
            if(currentUser != null) {
                String currentUserJson = JsonSerializer.serialize(currentUser);
                preferences.put("user", currentUserJson);
            } else {
                preferences.put("user", "null");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void notSaveUserSession() {
        preferences.put("user", "null");
    }
}
