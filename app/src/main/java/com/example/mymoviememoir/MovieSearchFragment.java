package com.example.mymoviememoir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecyclerAdapter;
import Model.MovieDetailsResult;

public class MovieSearchFragment extends Fragment {
    private TextView tvSearch;
    private EditText searchtext;
    private Button btnSearch;
    private RecyclerView movieRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    private String keyword;
    private RecyclerAdapter.RecyclerViewClickListener movieRecyclerViewClickListener;
    private String movieTitle;
    private String movieID;
    private String movieImageURL;
    private String releaseYear;
    private String personid;
    JSONArray movieDetailsArray;

    private List<MovieDetailsResult> movieDetailsList = new ArrayList<>();

    public MovieSearchFragment() {
// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        Intent intent = getActivity().getIntent();
        personid = intent.getStringExtra("personid");
        View view = inflater.inflate(R.layout.movie_search_fragment, container, false);
        tvSearch = view.findViewById(R.id.tv_Result);
        searchtext = view.findViewById(R.id.ed_keyword);
        btnSearch = view.findViewById(R.id.btn_search);
        movieRecyclerView = view.findViewById(R.id.rv_movieinfo);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = searchtext.getText().toString(); //create an anonymous AsyncTask
                getAPIdetails asyncTask = new getAPIdetails();
                asyncTask.execute(keyword);
            }
        });

        return view;
    }

    public class getAPIdetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return SearchOmDBAPI.search(keyword);
        }

        @Override
        protected void onPostExecute(String result) {
            movieDetailsArray = SearchOmDBAPI.getDetails(result);
            try {
                    for(int i = 0; i<movieDetailsArray.length(); i++) {
                        movieTitle = movieDetailsArray.getJSONObject(i).getString("title");
                        movieImageURL = movieDetailsArray.getJSONObject(i).getString("movie_poster");
                        releaseYear = movieDetailsArray.getJSONObject(i).getString("movie_release_date");
                        movieID = movieDetailsArray.getJSONObject(i).getString("IMDBid");
                        MovieDetailsResult movieDetailsResult = new MovieDetailsResult(movieID, movieTitle, releaseYear, movieImageURL);
                        movieDetailsList.add(movieDetailsResult);
                        Log.d("movieID", movieID);

                    }
                    movieRecyclerViewClickListener = new RecyclerAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onItemClick(int item) {
                        try {
                            Intent intent = new Intent(getActivity(), MovieView.class);
                            movieID = movieDetailsArray.getJSONObject(item).getString("IMDBid");
                            intent.putExtra("movieTitle", movieTitle);
                            intent.putExtra("movieReleaseYear", releaseYear);
                            intent.putExtra("movieImageURL", movieImageURL);
                            intent.putExtra("movieID", movieID);
                            intent.putExtra("personid",personid);
                            Log.d("movieID", movieID);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                    recyclerAdapter = new RecyclerAdapter(movieDetailsList, movieRecyclerViewClickListener);
                    movieRecyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
