package org.example.server;

public class ServerMain {

    private int port;
    Server server;

    public ServerMain(int port) {
        this.port = port;
        server = new Server(port);
    }

    public static void main(String[] args) {
        new ServerMain(12345);
    }

}
