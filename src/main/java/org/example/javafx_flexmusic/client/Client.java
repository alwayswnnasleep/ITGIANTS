package org.example.javafx_flexmusic.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.javafx_flexmusic.models.MusicPlayer;
import org.example.javafx_flexmusic.models.Track;
import org.example.javafx_flexmusic.tools.FileSelector;


import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedOutputStream outputSocket;
    private BufferedInputStream inputSocket;
    private PrintWriter pw;
    private BufferedReader br;

    public void connect(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        outputSocket = new BufferedOutputStream(socket.getOutputStream());
        inputSocket = new BufferedInputStream(socket.getInputStream());
        pw = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void disconnect() throws IOException {
        socket.close();
    }


    public Boolean receiveConfirmation() throws IOException {
        String command = br.readLine();
        return command.equals("OK");
    }


    public void sendFile(File file) throws IOException {
        pw.println("POST");
        if (receiveConfirmation()) {
            FileInputStream inputFile = new FileInputStream(file);
            int bytesRead;
            byte[] buffer = new byte[200000];
            while ((bytesRead = inputFile.read(buffer)) > 0) {
                outputSocket.write(buffer, 0, bytesRead);
            }
            inputFile.close();
        }
    }

    private String getLineFromServer() throws IOException {
        return br.readLine();
    }

    private void sendTrack(Track track) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson
        String trackJson = objectMapper.writeValueAsString(track);
        pw.println(trackJson);
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connect("localhost", 12345);
        File file = FileSelector.selectMp3File();
        client.sendFile(file);
        System.out.println("client send mp3 file to server");
        String url = client.getLineFromServer();
        System.out.println("client accept url from server: " + url);
        System.out.println("start track on client");
        Track track = new Track("Кастинг", "Баста", "Баста 3", url);
        client.sendTrack(track);
        MusicPlayer player = new MusicPlayer(track);
        player.play();
    }
}
