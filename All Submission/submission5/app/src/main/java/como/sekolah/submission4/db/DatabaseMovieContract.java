package como.sekolah.submission4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseMovieContract {

        public static final String AUTHORITY = "como.sekolah.submission4";
        private static final String SCHEME = "content";

    public static String TABLE_NAME = "movie";

    public static final class MovieColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
        public static String VOTE_AVERAGE = "vote_average";
        public static String RELEASE_DATE = "release_date";

        // untuk membuat URI content://como.sekolah.submission4/movie
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return  cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
