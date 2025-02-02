package org.example.tools;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class StringUrlConverter {

    public static URL stringToUrlConvert(String path) throws MalformedURLException {
        return URI.create(path).toURL();
    }

    public static String urlToStringConvert(URL url) {
        return url.toString();
    }

}
