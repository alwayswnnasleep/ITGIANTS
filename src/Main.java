import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

// https://stream2.datacenter.by/dushevnoe     - душевное

public class Main {
    public static void main(String[] args) {
        String audioUrl = "https://chanson.hostingradio.ru:8041/chanson128.mp3";

        try {
            // Создание URL объекта
            URL url = new URL(audioUrl);
            // Получение соединения
            URLConnection connection = url.openConnection();
            // Получение входного потока
            InputStream inputStream = connection.getInputStream();

            // Воспроизведение аудиопотока
            Player player = new Player(inputStream);
            player.play();

        } catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }
}