package como.sekolah.submission4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperTv extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "tv_showdb";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContractTv.TABLE_NAME,
            DatabaseContractTv.TvShowColumns._ID,
            DatabaseContractTv.TvShowColumns.TITLE,
            DatabaseContractTv.TvShowColumns.OVERVIEW,
            DatabaseContractTv.TvShowColumns.POSTER,
            DatabaseContractTv.TvShowColumns.FIRST_AIR_DATE,
            DatabaseContractTv.TvShowColumns.VOTE_AVERAGE
    );

    public DatabaseHelperTv(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContractTv.TABLE_NAME);
        onCreate(db);
    }
}
