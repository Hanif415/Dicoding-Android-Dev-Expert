package como.sekolah.submission4.helper;

import android.database.Cursor;

import java.util.ArrayList;

import como.sekolah.submission4.db.DatabaseTVShowContract;
import como.sekolah.submission4.entity.FavoriteTVShow;

public class TVShowMappingHelper {
    public static ArrayList<FavoriteTVShow> mapCursorToArrayList(Cursor noteCursor) {
        ArrayList<FavoriteTVShow> tvShowsList = new ArrayList<>();

        while (noteCursor.moveToNext()) {
            int id = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(DatabaseTVShowContract.TvShowColumns._ID));
            String title = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseTVShowContract.TvShowColumns.TITLE));
            String overview = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseTVShowContract.TvShowColumns.OVERVIEW));
            String releaseDate = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseTVShowContract.TvShowColumns.FIRST_AIR_DATE));
            String rate = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseTVShowContract.TvShowColumns.VOTE_AVERAGE));
            String poster = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseTVShowContract.TvShowColumns.POSTER));
            tvShowsList.add(new FavoriteTVShow(id, title, overview, releaseDate, rate, poster));
        }

        return tvShowsList;
    }
}
