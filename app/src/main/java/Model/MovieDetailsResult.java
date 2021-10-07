package Model;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsResult {
    public String movie_ID;
    public String movie_name;
    public String movie_release_year;
    public String img_url;
    public String movie_genre;
    public String movie_cast;
    public String movie_country;
    public String movie_director;
    public String movie_summary;
    public String movie_rating;

    public MovieDetailsResult(String movieID, String movieName, String movieRelease, String movieImage) {
        movie_ID = movieID;
        movie_name = movieName;
        movie_release_year = movieRelease;
        img_url = movieImage;
    }

    public MovieDetailsResult(String movieImage, String movieName, String movieGenre, String movieCast, String movieRelease, String movieCountry,
                              String movieDirector,  String movieSummary, String movieRating){
        img_url = movieImage;
        movie_name = movieName;
        movie_genre = movieGenre;
        movie_cast = movieCast;
        movie_release_year = movieRelease;
        movie_country = movieCountry;
        movie_director = movieDirector;
        movie_summary = movieSummary;
        movie_rating = movieRating;

    }

    public String getMovie_name(){

        return movie_name;
    }

    public String getMovie_release_year(){

        return movie_release_year;
    }

    public String getImg_url(){

        return img_url;
    }

    public String getMovie_genre(){

        return movie_genre;
    }

    public String getMovie_cast(){

        return movie_cast;
    }

    public String getMovie_country(){

        return movie_country;
    }

    public String getMovie_director(){

        return movie_director;
    }

    public String getMovie_summary(){

        return movie_summary;
    }

    public String getMovie_rating(){

        return movie_rating;
    }

    public void setMovie_genre(String movie_genre) {

        this.movie_genre = movie_genre;
    }

    public void setMovie_cast(String movie_cast) {

        this.movie_name = movie_cast;
    }

    public void setMovie_country(String movie_country) {
        this.movie_country = movie_country;
    }

    public void setMovie_director(String movie_director) {

        this.movie_name = movie_director;
    }

    public void setMovie_summary(String movie_summary) {

        this.movie_summary = movie_summary;
    }

    public void setMovie_rating(String movie_rating) {

        this.movie_rating = movie_rating;
    }
    public void setMovie_name(String movie_name) {

        this.movie_name = movie_name;
    }

    public void setMovie_release_year(String movie_release_year) {
        this.movie_release_year = movie_release_year;
    }

    public void setImg_url(String img_url) {

        this.img_url = img_url;
    }

}
