package com.example.mymoviememoir;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MemoirView extends AppCompatActivity {

    private ImageView memoirimageView;
    private TextView memoirSelectedMovieName;
    private TextView memoirSelectedMovieReleaseYear;
    private TextView memoirSelectedMovieCast;
    private TextView memoirSelectedMovieGenre;
    private TextView memoirSelectedMovieCountry;
    private TextView memoirSelectedMovieDirector;
    private TextView memoirSelectedMovieRating;
    private TextView memoirSelectedMovieSummary;
    private RatingBar memoirRatingbyUser;
    private RatingBar movieRating;
    private Button gobackButton;
    private String memoirmovieTitle;
    private String memoirSelectedName;
    private String memoirSelectedReleaseYear;
    private String memoirSelectedImageURL;
    private String movieSelectedImageURL;
    private String currentDateandTime;



    private void backToMemoirMovie() {
        Intent intent = new Intent(this, MovieMemoir.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoir_view);

        memoirimageView = findViewById(R.id.memoirSelected_movieImage);
        memoirSelectedMovieName = findViewById(R.id.memoirSelected_name);
        memoirSelectedMovieReleaseYear = findViewById(R.id.memoirSelected_releaseYear);
        memoirSelectedMovieCast = findViewById(R.id.memoirSelected_cast);
        memoirSelectedMovieGenre = findViewById(R.id.memoirSelected_genre);
        memoirSelectedMovieCountry = findViewById(R.id.memoirSelected_country);
        memoirSelectedMovieDirector = findViewById(R.id.memoirSelected_director);
        memoirSelectedMovieRating = findViewById(R.id.memoirSelected_movieRating);
        memoirSelectedMovieSummary = findViewById(R.id.memoirSelected_summary);
        memoirRatingbyUser = findViewById(R.id.ratingBar_memoirImDB);
        gobackButton = findViewById(R.id.btn_memoirback);

        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMemoirMovie();

            }
        });

        Intent intent = getIntent();
        memoirmovieTitle = intent.getStringExtra("memoirmoviename");
        Log.d("MovieTitle", memoirmovieTitle);

        getSelectedMemoirDetails getSelectedMemoirDetails = new getSelectedMemoirDetails();
        getSelectedMemoirDetails.execute();


    }

        public class getSelectedMemoirDetails extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                return SearchOmDBAPI.searchMovieImagefromTitle(memoirmovieTitle);
            }

            @Override
            protected void onPostExecute(String result) {
                JSONObject movieFullDetails = SearchOmDBAPI.getMovieFullDetails(result);
                try {
                    memoirSelectedName = movieFullDetails.getString("title");
                    memoirSelectedReleaseYear = movieFullDetails.getString("movie_release_date");
                    memoirSelectedImageURL = movieFullDetails.getString("movie_poster");
                    String memoirSelectedCast = movieFullDetails.getString("cast");
                    String memoirSelectedGenre = movieFullDetails.getString("genre");
                    String memoirSelectedDirector = movieFullDetails.getString("director");
                    String memoirSelectedCountry = movieFullDetails.getString("country");
                    String memoirSelectedRating = movieFullDetails.getString("rating");
                    Float rating = Float.parseFloat(memoirSelectedRating);
                    String memoirSelectedSummary = movieFullDetails.getString("plot");
                    memoirSelectedMovieName.setText(memoirSelectedName);
                    memoirSelectedMovieReleaseYear.setText(memoirSelectedReleaseYear);
                    memoirSelectedMovieGenre.setText(memoirSelectedGenre);
                    memoirSelectedMovieCast.setText(memoirSelectedCast);
                    memoirSelectedMovieCountry.setText(memoirSelectedCountry);
                    memoirSelectedMovieDirector.setText(memoirSelectedDirector);
                    memoirSelectedMovieRating.setText(memoirSelectedRating);
                    movieRating.setRating(rating);
                    memoirSelectedMovieSummary.setText(memoirSelectedSummary);
                    Picasso.get()
                            .load(memoirSelectedImageURL)
                            .placeholder(R.drawable.moviep)
                            .resize(700, 200)
                            .centerInside()
                            .into(memoirimageView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
