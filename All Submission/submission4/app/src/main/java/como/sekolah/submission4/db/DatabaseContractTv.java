package como.sekolah.submission4.db;

import android.provider.BaseColumns;

public class DatabaseContractTv {

    static String TABLE_NAME = "tv_show";

    public static final class TvShowColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
        public static String VOTE_AVERAGE = "vote_average";
        public static String FIRST_AIR_DATE = "first_air_date";
    }
}
