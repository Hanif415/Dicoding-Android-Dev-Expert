package como.sekolah.favoriteapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseTVShowContract {

    public static final String AUTHORITY = "como.sekolah.submission4";
    private static final String SCHEME = "content";

    public static String TABLE_NAME = "tv_show";

    public static final class TvShowColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
        public static String VOTE_AVERAGE = "vote_average";
        public static String FIRST_AIR_DATE = "first_air_date";

        // untuk membuat URI content://como.sekolah.submission4/tv_show
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
