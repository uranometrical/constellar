package dev.tomat.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
    public static String getJson(String url) {
        try {
            // json encoding
            InputStream response = new URL(url).openStream();
            byte[] bytes = new byte[response.read()];
            System.out.println(response.read(bytes));
            response.close();
            return new String(bytes).trim();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
