package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entity.WatchList;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WatchListDAO {


        @Query("SELECT * FROM WatchList")
        LiveData<List<WatchList>> getAll();

        @Query("SELECT * FROM WatchList WHERE watchListID = :watchListID LIMIT 1")
        WatchList findByID(String watchListID);

        @Insert
        void insertAll(WatchList... watchLists);

        @Insert
        long insert(WatchList watchList);

        @Delete
        void delete(WatchList watchList);

        @Update(onConflict = REPLACE)
        void updateWatchLists(WatchList... watchLists);

        @Query("DELETE FROM WatchList")
        void deleteAll();

        @Query("UPDATE WatchList SET watch_list_MovieName=:watchListMovieName, watch_list_MovieReleaseDate=:watchListMovieReleaseDate, " +
                "movie_added_to_watchlist_DateTime=:watchListMovieDateTime WHERE watchListID= :watchListID")
        void updatebywatchListID(String watchListID, String watchListMovieName, String watchListMovieReleaseDate, String watchListMovieDateTime);
}
