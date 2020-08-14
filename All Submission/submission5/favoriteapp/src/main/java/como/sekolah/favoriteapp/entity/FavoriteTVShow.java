package como.sekolah.favoriteapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteTVShow implements Parcelable {

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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public FavoriteTVShow(int id, String title, String overview, String first_air_date, String rate, String poster) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.first_air_date = first_air_date;
        this.vote_average = rate;
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
        dest.writeString(this.poster);
        dest.writeString(this.vote_average);
        dest.writeString(this.first_air_date);
    }

    protected FavoriteTVShow(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.vote_average = in.readString();
        this.first_air_date = in.readString();
    }

    public static final Creator<FavoriteTVShow> CREATOR = new Creator<FavoriteTVShow>() {
        @Override
        public FavoriteTVShow createFromParcel(Parcel source) {
            return new FavoriteTVShow(source);
        }

        @Override
        public FavoriteTVShow[] newArray(int size) {
            return new FavoriteTVShow[size];
        }
    };
}
