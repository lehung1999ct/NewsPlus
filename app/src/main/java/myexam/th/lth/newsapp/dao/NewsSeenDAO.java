package myexam.th.lth.newsapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import myexam.th.lth.newsapp.model.BookmarkNews;
import myexam.th.lth.newsapp.model.NewsSeen;

import static myexam.th.lth.newsapp.dao.BookmarkDBHelper.ID_BOOKMARK;
import static myexam.th.lth.newsapp.dao.SeenDBHelper.ID_S;

public class NewsSeenDAO {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private static NewsSeenDAO instance;
    private static String TAG = "NEWSDAO";

    public NewsSeenDAO(Context context) {
        SeenDBHelper sqlHelper = new SeenDBHelper(context);
        db = sqlHelper.getWritableDatabase();
    }

    public void Donee(){
        Log.i( TAG, "Database Closed");
        dbHelper.close();
    }

    public static NewsSeenDAO getInstance(Context context){

        if(instance == null){
            instance = new NewsSeenDAO(context);
        }
        return instance;
    }

    //.. CURSOR ..//
    public ArrayList<NewsSeen> getNewsData( String sql, String...SelectionArgs ){
        ArrayList<NewsSeen> result = new ArrayList<>();
        Cursor c = db.rawQuery( sql, SelectionArgs );

        NewsSeen temp;

        while ( c.moveToNext( ) ) {
            temp = new NewsSeen();
            temp.setmId( c.getString( c.getColumnIndex( ID_S ) ) );
            temp.setmTitle( c.getString( c.getColumnIndex( SeenDBHelper.TITLE_NEWS ) ) );
            temp.setmDesc( c.getString( c.getColumnIndex( SeenDBHelper.DESCRIPTION_NEWS ) ) );
            temp.setmCate( c.getString( c.getColumnIndex( SeenDBHelper.CATEGORY_NEWS ) ) );
            temp.setmDate( c.getString( c.getColumnIndex( SeenDBHelper.DATE_NEWS ) ) );
            temp.setmThumb( c.getString( c.getColumnIndex( SeenDBHelper.THUMB_NEWS ) ) );

            result.add(temp);

        }

        return result;
    }

    //..GET ALL
    public ArrayList<NewsSeen> getBookmarkAll(){
        String sql = " SELECT * FROM " + SeenDBHelper.TABLE_NAME;
        return getNewsData(sql);
    }

    //      FIND BY ID
    public NewsSeen getByID(String bIdNews){
        String sql = " SELECT * FROM " + SeenDBHelper.TABLE_NAME+ " WHERE idnewseen = ? ";
        ArrayList<NewsSeen> list = getNewsData(sql, bIdNews);
        return list.get(0);
    }

    //..ADD BOOKMARK
    public long insertProduct(NewsSeen temp){
        ContentValues values = new ContentValues();

//        values.put( ID_BOOKMARK,temp.getbId() );
        values.put( SeenDBHelper.ID_NEWS_SEEN,temp.getmIdNews() );
        values.put( SeenDBHelper.TITLE_NEWS,temp.getmTitle() );
        values.put( SeenDBHelper.DESCRIPTION_NEWS,temp.getmDesc() );
        values.put( SeenDBHelper.CATEGORY_NEWS,temp.getmCate() );
        values.put( SeenDBHelper.DATE_NEWS,temp.getmDate() );
        values.put( SeenDBHelper.THUMB_NEWS,temp.getmThumb() );

        return db.insert(SeenDBHelper.TABLE_NAME,null, values );
    }

    //..DELETE BOOKMARK
    public int DelSeen(String id){

        return db.delete( SeenDBHelper.TABLE_NAME,"idnewseen = ?",
                new String[ ] {
                        id
        } );

    }
}
