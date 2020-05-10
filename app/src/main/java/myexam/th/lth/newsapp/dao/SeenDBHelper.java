package myexam.th.lth.newsapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeenDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "seen";
    public static final String ID_NEWS_SEEN = "idnewseen";
    public static final String ID_S = "idsenn";
    public static final String TITLE_NEWS = "titlesenn";
    public static final String CATEGORY_NEWS = "categorysenn";
    public static final String DESCRIPTION_NEWS = "descriptionsenn";
    public static final String THUMB_NEWS = "thumbsenn";
    public static final String DATE_NEWS = "datenewssenn";

    private static final String DATABASE_NAME = "seen.db";

    private static final int DATABASE_VERSION = 2;

    public static final String TAG = "NEWS_SEEN";

    public SeenDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_S + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_NEWS_SEEN + " TEXT NOT NULL, " +
                TITLE_NEWS + " TEXT NOT NULL, " +
                CATEGORY_NEWS + " TEXT NOT NULL, " +
                DESCRIPTION_NEWS + " TEXT NOT NULL, " +
                DATE_NEWS + " TEXT NOT NULL, " +
                THUMB_NEWS + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_BOOKMARK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
