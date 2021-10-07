package entity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

import dao.WatchListDAO;

@Entity
public class WatchList {

    @PrimaryKey
    @NonNull
    public String watchListID;

    @ColumnInfo(name = "watch_list_MovieName")
    public String watchListMovieName;

    @ColumnInfo(name = "watch_list_MovieReleaseDate")
    public String watchListMovieReleaseDate;

    @ColumnInfo(name = "movie_added_to_watchlist_DateTime")
    public String watchListMovieDateTime;

    public WatchList(String watchListID, String watchListMovieName, String watchListMovieReleaseDate, String watchListMovieDateTime){
        this.watchListID = watchListID;
        this.watchListMovieName = watchListMovieName;
        this.watchListMovieReleaseDate = watchListMovieReleaseDate;
        this.watchListMovieDateTime = watchListMovieDateTime;
    }

    public String getWatchListID() {
        return watchListID;
    }

    public String getWatchListMovieName() {
        return watchListMovieName;
    }

    public void setWatchListMovieName(String watchListMovieName) {
        this.watchListMovieName = watchListMovieName;
    }

    public void setWatchListID(String watchListID) {
        this.watchListID = watchListID;
    }

    public String getWatchListMovieReleaseDate() {
        return watchListMovieReleaseDate;
    }

    public void setWatchListMovieReleaseDate(String watchListMovieReleaseDate) {
        this.watchListMovieReleaseDate = watchListMovieReleaseDate;
    }

    public String getWatchListMovieDateTime() {
        return watchListMovieDateTime;
    }

    public void setWatchListMovieDateTime(String watchListMovieDateTime) {
        this.watchListMovieDateTime = watchListMovieDateTime;
    }


}
