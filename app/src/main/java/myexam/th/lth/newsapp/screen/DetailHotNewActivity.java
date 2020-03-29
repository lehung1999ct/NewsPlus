package myexam.th.lth.newsapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.dao.BookmarkDBHelper;
import myexam.th.lth.newsapp.dao.NewsDAO;
import myexam.th.lth.newsapp.model.BookmarkNews;
import myexam.th.lth.newsapp.model.ResponseServer;
import myexam.th.lth.newsapp.model.ResponseView;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailHotNewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Detail";
    private TextView tvTittle_detail, tvDateTime_detail, tvPoster_detail, tvContent_detail, tvViewCount_detail,tvCate_detail;
    private ImageView ivImage_detail,ic_bookmark;
    private AppBarLayout appbar_detail;
    private CollapsingToolbarLayout collapsing_toolbar_detail;

    private NewsDAO dao;
    private BookmarkNews temp;
    ArrayList<BookmarkNews> arrBookmark;
    private BookmarkDBHelper helper;

    NetworkAPI api;

    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    String id_hot,title_hot,description_hot,thumb_hot,category_id_hot,post_date_hot,views_count_hot,content_hot;

    //.. ..//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_hot_new );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();

        tvTittle_detail = findViewById( R.id.tvTitle_detail );
        tvDateTime_detail = findViewById( R.id.tvDateTime_detail );
        tvPoster_detail = findViewById( R.id.tvPoster_detail );
        tvContent_detail = findViewById( R.id.tvContent_detail );
        tvCate_detail = findViewById( R.id.tvCate_detail );
        tvViewCount_detail = findViewById( R.id.tvViewCount_detail );

        ivImage_detail = findViewById( R.id.ivImage_detail );
        ic_bookmark = findViewById( R.id.ic_bookmark );

        ic_bookmark.setOnClickListener( this );

        Intent intent = getIntent();
        if (intent.hasExtra( "id_hot" )){
            id_hot = getIntent().getExtras().getString( "id_hot" );
            title_hot = getIntent().getExtras().getString( "title_hot" );
            description_hot = getIntent().getExtras().getString( "description_hot" );
            thumb_hot = getIntent().getExtras().getString( "thumb_hot" );
            category_id_hot = getIntent().getExtras().getString( "category_id_hot" );
            post_date_hot = getIntent().getExtras().getString( "post_date_hot" );
            views_count_hot = getIntent().getExtras().getString( "views_count_hot" );
            content_hot = getIntent().getExtras().getString( "content_hot" );

            Glide.with( this )
                    .load( thumb_hot )
                    .placeholder( R.drawable.image_icon_load )
                    .into( ivImage_detail );

            tvCate_detail.setText(category_id_hot);
            tvViewCount_detail.setText( views_count_hot );
            tvTittle_detail.setText( title_hot );
            tvDateTime_detail.setText(post_date_hot);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvContent_detail.setText( Html.fromHtml(content_hot, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvContent_detail.setText(Html.fromHtml(content_hot));
            }

        }

        api = ServiceAPI.getNewsService( NetworkAPI.class );

        setVC( id_hot, views_count_hot ).enqueue( new Callback<ResponseView>() {
            @Override
            public void onResponse(Call<ResponseView> call, Response<ResponseView> response) {
                Toast.makeText( getBaseContext(), "Oke", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<ResponseView> call, Throwable t) {
                Toast.makeText( getBaseContext(), "Fail", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void init() {
        //.. connect sqlite
        dao = NewsDAO.getInstance( getBaseContext() );
        arrBookmark = dao.getBookmarkAll();
    }

    public void saveBookmark(String id_hot, String title_hot, String description_hot,String thumb_hot,String category_id_hot,String post_date_hot,String content_hot){

        BookmarkNews temp = new BookmarkNews(  );

        temp.setbIdNews( id_hot );
        temp.setbTitle( title_hot );
        temp.setbContent( content_hot );
        temp.setbDescription( description_hot );
        temp.setbCate( category_id_hot );
        temp.setbDate( post_date_hot );
        temp.setbImage( thumb_hot );

        long add = dao.insertProduct( temp );



    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_bookmark:
                if (checkEqual()>0){
                    Toast.makeText( getBaseContext(), "Đã thêm vào xem sau", Toast.LENGTH_SHORT ).show();
                    saveBookmark( id_hot,title_hot,description_hot,thumb_hot,category_id_hot,post_date_hot,content_hot );
                    finish();
                    startActivity( new Intent( this,BookmarkActivity.class ) );
                }else {
                    Toast.makeText( getBaseContext(), "Tin tức này đang nằm ở DS xem sau", Toast.LENGTH_LONG ).show();
                }
        }
    }

    private int checkEqual(){
        int val = 1;
        int i;
        for (i=0;i<arrBookmark.size();i++){
            temp = arrBookmark.get( i );
            if (temp.getbIdNews().equals( id_hot )){
                val -= 1;
            }else {
                val += 1 ;
            }
        }
        return val;
    }

    private Call<ResponseView> setVC(String id, String viess){

        return api.setViewCount( id,viess );
    }
}
