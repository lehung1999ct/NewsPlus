package myexam.th.lth.newsapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import myexam.th.lth.newsapp.model.BookmarkNews;

public class BookmarkDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "bookmarksave";
    public static final String ID_BOOKMARK = "id";
    public static final String ID_NEWS = "idnews";
    public static final String TITLE_NEWS = "title";
    public static final String CONTENT_NEWS = "content";
    public static final String CATEGORY_NEWS = "category";
    public static final String DESCRIPTION_NEWS = "description";
    public static final String THUMB_NEWS = "thumb";
    public static final String DATE_NEWS = "datenews";

    private static final String DATABASE_NAME = "bookmark.db";

    private static final int DATABASE_VERSION = 2;

    public static final String TAG = "BOOKMARK";

    public BookmarkDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_BOOKMARK + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_NEWS + " INTEGER, " +
                TITLE_NEWS + " TEXT NOT NULL, " +
                CATEGORY_NEWS + " TEXT NOT NULL, " +
                DESCRIPTION_NEWS + " TEXT NOT NULL, " +
                CONTENT_NEWS + " TEXT NOT NULL, " +
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
