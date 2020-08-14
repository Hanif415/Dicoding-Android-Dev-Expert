package como.sekolah.submission4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "moviedb";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.POSTER,
            DatabaseContract.MovieColumns.VOTE_AVERAGE,
            DatabaseContract.MovieColumns.RELEASE_DATE
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
