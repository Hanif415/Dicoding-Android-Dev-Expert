package como.sekolah.submission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static como.sekolah.submission4.db.DatabaseContractTv.TABLE_NAME;

public class TvShowHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelperTv databaseHelperTv;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelperTv = new DatabaseHelperTv(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        database = databaseHelperTv.getWritableDatabase();
    }

    public void close() {
        databaseHelperTv.close();

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

    public static int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
