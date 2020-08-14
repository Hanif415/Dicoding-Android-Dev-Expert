package como.sekolah.submission3.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movies implements Parcelable {

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

    public void setOverview(String description) {
        this.overview = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

//    berfungsi untuk mengambi setiap film yang berada di website dengan menggunakan API
    public Movies(JSONObject object) {
        try {
            String title = object.getString("title");
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

    protected Movies(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
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
