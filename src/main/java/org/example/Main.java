package org.example;

import org.example.tools.FileSelector;
import org.example.tools.StringUrlConverter;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class Main {



    public static void main(String[] argv) {
        try {
//            FileSelector selector = new FileSelector();
//            File file  = selector.selectMp3File();
            URL url = StringUrlConverter.stringToUrlConvert("https://www.dropbox.com/scl/fi/6qjcws9sxakc8gpk8268b/file_new_laza.mp3?rlkey=gq29ojdvxzfbrpnyj2m2ll6gf&e=1&dl=1");
            Track track = new Track("1o", "Yhapojj", "None", url);
            MusicPlayer player = new MusicPlayer(track);
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите 1 для воспроизведения, 2 для приостановки, 3 для возобновления:");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1:
                        player.play();
                        break;
                    case 2:
                        player.pause();
                        break;
                    case 3:
                        player.resume();
                        break;
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
