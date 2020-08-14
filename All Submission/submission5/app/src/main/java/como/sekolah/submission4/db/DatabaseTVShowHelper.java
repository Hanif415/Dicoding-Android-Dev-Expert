package como.sekolah.submission4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseTVShowHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "tv_showdb";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseTVShowContract.TABLE_NAME,
            DatabaseTVShowContract.TvShowColumns._ID,
            DatabaseTVShowContract.TvShowColumns.TITLE,
            DatabaseTVShowContract.TvShowColumns.OVERVIEW,
            DatabaseTVShowContract.TvShowColumns.POSTER,
            DatabaseTVShowContract.TvShowColumns.FIRST_AIR_DATE,
            DatabaseTVShowContract.TvShowColumns.VOTE_AVERAGE
    );

    public DatabaseTVShowHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseTVShowContract.TABLE_NAME);
        onCreate(db);
    }
}
