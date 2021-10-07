package com.example.mymoviememoir;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchOmDBAPI {

        private static final String API_KEY = "ae9812a5";
        //private static final String SEARCH_ID_cx = "015225941338325582206:bs2undbczor";

        public static String search(String keyword) {
            keyword = keyword.replace(" ", "+");
            URL url = null;
            HttpURLConnection connection = null;
            String textResult = "";
            try {
                url = new URL("http://www.omdbapi.com/?s=" + keyword + "&apikey=" + API_KEY);
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
            String release_date = null;
            String imdbID = null;

            JSONArray jsonArray_details = new JSONArray();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("Search");
                if (jsonArray != null && jsonArray.length() > 0) {
                    for(int i =0 ; i< jsonArray.length();i++) {
                        title = jsonArray.getJSONObject(i).getString("Title");
                        movie_poster = jsonArray.getJSONObject(i).getString("Poster");
                        release_date = jsonArray.getJSONObject(i).getString("Year");
                        imdbID = jsonArray.getJSONObject(i).getString("imdbID");
                        JSONObject details = new JSONObject();
                        details.put("result", "success");
                        details.put("IMDBid", imdbID);
                        details.put("title", title);
                        details.put("movie_poster", movie_poster);
                        details.put("movie_release_date", release_date);
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

    public static String searchMovieDetails(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("http://www.omdbapi.com/?i=" + keyword + "&apikey=" + API_KEY);
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

    public static JSONObject getMovieFullDetails(String result) {
        String title = null;
        String movie_poster = null;
        String release_date = null;
        String movie_genre = null;
        String movie_cast = null;
        String movie_rating = null;
        String movie_director = null;
        String movie_country = null;
        String movie_summary = null;
        String movie_id = null;
        JSONObject details = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject != null && jsonObject.length() > 0) {
                    title = jsonObject.getString("Title");
                    movie_poster = jsonObject.getString("Poster");
                    release_date = jsonObject.getString("Released");
                    movie_genre = jsonObject.getString("Genre");
                    movie_cast = jsonObject.getString("Actors");
                    movie_country = jsonObject.getString("Country");
                    movie_director = jsonObject.getString("Director");
                    movie_rating = jsonObject.getString("imdbRating");
                    movie_summary = jsonObject.getString("Plot");
                    movie_id = jsonObject.getString("imdbID");
                    details.put("result", "success");
                    details.put("title", title);
                    details.put("movie_poster", movie_poster);
                    details.put("movie_release_date", release_date);
                    details.put("genre", movie_genre);
                    details.put("cast", movie_cast);
                    details.put("country",movie_country);
                    details.put("director", movie_director);
                    details.put("rating", movie_rating);
                    details.put("plot", movie_summary);
                    details.put("imdbID", movie_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                details.put("result","fail");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return details;
    }

    public static String searchMovieImagefromTitle(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("http://www.omdbapi.com/?t=" + keyword + "&apikey=" + API_KEY);
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
