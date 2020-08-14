package como.sekolah.submission4.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_NAME = "movie";

    public static final class MovieColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
        public static String VOTE_AVERAGE = "vote_average";
        public static String RELEASE_DATE = "release_date";
    }
}
