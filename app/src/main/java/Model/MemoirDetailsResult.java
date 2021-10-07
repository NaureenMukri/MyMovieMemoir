package Model;

public class MemoirDetailsResult {

        public String movie_ID;
        public String memoir_ID;
        public String memoirmovie_name;
        public String memoirmovie_release_year;
        public String memoirimg_url;
        public String memoirmovie_watchdate;
        public String memoirmovie_summary;
        public String memoirmovie_rating;
        public String memoirmovie_cinemaPostcode;
        public String memoirmovie_personID;

        public MemoirDetailsResult(String memoirmovie_name, String memoirmovie_release_year,
                                   String memoirmovie_cinemaPostcode, String memoirmovie_watchdate, String memoirmovie_summary, String memoirmovie_rating){

            this.memoirmovie_name = memoirmovie_name;
            this.memoirmovie_release_year = memoirmovie_release_year;
            this.memoirmovie_cinemaPostcode = memoirmovie_cinemaPostcode;
            this.memoirmovie_watchdate = memoirmovie_watchdate;
            this.memoirmovie_summary = memoirmovie_summary;
            this.memoirmovie_rating = memoirmovie_rating;
        }


    public String getMovie_ID() {
        return movie_ID;
    }

    public void setMovie_ID(String movie_ID) {
        this.movie_ID = movie_ID;
    }

    public String getMemoir_ID() {
        return memoir_ID;
    }

    public void setMemoir_ID(String memoir_ID) {
        this.memoir_ID = memoir_ID;
    }

    public String getMemoirmovie_name() {
        return memoirmovie_name;
    }

    public void setMemoirmovie_name(String memoirmovie_name) {
        this.memoirmovie_name = memoirmovie_name;
    }

    public String getMemoirmovie_release_year() {
        return memoirmovie_release_year;
    }

    public void setMemoirmovie_release_year(String memoirmovie_release_year) {
        this.memoirmovie_release_year = memoirmovie_release_year;
    }

    public String getMemoirimg_url() {
        return memoirimg_url;
    }

    public void setMemoirimg_url(String memoirimg_url) {
        this.memoirimg_url = memoirimg_url;
    }

    public String getMemoirmovie_watchdate() {
        return memoirmovie_watchdate;
    }

    public void setMemoirmovie_watchdate(String memoirmovie_watchdate) {
        this.memoirmovie_watchdate = memoirmovie_watchdate;
    }

    public String getMemoirmovie_summary() {
        return memoirmovie_summary;
    }

    public void setMemoirmovie_summary(String memoirmovie_summary) {
        this.memoirmovie_summary = memoirmovie_summary;
    }

    public String getMemoirmovie_rating() {
        return memoirmovie_rating;
    }

    public void setMemoirmovie_rating(String memoirmovie_rating) {
        this.memoirmovie_rating = memoirmovie_rating;
    }

    public String getMemoirmovie_cinemaPostcode() {
        return memoirmovie_cinemaPostcode;
    }

    public void setMemoirmovie_cinemaPostcode(String memoirmovie_cinemaPostcode) {
        this.memoirmovie_cinemaPostcode = memoirmovie_cinemaPostcode;
    }

    public String getMemoirmovie_personID() {
        return memoirmovie_personID;
    }

    public void setMemoirmovie_personID(String memoirmovie_personID) {
        this.memoirmovie_personID = memoirmovie_personID;
    }
}
