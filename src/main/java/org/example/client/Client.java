package org.example.client;

import org.example.tools.FileSelector;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private final String serverAddress;
    private final int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void sendFile(File file) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             FileInputStream input = new FileInputStream(file);
             BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream())) {
            while (input.available() > 0) {
                int bSize = input.available();
                byte[] buffer = new byte[bSize];
                input.read(buffer);
                System.out.println("buffer size: " + buffer.length);
                output.write(buffer, 0, bSize);
            }
            System.out.println("Файл успешно отправлен.");
        } catch (IOException e) {
            System.err.println("Ошибка отправки файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        FileSelector selector = new FileSelector();
        File file = selector.selectMp3File();
        client.sendFile(file);
    }
}