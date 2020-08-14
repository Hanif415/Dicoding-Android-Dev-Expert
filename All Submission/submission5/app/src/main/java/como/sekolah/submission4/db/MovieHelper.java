package como.sekolah.submission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static como.sekolah.submission4.db.DatabaseMovieContract.MovieColumns.TITLE;
import static como.sekolah.submission4.db.DatabaseMovieContract.TABLE_NAME;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseMovieHelper databaseMovieHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

//    constractor
      private MovieHelper(Context context) {
        databaseMovieHelper = new DatabaseMovieHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseMovieHelper.getWritableDatabase();
    }

    public void close() {
        databaseMovieHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                TITLE + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
