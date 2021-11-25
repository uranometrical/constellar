package dev.tomat.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
    public static String getJson(String url) {
        try {
            URLConnection request = new URL(url).openConnection();
            request.connect();
            return new InputStreamReader((InputStream) request.getContent()).toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
