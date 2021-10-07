package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import networkconnection.NetworkConnection;

public class AddToMemoir extends AppCompatActivity {

    String dateTimeMovieWatched;
    String cinemaName;
    int cinemaPostCode;
    String movieFeedBack;
    float movieRating;
    String movieName;
    String movieReleaseDate;
    String dateTimemovieaddedtoWatchList;

    ImageView memoir_movieImage;
    RatingBar memoirRatingBar;
    TextView memoir_movieName;
    TextView memoir_movieReleaseDate;
    TextView memoir_movieAddedtoWatchList;
    DatePicker memoir_dateMovieWatched;
    TimePicker memoir_timeMovieWatched;
    Spinner memoir_cinemaName;
    Spinner memoir_cinemaPostCode;
    EditText memoir_movieFeedBack;
    Button addMemoir;
    String personID;
    String imageURL;


    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void gobacktoHomePage(){
        Intent intent = new Intent(this, MemoirView.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_memoir);

        Intent intent = getIntent();
        personID = intent.getStringExtra("personid");
        imageURL = intent.getStringExtra("movieImage");
        movieName = intent.getStringExtra("movieName");
        movieReleaseDate = intent.getStringExtra("movieReleaseDate");
        dateTimemovieaddedtoWatchList = intent.getStringExtra("movieaddedtoWatchList");

        memoir_movieImage = findViewById(R.id.iv_movieimg);
        Picasso.get()
                .load(imageURL)
                .placeholder(R.mipmap.ic_launcher)
                .resize(700, 200)
                .centerInside()
                .into(memoir_movieImage);
        memoir_movieName = findViewById(R.id.memoir_movieName);
        memoir_movieName.setText(movieName);
        memoir_movieReleaseDate = findViewById(R.id.memoir_movieReleaseDate_value);
        memoir_movieReleaseDate.setText(movieReleaseDate);
        memoir_movieAddedtoWatchList = findViewById(R.id.memoir_movieDateTime_value);
        memoir_movieAddedtoWatchList.setText(dateTimemovieaddedtoWatchList);
        memoir_dateMovieWatched = findViewById(R.id.date_picker_movie);
        memoir_timeMovieWatched = findViewById(R.id.memoir_time_picker);
        memoir_cinemaName = findViewById(R.id.spinner_movieCinemaName);
        memoir_cinemaPostCode = findViewById(R.id.spinner_movieCinemaPostCode);
        memoir_movieFeedBack = findViewById(R.id.memoir_moviesummary_value);
        memoirRatingBar = findViewById(R.id.ratingBar_addMovieRating);
        addMemoir = findViewById(R.id.button_add_movie_memoir);

//        getCinemaNameAndList getCinemaNameAndList = new getCinemaNameAndList();
//        getCinemaNameAndList.execute();

        addMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = memoir_dateMovieWatched.getDayOfMonth();
                int month = memoir_dateMovieWatched.getMonth();
                int year = memoir_dateMovieWatched.getYear();
                int hour = memoir_timeMovieWatched.getHour();
                int minute = memoir_timeMovieWatched.getMinute();

                dateTimeMovieWatched = "" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + "00.0";
                cinemaName = memoir_cinemaName.getSelectedItem().toString();
                cinemaPostCode = Integer.parseInt(memoir_cinemaPostCode.getSelectedItem().toString());
                movieFeedBack = memoir_movieFeedBack.getText().toString();
                movieRating = memoirRatingBar.getRating();
                movieName = memoir_movieName.getText().toString();
                movieReleaseDate = memoir_movieReleaseDate.getText().toString();
                JSONObject postdataObject = new JSONObject();
                JSONObject postdata = new JSONObject();
                String resource = "memoir";
                String methodPath = "addMemoirfromWatchList";
                try {
                    postdata.put("memoirmoviename", movieName);
                    postdata.put("memoirmoviereleasedate", movieReleaseDate);
                    postdata.put("memoirdatetime", dateTimeMovieWatched);
                    postdata.put("memoirmovierating", movieRating);
                    postdata.put("memoirmoviecomment", movieFeedBack);
                    postdata.put("cinemapostcode", cinemaPostCode);
                    postdata.put("personid", personID);
                    postdataObject.put("methodPath", methodPath);
                    postdataObject.put("resourceValue", resource);
                    postdataObject.put("dataValue", postdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                putmovieinmemoir putmovieinmemoir = new putmovieinmemoir();
                putmovieinmemoir.execute(postdataObject);


            }
        });

    }

    private class putmovieinmemoir extends AsyncTask<JSONObject, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(JSONObject... jsonObjects) {
            return NetworkConnection.PostRequestOkHTTP(jsonObjects[0]);
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            try {
                String responseStatus = response.getString("status");
                if (Integer.parseInt(responseStatus) == 200) {
                    Toast.makeText(getApplicationContext(), "Movie Added to Memoir Successfully", Toast.LENGTH_LONG).show();
                    gobacktoHomePage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

