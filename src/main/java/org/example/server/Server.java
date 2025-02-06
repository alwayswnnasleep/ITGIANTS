package org.example.server;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private boolean running = false;
    ServerSocket serverSocket;
    private Thread serverRun;
    private ExecutorService threadPool;
    private DbxClientV2 client;

    public Server(int port) {
        this.port = port;
        threadPool = Executors.newFixedThreadPool(10);
        initializeDropbox();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread serverRun = new Thread(() -> {
            System.out.println("Server started on port: " + port);
            running = true;
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    threadPool.execute(new ClientHandler(clientSocket, client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        serverRun.start();
    }


    void initializeDropbox() {
        String ACCESS_TOKEN = "";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, ACCESS_TOKEN);
        FullAccount account;
        try {
            account = client.users().getCurrentAccount();
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }
        System.out.println(account.getName().getDisplayName());
    }
}
