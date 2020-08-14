package como.sekolah.submission2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    private int poster;
    private String judul;
    private String rating;
    private String sinopsis;


    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.poster);
        dest.writeString(this.judul);
        dest.writeString(this.rating);
        dest.writeString(this.sinopsis);
    }

    protected Movie(Parcel in) {
        this.poster = in.readInt();
        this.judul = in.readString();
        this.rating = in.readString();
        this.sinopsis = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
