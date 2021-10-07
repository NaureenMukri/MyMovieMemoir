package com.example.mymoviememoir;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchGoogleAPI {
    private static final String API_KEY = "AIzaSyADEYElydMcmNIE4x_kAh442wSwgiGoFrY";
    private static final String SEARCH_ID_cx = "015225941338325582206:bs2undbczor";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" + API_KEY + "&cx=" +
                    SEARCH_ID_cx + "&q=" + keyword + query_parameter);
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

    public static JSONArray getDetails(String result) {
        String title = null;
        String movie_poster = null;
        JSONArray jsonArray_details = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            if (jsonArray != null && jsonArray.length() > 0) {
                for(int i =0 ; i< jsonArray.length();i++) {
                    title = jsonArray.getJSONObject(i).getString("title");
                    JSONArray cse_image = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("cse_image");
                    movie_poster = cse_image.getJSONObject(0).getString("src");
                    JSONObject details = new JSONObject();
                    details.put("result", "success");
                    details.put("title", title);
                    details.put("movie_poster", movie_poster);
                    jsonArray_details.put(details);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject details = new JSONObject();
            try {
                details.put("result","fail");
                jsonArray_details.put(details);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonArray_details;
    }
}
