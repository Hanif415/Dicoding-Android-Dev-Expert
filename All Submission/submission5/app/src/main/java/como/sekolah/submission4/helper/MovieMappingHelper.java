package como.sekolah.submission4.helper;

import android.database.Cursor;

import java.util.ArrayList;

import como.sekolah.submission4.db.DatabaseMovieContract;
import como.sekolah.submission4.entity.FavoriteMovie;

public class MovieMappingHelper {
    public static ArrayList<FavoriteMovie> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<FavoriteMovie> moviesList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns._ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.TITLE));
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.OVERVIEW));
            String releaseDate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.RELEASE_DATE));
            String rate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.VOTE_AVERAGE));
            String poster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.POSTER));
            moviesList.add(new FavoriteMovie(id, title, overview, releaseDate, rate, poster));
        }

        return moviesList;
    }

    public static FavoriteMovie mapCursorToObject(Cursor movieCursor){
        movieCursor.moveToFirst();
        int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns._ID));
        String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.TITLE));
        String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.OVERVIEW));
        String releaseDate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.RELEASE_DATE));
        String rate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.VOTE_AVERAGE));
        String poster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseMovieContract.MovieColumns.POSTER));

        return new FavoriteMovie(id, title, overview, releaseDate, rate, poster);
    }
}
