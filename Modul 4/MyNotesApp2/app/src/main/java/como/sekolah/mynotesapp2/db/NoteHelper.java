package como.sekolah.mynotesapp2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import como.sekolah.mynotesapp2.DatabaseHelper;

import static android.provider.BaseColumns._ID;
import static como.sekolah.mynotesapp2.db.DatabaseContract.TABLE_NAME;

public class NoteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static NoteHelper INSTANCE;

    private static SQLiteDatabase database;

//    constractor
    public NoteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

//    method 1
    public static NoteHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class) {
                INSTANCE = new NoteHelper(context);
            }
        }
        return INSTANCE;
    }

//    method 2
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

//    method 3
    public void close() {
        databaseHelper.close();

        if (database.isOpen()){
            database.close();
        }
    }

//    method 4
    public Cursor queyAll() {
        return database.query(
          DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

//    method 5
    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

//    method 6
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

//    method 7
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

//    method 8
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
