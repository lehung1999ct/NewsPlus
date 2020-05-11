package myexam.th.lth.newsapp.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.BookmarkAdapter;
import myexam.th.lth.newsapp.adapter.RecyclerItemClickListener;
import myexam.th.lth.newsapp.dao.NewsDAO;
import myexam.th.lth.newsapp.model.BookmarkNews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.NavigationMenuItemView;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    NewsDAO dao;
    ArrayList<BookmarkNews> arrayList;
    BookmarkNews temp;
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


        init();
        adapter = new BookmarkAdapter( this,arrayList );

        LinearLayoutManager manager = new LinearLayoutManager( this,RecyclerView.VERTICAL,false );

        rvBookmark.setLayoutManager( manager );
        rvBookmark.setAdapter( adapter );

        rvBookmark.addOnItemTouchListener( new RecyclerItemClickListener( this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                temp = arrayList.get( position );
//                Toast.makeText( BookmarkActivity.this, "title "+temp.getbTitle(), Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent( BookmarkActivity.this, DetailBookmarkActivity.class );
                intent.putExtra( "id", temp.getbId() );
                intent.putExtra( "title", temp.getbTitle() );
                intent.putExtra( "description", temp.getbDescription() );
                intent.putExtra( "thumb", temp.getbImage() );
                intent.putExtra( "content", temp.getbContent() );
                intent.putExtra( "category_id",temp.getbCate() );
                intent.putExtra( "post_date", temp.getbDate() );

                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity( intent );
                finish();
            }
        } ) );
    }

    public void init(){
        dao = NewsDAO.getInstance( getBaseContext() );
        arrayList=dao.getBookmarkAll();

    }
    @Override
    protected void onResume() {
        super.onResume();
        init();
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
