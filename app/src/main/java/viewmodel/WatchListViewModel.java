package viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import entity.WatchList;
import repository.WatchListRepository;

public class WatchListViewModel extends ViewModel {

    private WatchListRepository wlRepository;
    private MutableLiveData<List<WatchList>> allWatchLists;
    public WatchListViewModel () {

        allWatchLists=new MutableLiveData<>();
    }
    public void setWatchList(List<WatchList> watchLists) {
        allWatchLists.setValue(watchLists);
    }

    public LiveData<List<WatchList>> getAllWatchLists()
    {
        return wlRepository.getAllWatchLists();
    }

    public void initalizeVars(Application application)
    {
        wlRepository = new WatchListRepository(application);
    }

    public void insert(WatchList watchList)
    {
        wlRepository.insert(watchList);
    }

    public void insertAll(WatchList... watchLists)
    {
        wlRepository.insertAll(watchLists);
    }

    public void deleteAll()
    {
        wlRepository.deleteAll();
    }

    public void update(WatchList... watchLists)
    {
        wlRepository.updateWatchLists(watchLists);
    }

    public void updateByID(String watchlistID, String watchListMovieName, String watchListMovieReleaseDate, String watchListMovieDateTime) {
        wlRepository.updatebywatchListID(watchlistID, watchListMovieName, watchListMovieReleaseDate, watchListMovieDateTime);
    }

    public WatchList findByID(String watchlistid){

        return wlRepository.findByID(watchlistid); }
}

