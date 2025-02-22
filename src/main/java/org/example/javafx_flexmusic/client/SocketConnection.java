package org.example.javafx_flexmusic.client;

import lombok.Getter;
import org.example.javafx_flexmusic.Commands.Commands;

import java.io.*;
import java.net.Socket;

@Getter
public class SocketConnection {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;

    public void connect(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedOutputStream(socket.getOutputStream());
        inputStream = new BufferedInputStream(socket.getInputStream());
    }

    public void disconnect() throws IOException {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            socket.close();
        }
    }

    public void sendCommand(Commands command) {
        writer.println(command);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void writeLine(String line) {
        writer.println(line);
    }

    public void sendFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[200000];
        int bytesRead;

        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }

    public boolean receiveConfirmation() throws IOException {
        String response = readLine();
        return "OK".equals(response);
    }
}
