package como.sekolah.mypreloaddata.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "table_mahasiswa";

    public static final class MahasiswaColumns implements BaseColumns {
         static String NAMA = "nama";
         static String NIM = "nim";
    }

}
