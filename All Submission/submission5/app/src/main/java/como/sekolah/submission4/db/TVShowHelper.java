package como.sekolah.submission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static como.sekolah.submission4.db.DatabaseTVShowContract.TABLE_NAME;

public class TVShowHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseTVShowHelper databaseTvShowHelper;
    private static TVShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private TVShowHelper(Context context) {
        databaseTvShowHelper = new DatabaseTVShowHelper(context);
    }

    public static TVShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        database = databaseTvShowHelper.getWritableDatabase();
    }

    public void close() {
        databaseTvShowHelper.close();

        if (database.isOpen())
            database.close();
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
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
