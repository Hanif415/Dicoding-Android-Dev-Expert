package como.sekolah.favoriteapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteMovie implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String release_date;
    private String vote_average;
    private String poster;

    public FavoriteMovie(int id, String title, String overview, String date_release, String rating, String poster) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = date_release;
        this.vote_average = rating;
        this.poster = poster;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String date_release) {
        this.release_date = date_release;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String rating) {
        this.vote_average = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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
        dest.writeString(this.release_date);
        dest.writeString(this.vote_average);
        dest.writeString(this.poster);
    }

    protected FavoriteMovie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<FavoriteMovie> CREATOR = new Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel source) {
            return new FavoriteMovie(source);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };
}
