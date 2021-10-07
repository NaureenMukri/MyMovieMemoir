package com.example.mymoviememoir;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.WatchListDatabase;
import entity.WatchList;
import viewmodel.WatchListViewModel;


public class WatchListFragment extends Fragment {

    WatchListViewModel watchListViewModel;
    WatchListDatabase watchListDatabase;
    List<HashMap<String, String>> watchListArray;
    SimpleAdapter myListAdapter;
    List<String> movieIDList;
    String movieID;

    String[] colHEAD = new String[] {"Movie Name","Movie Release Date","Movie Added to Watchlist Date"};
    int[] dataCell = new int[] {R.id.tv_watchlistMovieName,R.id.tv_watchlistMovieReleaseDate,R.id.tv_movieAddedtoWatchListDateTime};

    ListView watchListView;
    Button deleteMovie;

    public WatchListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.activity_watch_list_fragment, container, false);
        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getActivity().getApplication());
        watchListView = view.findViewById(R.id.watchListView);
        watchListArray = new ArrayList<HashMap<String, String>>();
        deleteMovie = view.findViewById(R.id.btn_deleteALL);
        getAllWatchListData getAllWatchListData = new getAllWatchListData();
        getAllWatchListData.execute();
        movieIDList = new ArrayList<String>();
        watchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MovieView.class);
                movieID = movieIDList.get(position);
                intent.putExtra("movieID", movieID);
                startActivity(intent);
            }
        });
        return view;

    }

    public class getAllWatchListData extends AsyncTask<Void, String, Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

           watchListViewModel.getAllWatchLists().observe(getViewLifecycleOwner(), new Observer<List<WatchList>>() {

               @Override
               public void onChanged(List<WatchList> watchLists) {
                   Log.d("size",String.valueOf(watchLists.size()));
                    for(WatchList temp: watchLists){
                        HashMap<String,String> map = new HashMap<String,String>();
                        String moviename = temp.getWatchListMovieName();
                        String moviereleasedate = temp.getWatchListMovieReleaseDate();
                        String movieaddedtowatchList = temp.getWatchListMovieDateTime();
                        String movieID = temp.getWatchListID();
                        map.put("Movie Name", moviename);
                        map.put("Movie Release Date", moviereleasedate);
                        map.put("Movie Added to Watchlist Date", movieaddedtowatchList);
                        watchListArray.add(map);
                        movieIDList.add(movieID);
                    }
                   myListAdapter = new SimpleAdapter(getContext(),watchListArray,R.layout.list_view,colHEAD,dataCell);
                   watchListView.setAdapter(myListAdapter);
               }
           });

           deleteMovie.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   watchListViewModel.deleteAll();
                   Toast.makeText(getContext(), "All Movies Deleted",Toast.LENGTH_LONG).show();
               }
           });

        }
    }

}
