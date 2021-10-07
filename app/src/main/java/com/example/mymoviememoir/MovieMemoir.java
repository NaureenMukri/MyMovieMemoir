package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Adapter.MemoirRecyclerAdapter;
import Model.MemoirDetailsResult;
import networkconnection.NetworkConnection;

public class MovieMemoir extends Fragment {

    private String personid;
    private RecyclerView memoirrecycler;
    private String memoirmovieName;
    private String memoirmovieReleaseDate;
    private String memoirmovieWatchedOnDate;
    private String memoirmovieRating;
    private String memoirmovieCinemaPostcode;
    private String memoirmovieComment;
    private String memoirmovieimage_url;
    private Spinner memoirSortSpinner;
    private String sort;
    private MemoirRecyclerAdapter memoirRecyclerAdapter;
    private MemoirRecyclerAdapter.RecyclerViewClickListener memoirrecyclerViewClickListener;
    JSONObject memoirDetailsList;
    JSONArray jsonArray;

    private List<MemoirDetailsResult> memoirDetailsResultsList = new ArrayList<>();

    public MovieMemoir(){
        //Empty Constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        personid = intent.getStringExtra("personid");
        View view = inflater.inflate(R.layout.activity_movie_memoir, container, false);
        memoirSortSpinner = view.findViewById(R.id.spinner_sortMemoir);
        memoirrecycler = view.findViewById(R.id.movie_memoir_info);
        memoirrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        getMemoirDetails getMemoirDetails = new getMemoirDetails();
        getMemoirDetails.execute();

        memoirSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort = parent.getItemAtPosition(position).toString();
                if (sort=="User Rating"){
                    Collections.sort(memoirDetailsResultsList, new Comparator<MemoirDetailsResult>() {
                        @Override
                        public int compare(MemoirDetailsResult o1, MemoirDetailsResult o2) {
                            return o1.memoirmovie_rating.compareTo(o2.memoirmovie_rating);

                        }
                    });
                }
                else if(sort=="Release Date"){
                    Collections.sort(memoirDetailsResultsList, new Comparator<MemoirDetailsResult>() {
                        @Override
                        public int compare(MemoirDetailsResult o1, MemoirDetailsResult o2) {
                            return o1.memoirmovie_release_year.compareTo(o2.memoirmovie_release_year);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public class getMemoirDetails extends AsyncTask<Void, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(Void... voids) {
            String methodPath = "findMemoirByPersonID/" + personid;
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
        protected void onPostExecute(final JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    memoirmovieName = jsonArray.getJSONObject(i).getString("memoirmoviename");
                    memoirmovieReleaseDate = jsonArray.getJSONObject(i).getString("memoirmoviereleasedate");
                    memoirmovieWatchedOnDate = jsonArray.getJSONObject(i).getString("memoirdatetime");
                    memoirmovieComment = jsonArray.getJSONObject(i).getString("memoirmoviecomment");
                    memoirmovieRating = jsonArray.getJSONObject(i).getString("memoirmovierating");
                    memoirmovieCinemaPostcode = jsonArray.getJSONObject(i).getJSONObject("cinemaid").getString("cinemapostcode");
                    MemoirDetailsResult memoirDetailsResult = new MemoirDetailsResult(memoirmovieName, memoirmovieReleaseDate, memoirmovieCinemaPostcode,
                            memoirmovieWatchedOnDate, memoirmovieComment, memoirmovieRating);
                    memoirDetailsResultsList.add(memoirDetailsResult);

                }
                memoirrecyclerViewClickListener = new MemoirRecyclerAdapter.RecyclerViewClickListener() {
                    @Override
                    public void onItemClick(int item) {
                        try {
                            Intent intent = new Intent(getActivity(), MemoirView.class);
                            memoirmovieName = jsonArray.getJSONObject(item).getString("memoirmoviename");
                            intent.putExtra("memoirmoviename", memoirmovieName);
                            Log.d("Movie Title passed", memoirmovieName);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                memoirRecyclerAdapter = new MemoirRecyclerAdapter(memoirDetailsResultsList, memoirrecyclerViewClickListener);
                memoirrecycler.setAdapter(memoirRecyclerAdapter);
                memoirRecyclerAdapter.notifyDataSetChanged();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class getMovieImage extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return SearchOmDBAPI.searchMovieImagefromTitle(memoirmovieName);
        }


        protected void onPostExecute(String result) {
            try {
                memoirDetailsList = SearchOmDBAPI.getMovieFullDetails(result);
                memoirmovieimage_url = memoirDetailsList.getString("Poster");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

