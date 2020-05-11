package myexam.th.lth.newsapp.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.dao.NewsDAO;
import myexam.th.lth.newsapp.model.BookmarkNews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailBookmarkActivity extends AppCompatActivity {

    private NewsDAO dao;
    private BookmarkNews temp;
    ArrayList<BookmarkNews> arrBookmark;

    Toolbar toolbar_detail_bookmark;

    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

    String id,title,description,thumb,post_date,views_count,content;
    String category_id;

    private TextView tvTitle_db,tvCalen_db,tvCate_db,tvContent_db;
    private ImageView ivDel_db,ivThumb_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_bookmark );

        toolbar_detail_bookmark = findViewById( R.id.toolbar_detail_bookmark );
        setSupportActionBar(toolbar_detail_bookmark);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle_db = findViewById( R.id.tvTitle_db );
        tvCalen_db = findViewById( R.id.tvCalen_db );
        tvCate_db = findViewById( R.id.tvCate_db );
        tvContent_db = findViewById( R.id.tvContent_db );

        ivThumb_db = findViewById( R.id.ivThumb_db );
        ivDel_db = findViewById( R.id.ivDel_db );

        dao = NewsDAO.getInstance( getBaseContext() );
        arrBookmark = dao.getBookmarkAll();

        Intent intent = getIntent();
        if (intent.hasExtra( "id" )){
            id = getIntent().getExtras().getString( "id" );
            title = getIntent().getExtras().getString( "title" );
            description = getIntent().getExtras().getString( "description" );
            thumb = getIntent().getExtras().getString( "thumb" );
            category_id = getIntent().getExtras().getString( "category_id" ) ;
            post_date = getIntent().getExtras().getString( "post_date" );
            content = getIntent().getExtras().getString( "content" );

            Glide.with( this )
                    .load( thumb )
                    .placeholder( R.drawable.image_icon_load )
                    .into( ivThumb_db );

//            Toast.makeText( this, category_id,Toast.LENGTH_SHORT ).show();

            tvTitle_db.setText( title );
            tvCalen_db.setText(post_date);
            tvCate_db.setText( category_id );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvContent_db.setText( Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvContent_db.setText(Html.fromHtml(content));
            }

        }

        ivDel_db.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<arrBookmark.size();i++){
                    final BookmarkNews temp = arrBookmark.get( i );
                    if (temp.getbId().equals( id )){
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailBookmarkActivity.this);
                mBuilder.setTitle( "Xoá tin tức xem sau ");
                mBuilder.setMessage( "Bạn có chắc chắn xoá tin tức này: "+temp.getbTitle() );
                mBuilder.setCancelable( false );
                mBuilder.setPositiveButton( "XOÁ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.DelBookmark( temp );
                        startActivity( new Intent( DetailBookmarkActivity.this,BookmarkActivity.class ) );
                        finish();
                    }
                } );
                mBuilder.setNegativeButton( "HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                } );
                mBuilder.create().show();
                    }else {
                        Toast.makeText( DetailBookmarkActivity.this, "K.O", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
