package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Adapter.RecyclerAdapter;
import database.WatchListDatabase;
import entity.WatchList;
import viewmodel.WatchListViewModel;

public class MovieView extends AppCompatActivity {

    private ImageView movieimageView;
    private TextView movieSelectedMovieName;
    private TextView movieSelectedMovieReleaseYear;
    private TextView movieSelectedMovieCast;
    private TextView movieSelectedMovieGenre;
    private TextView movieSelectedMovieCountry;
    private TextView movieSelectedMovieDirector;
    private TextView movieSelectedMovieRating;
    private TextView movieSelectedMovieSummary;
    private RatingBar movieRating;
    private String movieID;
    private Button addMovieToWatchList;
    private Button addMovieToMemoir;
    private String movieSelectedName;
    private String movieSelectedID;
    private String movieSelectedReleaseYear;
    private String personID;
    private String movieSelectedImageURL;
    private String currentDateandTime;
    WatchListDatabase watchListDatabase;
    WatchListViewModel watchListViewModel;
    Toast toast;

    private void memoirMovie(){
        Intent intent = new Intent(this, AddToMemoir.class);
        intent.putExtra("personid", personID);
        intent.putExtra("movieImage", movieSelectedImageURL);
        intent.putExtra("movieName", movieSelectedName);
        intent.putExtra("movieReleaseDate", movieSelectedReleaseYear);
        intent.putExtra("movieaddedtoWatchList", currentDateandTime);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        movieimageView = findViewById(R.id.movieSelected_movieImage);
        movieSelectedMovieName = findViewById(R.id.movieSelected_name);
        movieSelectedMovieReleaseYear = findViewById(R.id.movieSelected_releaseYear);
        movieSelectedMovieCast = findViewById(R.id.movieSelected_cast);
        movieSelectedMovieGenre = findViewById(R.id.movieSelected_genre);
        movieSelectedMovieCountry = findViewById(R.id.movieSelected_country);
        movieSelectedMovieDirector = findViewById(R.id.movieSelected_director);
        movieSelectedMovieSummary = findViewById(R.id.movieSelected_summary);
        addMovieToWatchList = findViewById(R.id.btn_addMovietoWatchList);
        addMovieToMemoir = findViewById(R.id.btn_addMovietoMemoir);
        movieRating = findViewById(R.id.ratingBar_movieImdb);
        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getApplication());
        watchListViewModel.getAllWatchLists();

        Intent intent = getIntent();
        movieID = intent.getStringExtra("movieID");
        personID = intent.getStringExtra("personid");
        Log.d("movieID", movieID);

        getWatchListDatabase asyncTask1 = new getWatchListDatabase();
        asyncTask1.execute();

        getSelectedMovieDetails asyncTask = new getSelectedMovieDetails();
        asyncTask.execute(movieID);

        addMovieToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reached inside onClick", movieID);
                Log.d("movie ID", movieID + movieSelectedName);
                watchListViewModel.getAllWatchLists();
                if (watchListViewModel.findByID(movieSelectedID) !=null) {
                    addMovieToWatchList.setClickable(false);
                    addMovieToWatchList.setText("Added to WatchList");
                    addMovieToWatchList.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Log.d("MovieID", "already exists");
                    toast.makeText(MovieView.this,
                            "Movie Already Exists in WatchList", Toast.LENGTH_LONG).show();
                } else {
                    String watchlistMovieName = movieSelectedName;
                    String watchListMovieReleaseYear = movieSelectedReleaseYear;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    currentDateandTime = sdf.format(new Date());
                    WatchList watchList = new WatchList(movieSelectedID, watchlistMovieName, currentDateandTime, watchListMovieReleaseYear);
                    watchListViewModel.insert(watchList);
                    toast.makeText(MovieView.this, "Movie added to WatchList", Toast.LENGTH_LONG).show();
                }
            }
        });

        addMovieToMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoirMovie();
            }
        });
    }

    public class getSelectedMovieDetails extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            return SearchOmDBAPI.searchMovieDetails(movieID);
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject movieFullDetails = SearchOmDBAPI.getMovieFullDetails(result);
            try {
                movieSelectedName = movieFullDetails.getString("title");
                movieSelectedReleaseYear = movieFullDetails.getString("movie_release_date");
                movieSelectedImageURL = movieFullDetails.getString("movie_poster");
                Log.d("movie poster url", movieSelectedImageURL);
                String movieSelectedCast = movieFullDetails.getString("cast");
                String movieSelectedGenre = movieFullDetails.getString("genre");
                String movieSelectedDirector = movieFullDetails.getString("director");
                String movieSelectedCountry = movieFullDetails.getString("country");
                String movieSelectedRating = movieFullDetails.getString("rating");
                float rating = Float.parseFloat(movieSelectedRating);
                String movieSelectedSummary = movieFullDetails.getString("plot");
                movieSelectedID = movieFullDetails.getString("imdbID");
                movieSelectedMovieName.setText(movieSelectedName);
                movieSelectedMovieReleaseYear.setText(movieSelectedReleaseYear);
                movieSelectedMovieGenre.setText(movieSelectedGenre);
                movieSelectedMovieCast.setText(movieSelectedCast);
                movieSelectedMovieCountry.setText(movieSelectedCountry);
                movieSelectedMovieDirector.setText(movieSelectedDirector);
                movieSelectedMovieSummary.setText(movieSelectedSummary);
                movieRating.setRating(rating);
                Picasso.get()
                        .load(movieSelectedImageURL)
                        .placeholder(R.drawable.moviep)
                        .resize(700, 200)
                        .centerInside()
                        .into(movieimageView);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        public class getWatchListDatabase extends AsyncTask<Void, Void, Void> {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                WatchList watchList = watchListViewModel.findByID(movieSelectedID);
                if (watchList != null) {
                    addMovieToWatchList.setClickable(false);
                    addMovieToWatchList.setText("Added to WatchList");
                }
                return null;
            }
        }

}
