package org.example;

import org.example.tools.StringUrlConverter;

import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] argv) {
        try {
            URL url = StringUrlConverter.stringToUrlConvert("https://firebasestorage.googleapis.com/v0/b/sellcardatabase.appspot.com/o/music%2F1o_prod_hhhcra_Yk.mp3?alt=media&token=efb82ea0-a96e-4466-b7c3-170c61c035c2");
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
