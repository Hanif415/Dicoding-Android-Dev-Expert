package como.sekolah.submission4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseMovieHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "moviedb";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseMovieContract.TABLE_NAME,
            DatabaseMovieContract.MovieColumns._ID,
            DatabaseMovieContract.MovieColumns.TITLE,
            DatabaseMovieContract.MovieColumns.OVERVIEW,
            DatabaseMovieContract.MovieColumns.POSTER,
            DatabaseMovieContract.MovieColumns.VOTE_AVERAGE,
            DatabaseMovieContract.MovieColumns.RELEASE_DATE
    );

    public DatabaseMovieHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMovieContract.TABLE_NAME);
        onCreate(db);
    }
}
