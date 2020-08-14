package como.sekolah.submission3.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShows implements Parcelable {

    private String title;
    private String overview;
    private String poster;

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

    public TvShows(JSONObject object){

        try{
            String title = object.getString("name");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");

            this.title = title;
            this.overview = overview;
            this.poster = poster_path;

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
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
    }

    protected TvShows(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
    }

    public static final Parcelable.Creator<TvShows> CREATOR = new Parcelable.Creator<TvShows>() {
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
