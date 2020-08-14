package como.sekolah.submission4.entity;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.OVERVIEW;
import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.POSTER;
import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.RELEASE_DATE;
import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.TITLE;
import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.VOTE_AVERAGE;
import static como.sekolah.submission4.db.DatabaseMovieContract.getColumnInt;
import static como.sekolah.submission4.db.DatabaseMovieContract.getColumnString;

public class ResultItem {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster")
    private String poster;

    @SerializedName("vote_average")
    private String vote_average;

    @SerializedName("release_date")
    private String release_date;

    public ResultItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public ResultItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.poster = getColumnString(cursor, POSTER);
        this.vote_average = getColumnString(cursor, VOTE_AVERAGE);
        this.release_date = getColumnString(cursor, RELEASE_DATE);
    }

    public String toString() {
        return
                "ResultsItem{" +
                        "overview = '" + overview + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + poster + '\'' +
                        ",release_date = '" + release_date + '\'' +
                        ",id = '" + id + '\'' +
                        ", vote_average = '" + vote_average + '\'' +
                        "}";
    }
}
