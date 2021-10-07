package com.example.mymoviememoir;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchGeoLocation {

    private static final String API_KEY = "AIzaSyBav2rXIz_4WWvtksoFm0MIXZz8-7eN7Ow";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + keyword + "&key=" + API_KEY);
            Log.d("url",url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            int responsecode = connection.getResponseCode();
            BufferedReader br;
            if (responsecode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
            }
            else
            {
                br = new BufferedReader(new InputStreamReader(
                        connection.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            Log.d("response", response.toString());
            textResult = response.toString();
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return textResult;
    }
}
