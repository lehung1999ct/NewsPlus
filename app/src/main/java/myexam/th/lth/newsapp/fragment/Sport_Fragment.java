package myexam.th.lth.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.RecyclerItemClickListener;
import myexam.th.lth.newsapp.adapter.TrendAdapter;
import myexam.th.lth.newsapp.model.GetNews;
import myexam.th.lth.newsapp.model.ResponseAllNews;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.ServiceAPI;
import myexam.th.lth.newsapp.screen.DetailHotNewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sport_Fragment extends Fragment {

    RecyclerView rvSport;
    NetworkAPI api;
    ArrayList<GetNews> arrayList;
    private LinearLayoutManager manager;


    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    TrendAdapter adapter;
    private static String TAG = "Sport";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.sport_fragment,container,false);
        init();

        rvSport = (RecyclerView)view.findViewById( R.id.rvSport );
        rvSport.setItemAnimator( new DefaultItemAnimator() );
        manager = new LinearLayoutManager( getContext(),RecyclerView.VERTICAL,false );
        rvSport.setLayoutManager( manager );

        getNews();
        return view;
    }

    private void init(){
        api = ServiceAPI.getNewsService( NetworkAPI.class );
    }

    private void getNews(){
        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                Call<ResponseAllNews> call = api.getByCategory( 0,1 );
                call.enqueue( new Callback<ResponseAllNews>() {
                    @Override
                    public void onResponse(Call<ResponseAllNews> call, Response<ResponseAllNews> response) {
                        if (response.body().getResult().equals( 1 )){
                            arrayList = response.body().getArrCate();
                            adapter = new TrendAdapter( getContext(), arrayList );
                            rvSport.setAdapter( adapter );

                            rvSport.addOnItemTouchListener(new RecyclerItemClickListener( getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    GetNews temp = arrayList.get( position );
//                                    Toast.makeText( getContext(), "title: "+listNews.get( position ).getmTitle(), Toast.LENGTH_SHORT ).show();

                                    try {
                                        date = fmt.parse(temp.getmPostDate());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    String setDate = new StringBuilder("Ngày ").append( fmtOut.format(date) ).toString();
                                    Intent intent = new Intent( getContext(), DetailHotNewActivity.class );
                                    intent.putExtra( "id_hot", temp.getmId() );
                                    intent.putExtra( "title_hot", temp.getmTitle() );
                                    intent.putExtra( "description_hot", temp.getmDescription() );
                                    intent.putExtra( "thumb_hot", temp.getmThumb() );
                                    intent.putExtra( "content_hot", temp.getmContent() );
                                    intent.putExtra( "category_id_hot",temp.getmCateId() );
                                    intent.putExtra( "post_date_hot", setDate );
                                    intent.putExtra( "views_count_hot", String.valueOf( temp.getmViewCount() + 1 ) );
                                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity( intent );
                                }
                            } ));

                        }else {
                            Toast.makeText( getContext(), "END", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAllNews> call, Throwable t) {
                        Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                        Log.e( TAG, t.getMessage() );
                    }
                } );
            }
        } );
    }
}
