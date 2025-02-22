package org.example.javafx_flexmusic.services;

import org.example.javafx_flexmusic.client.SocketConnection;
import org.example.javafx_flexmusic.db.entity.User;
import org.example.javafx_flexmusic.Commands.Commands;
import org.example.javafx_flexmusic.tools.JsonSerializer;

import javax.swing.*;

public class UserService {

    private final SocketConnection connection;

    public UserService(SocketConnection connection) {
        this.connection = connection;
    }

    public User loginUser(User user) throws Exception {
        connection.sendCommand(Commands.LOGIN_USER);
        if(connection.receiveConfirmation()) {
            String userJson = JsonSerializer.serialize(user);
            connection.writeLine(userJson);

            String loggedUserJson = connection.readLine();
            if(!loggedUserJson.equals("null")) {
                User loggedUser = JsonSerializer.deserialize(loggedUserJson, User.class);
                return loggedUser;
            }
        }
        return null;
    }

    public User registerUser(User user) throws Exception {
        connection.sendCommand(Commands.REGISTER_USER);
        if (connection.receiveConfirmation()) {
            String userJson = JsonSerializer.serialize(user);
            connection.writeLine(userJson);

            String createdUserJson = connection.readLine();
            if(!createdUserJson.equals("null")) {
                User createdUser = JsonSerializer.deserialize(createdUserJson, User.class);
                return createdUser;
            }
        }
        return null;
    }
}