package com.example.popularmovies.data.api;

import android.net.Uri;

import com.example.popularmovies.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = BuildConfig.API_KEY;

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

            socket.connect(socketAddress, timeoutMs);
            socket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Uri getBaseUrl() {
        return Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();
    }

    public static String getResponseFromHttpUrl(Uri path) throws IOException {
        URL url = new URL(path.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } finally {
            urlConnection.disconnect();
        }
    }
}
