package como.sekolah.submission4.helper;

import android.database.Cursor;

import java.util.ArrayList;

import como.sekolah.submission4.db.DatabaseContractTv;
import como.sekolah.submission4.entity.TvFav;

public class TvMappingHelper {
    public static ArrayList<TvFav> mapCursorToArrayList(Cursor noteCursor) {
        ArrayList<TvFav> tvShowsList = new ArrayList<>();

        while (noteCursor.moveToNext()) {
            int id = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(DatabaseContractTv.TvShowColumns._ID));
            String title = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContractTv.TvShowColumns.TITLE));
            String overview = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContractTv.TvShowColumns.OVERVIEW));
            String releaseDate = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContractTv.TvShowColumns.FIRST_AIR_DATE));
            String rate = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContractTv.TvShowColumns.VOTE_AVERAGE));
            String poster = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContractTv.TvShowColumns.POSTER));
            tvShowsList.add(new TvFav(id, title, overview, releaseDate, rate, poster));
        }

        return tvShowsList;
    }
}
