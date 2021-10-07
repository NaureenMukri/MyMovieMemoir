package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import dao.WatchListDAO;
import database.WatchListDatabase;
import entity.WatchList;

public class WatchListRepository {


    private WatchListDAO dao;
    private LiveData<List<WatchList>> allWatchLists;
    private WatchList watchList;
    public WatchListRepository(Application application){
        WatchListDatabase db = WatchListDatabase.getInstance(application);
        dao=db.watchListDAO();
    }

    public LiveData<List<WatchList>> getAllWatchLists() {
        allWatchLists=dao.getAll();
        return allWatchLists;
    }

    public void insert(final WatchList watchList){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            dao.insert(watchList);
        } });
    }

    public void deleteAll(){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            dao.deleteAll(); }
    });
    }

    public void delete(final WatchList watchList){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchList); }
        });
    }

    public void insertAll(final WatchList... watchLists){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            dao.insertAll(watchLists);
        }
    });
    }

    public void updateWatchLists(final WatchList... watchLists){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            dao.updateWatchLists(watchLists);
        }
    });
    }

    public void updatebywatchListID(final String watchlistid, final String watchListMovieName, final String watchListMovieReleaseDate, final String watchListMovieDateTime) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updatebywatchListID(watchlistid, watchListMovieName, watchListMovieReleaseDate, watchListMovieDateTime);
            }
        });
    }
        public WatchList findByID(final String watchlistid){
            WatchListDatabase.databaseWriteExecutor.execute(new Runnable() { @Override
            public void run() {

                WatchList runWatchList= dao.findByID(watchlistid);
                setWatchList(runWatchList);

            }
            });
            return watchList;
        }

        public void setWatchList(WatchList watchlist)
        {
            this.watchList=watchlist;
        }
}
