package com.example.mymoviememoir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import networkconnection.NetworkConnection;

public class HomeFragment extends Fragment {

    private TextView currentdate;
    private TextView username_textview;
    private TextView moviename1;
    private TextView moviename2;
    private TextView moviename3;
    private TextView moviename4;
    private TextView moviename5;
    private TextView movie1releasedate;
    private TextView movie2releasedate;
    private TextView movie3releasedate;
    private TextView movie4releasedate;
    private TextView movie5releasedate;
    private TextView movie1rating;
    private TextView movie2rating;
    private TextView movie3rating;
    private TextView movie4rating;
    private TextView movie5rating;
    private String personid;

    public HomeFragment() {
// Required empty public constructor
    }

    private double getRating(double rating)
    {
        double ratings = 0;
        if(rating < 1)
            ratings = 0;
        else if (rating < 1.9)
            ratings = 0.5;
        else if (rating < 2.8)
            ratings = 1;
        else if (rating < 3.7)
            ratings = 1.5;
        else if (rating < 4.6)
            ratings = 2;
        else if (rating < 5.5)
            ratings = 2.5;
        else if (rating < 6.4)
            ratings = 3;
        else if (rating < 7.3)
            ratings = 3.5;
        else if (rating < 8.2)
            ratings = 4;
        else if (rating < 9.1)
            ratings = 4.5;
        else
            ratings = 5;
        return ratings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Intent intent = getActivity().getIntent();
        personid = intent.getStringExtra("personid");
        String firstName = intent.getStringExtra("firstname");
        currentdate = view.findViewById(R.id.tv_currentdate_date);
        username_textview = view.findViewById(R.id.tv_username);
        username_textview.setText(firstName);
        moviename1 = view.findViewById(R.id.tv_topfivemovies_movie1);
        moviename2 = view.findViewById(R.id.tv_topfivemovies_movie2);
        moviename3 = view.findViewById(R.id.tv_topfivemovies_movie3);
        moviename4 = view.findViewById(R.id.tv_topfivemovies_movie4);
        moviename5 = view.findViewById(R.id.tv_topfivemovies_movie5);
        movie1releasedate = view.findViewById(R.id.movie_1_releasedate);
        movie2releasedate = view.findViewById(R.id.movie_2_releasedate);
        movie3releasedate = view.findViewById(R.id.movie_3_releasedate);
        movie4releasedate = view.findViewById(R.id.movie_4_releasedate);
        movie5releasedate = view.findViewById(R.id.movie_5_releasedate);
        movie1rating = view.findViewById(R.id.movie_1_rating);
        movie2rating = view.findViewById(R.id.movie_2_rating);
        movie3rating = view.findViewById(R.id.movie_3_rating);
        movie4rating = view.findViewById(R.id.movie_4_rating);
        movie5rating = view.findViewById(R.id.movie_5_rating);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        currentdate.setText(formattedDate);
        getPersonTopFiveMovies gp = new getPersonTopFiveMovies();
        gp.execute();
        return view;
    }

    public class getPersonTopFiveMovies extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... voids) {
            String methodPath = "findByMovieWatchedthesameyearreleased/" + personid;
            String resource = "Memoir";
            JSONArray response = null;
            try {
                JSONObject personObject = new JSONObject();
                personObject.put("methodPath", methodPath);
                personObject.put("resourceValue", resource);
                response = NetworkConnection.GetRequestOkHTTPAsArray(personObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("reachedpersondetails","success");
                    String movie_name = jsonObject.getString("MovieName");
                    String movie_release_date = jsonObject.getString("MovieReleaseYear");
                    Double movie_rating = jsonObject.getDouble("MovieRating");
                    Double final_movie_rating = getRating(movie_rating);
                    if(i==0){
                        moviename1.setText(movie_name);
                        movie1releasedate.setText(movie_release_date);
                        movie1rating.setText(final_movie_rating.toString());
                    }
                    else if(i==1){
                        moviename2.setText(movie_name);
                        movie2releasedate.setText(movie_release_date);
                        movie2rating.setText(final_movie_rating.toString());
                    }
                    else if(i==2){
                        moviename3.setText(movie_name);
                        movie3releasedate.setText(movie_release_date);
                        movie3rating.setText(final_movie_rating.toString());
                    }
                    else if(i==3){
                        moviename4.setText(movie_name);
                        movie4releasedate.setText(movie_release_date);
                        movie4rating.setText(final_movie_rating.toString());
                    }
                    else if(i==4){
                        moviename5.setText(movie_name);
                        movie5releasedate.setText(movie_release_date);
                        movie5rating.setText(final_movie_rating.toString());
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
