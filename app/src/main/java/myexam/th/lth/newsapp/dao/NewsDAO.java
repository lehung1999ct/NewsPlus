package myexam.th.lth.newsapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import myexam.th.lth.newsapp.model.BookmarkNews;

import static myexam.th.lth.newsapp.dao.BookmarkDBHelper.ID_BOOKMARK;

public class NewsDAO {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private static NewsDAO instance;
    private static String TAG = "NEWSDAO";

    public NewsDAO(Context context) {
        BookmarkDBHelper sqlHelper = new BookmarkDBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public void Donee(){
        Log.i( TAG, "Database Closed");
        dbHelper.close();
    }

    public static NewsDAO getInstance(Context context){

        if(instance == null){
            instance = new NewsDAO(context);
        }
        return instance;
    }

    //.. CURSOR ..//
    public ArrayList<BookmarkNews> getNewsData( String sql, String...SelectionArgs ){
        ArrayList<BookmarkNews> result = new ArrayList<>();
        Cursor c = db.rawQuery( sql, SelectionArgs );

        BookmarkNews temp;

        while ( c.moveToNext( ) ) {
            temp = new BookmarkNews();
            temp.setbId( c.getString( c.getColumnIndex( ID_BOOKMARK ) ) );
            temp.setbIdNews( c.getString( c.getColumnIndex( BookmarkDBHelper.ID_NEWS ) ) );
            temp.setbTitle( c.getString( c.getColumnIndex( BookmarkDBHelper.TITLE_NEWS ) ) );
            temp.setbContent( c.getString( c.getColumnIndex( BookmarkDBHelper.CONTENT_NEWS ) ) );
            temp.setbDescription( c.getString( c.getColumnIndex( BookmarkDBHelper.DESCRIPTION_NEWS ) ) );
            temp.setbCate( c.getString( c.getColumnIndex( BookmarkDBHelper.CATEGORY_NEWS ) ) );
            temp.setbDate( c.getString( c.getColumnIndex( BookmarkDBHelper.DATE_NEWS ) ) );
            temp.setbImage( c.getString( c.getColumnIndex( BookmarkDBHelper.THUMB_NEWS ) ) );


            result.add(temp);

        }

        return result;
    }

    //..GET ALL
    public ArrayList<BookmarkNews> getBookmarkAll(){
        String sql = " SELECT * FROM " + BookmarkDBHelper.TABLE_NAME;
        return getNewsData(sql);
    }

    //      FIND BY ID
    public BookmarkNews getByID(String bIdNews){
        String sql = " SELECT * FROM " + BookmarkDBHelper.TABLE_NAME+ " WHERE idnews = ? ";
        ArrayList<BookmarkNews> list = getNewsData(sql, bIdNews);
        return list.get(0);
    }

    //..ADD BOOKMARK
    public long insertProduct(BookmarkNews temp){
        ContentValues values = new ContentValues();

//        values.put( ID_BOOKMARK,temp.getbId() );
        values.put( BookmarkDBHelper.ID_NEWS,temp.getbIdNews() );
        values.put( BookmarkDBHelper.TITLE_NEWS,temp.getbTitle() );
        values.put( BookmarkDBHelper.CONTENT_NEWS,temp.getbContent() );
        values.put( BookmarkDBHelper.DESCRIPTION_NEWS,temp.getbDescription() );
        values.put( BookmarkDBHelper.CATEGORY_NEWS,temp.getbCate() );
        values.put( BookmarkDBHelper.DATE_NEWS,temp.getbDate() );
        values.put( BookmarkDBHelper.THUMB_NEWS,temp.getbImage() );

        return db.insert(BookmarkDBHelper.TABLE_NAME,null, values );
    }

    //..DELETE BOOKMARK
    public int DelBookmark(BookmarkNews temp){

        return db.delete( BookmarkDBHelper.TABLE_NAME,"id = ?",
                new String[ ] {
                        temp.getbId()
        } );

    }
}
