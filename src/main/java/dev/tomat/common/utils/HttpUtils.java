package dev.tomat.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class HttpUtils {
    public static String getJson(String urlString) {
        try {
            // Open url connection with GET request.
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Grab the response code and check if it was successful.
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error connecting to " + urlString + ". Response code: " + responseCode);
                return "";
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                // Write all the JSON data into a string using a scanner.
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                // Close the scanner and return the JSON string.
                scanner.close();
                return inline.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
