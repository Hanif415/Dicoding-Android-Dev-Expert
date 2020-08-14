package como.sekolah.submission4.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShows implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String poster;
    private String vote_average;
    private String first_air_date;

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

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return first_air_date;
    }

    public void setRelease_date(String release_date) {
        this.first_air_date = release_date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public TvShows(JSONObject object){
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            String vote_average = object.getString("vote_average");
            String first_air_date = object.getString("first_air_date");

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.poster = poster_path;
            this.vote_average = vote_average;
            this.first_air_date = first_air_date;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TvShows() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.vote_average);
        dest.writeString(this.first_air_date);
    }

    protected TvShows(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.vote_average = in.readString();
        this.first_air_date = in.readString();
    }

    public static final Creator<TvShows> CREATOR = new Creator<TvShows>() {
        @Override
        public TvShows createFromParcel(Parcel source) {
            return new TvShows(source);
        }

        @Override
        public TvShows[] newArray(int size) {
            return new TvShows[size];
        }
    };
}
