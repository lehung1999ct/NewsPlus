package myexam.th.lth.newsapp.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.BookmarkAdapter;
import myexam.th.lth.newsapp.dao.NewsDAO;
import myexam.th.lth.newsapp.model.BookmarkNews;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.internal.NavigationMenuItemView;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    private BookmarkNews ObjectNews;
    NewsDAO dao;
    ArrayList<BookmarkNews> arrayList;
    private RecyclerView rvBookmark;
    private BookmarkAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bookmark );

        rvBookmark = findViewById( R.id.rvBookmark );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bookmark);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( "Dánh Sách Xem Sau" );

        dao = NewsDAO.getInstance( getBaseContext() );
        arrayList=dao.getBookmarkAll();

        adapter = new BookmarkAdapter( this,arrayList );

        LinearLayoutManager manager = new LinearLayoutManager( this,RecyclerView.VERTICAL,false );

        rvBookmark.setLayoutManager( manager );
        rvBookmark.setAdapter( adapter );
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
