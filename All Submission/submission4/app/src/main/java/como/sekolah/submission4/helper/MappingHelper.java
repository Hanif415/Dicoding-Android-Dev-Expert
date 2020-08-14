package como.sekolah.submission4.helper;

import android.database.Cursor;

import java.util.ArrayList;

import como.sekolah.submission4.db.DatabaseContract;
import como.sekolah.submission4.entity.MovieFav;

public class MappingHelper {
    public static ArrayList<MovieFav> mapCursorToArrayList(Cursor noteCursor) {
        ArrayList<MovieFav> moviesList = new ArrayList<>();

        while (noteCursor.moveToNext()) {
            int id = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
            String title = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
            String overview = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW));
            String releaseDate = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE));
            String rate = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE));
            String poster = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER));
            moviesList.add(new MovieFav(id, title, overview, releaseDate, rate, poster));
        }

        return moviesList;
    }
}
