package org.example.server;


import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private DbxClientV2 client;

    public ClientHandler(Socket socket, DbxClientV2 client) {
        clientSocket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            sleep(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String outputFileName = "F:\\mp3\\received_" + System.currentTimeMillis() + ".mp3";
        try (BufferedInputStream input = new BufferedInputStream(clientSocket.getInputStream());
             FileOutputStream fileOutput = new FileOutputStream(outputFileName)) {
            while (input.available() > 0) {
                int bSize = input.available();
                byte[] buffer = new byte[bSize];
                input.read(buffer);
                fileOutput.write(buffer, 0, buffer.length);
                try {
                    sleep(100);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            uploadFileToDropbox(outputFileName);
            System.out.println("Файл успешно принят и сохранен как " + outputFileName);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке клиента: " + e.getMessage());
            e.printStackTrace();
        }
        //        } finally {
//            try {
//                //   clientSocket.close();
//            } catch (IOException e) {
//                System.err.println("Ошибка при закрытии сокета клиента: " + e.getMessage());
//            }
//        }
    }

    void uploadFileToDropbox(String localFilePath) {
        String dropboxFilePath = "/music/file_new_laza.mp3";
        try (InputStream in = new FileInputStream(localFilePath)) {
            FileMetadata metadata = client.files().uploadBuilder(dropboxFilePath)
                    .uploadAndFinish(in);
            String directUrl = getDirectUrl(dropboxFilePath);
            System.out.println(directUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    String getDirectUrl(String dropboxFilePath) throws DbxException {
        try {
            SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(dropboxFilePath);
            String sharedUrl = sharedLinkMetadata.getUrl();
            return convertToDirectUrl(sharedUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String convertToDirectUrl(String sharedUrl) {
        if (sharedUrl != null && sharedUrl.contains("&dl=0")) {
            return sharedUrl.replace("&dl=0", "&dl=1");
        }
        return sharedUrl;
    }

}
