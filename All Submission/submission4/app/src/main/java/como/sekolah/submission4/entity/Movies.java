package como.sekolah.submission4.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movies implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String poster;
    private String vote_average;
    private String release_date;

    public Movies(int id, String title, String overview, String releaseDate, String rate, String poster) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = releaseDate;
        this.vote_average = rate;
        this.poster = poster;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
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


    public Movies(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            String vote_average = object.getString("vote_average");
            String release_date = object.getString("release_date");

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.poster = poster_path;
            this.vote_average = vote_average;
            this.release_date = release_date;

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        dest.writeString(this.release_date);
    }

    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.vote_average = in.readString();
        this.release_date = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
