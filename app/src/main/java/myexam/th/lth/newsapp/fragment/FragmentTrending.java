package myexam.th.lth.newsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrending extends Fragment {

    private String TAG = "Trends";

    private LinearLayoutManager manager;
    private TrendAdapter adapter;
    private ArrayList<GetNews> listNews;
    RecyclerView rvList_trends;
    TextView tvTitlePage;
    ShimmerFrameLayout shimmerFrameLayout;
    NetworkAPI api;

    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    public FragmentTrending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_trending, container, false );

        rvList_trends = (RecyclerView)view.findViewById( R.id.rvList_trends );
        shimmerFrameLayout = (ShimmerFrameLayout)view.findViewById( R.id.shimmerLoading_trend );
        tvTitlePage = (TextView)view.findViewById( R.id.sd1223 );
        rvList_trends.setItemAnimator( new DefaultItemAnimator() );

        init();
        manager = new LinearLayoutManager( getContext(),RecyclerView.VERTICAL,false );

        rvList_trends.setLayoutManager( manager );

        handlerWait();
        getListData();

        return view;
    }


    private void handlerWait() {
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility( View.GONE );
                tvTitlePage.setVisibility( View.VISIBLE );
                rvList_trends.setVisibility( View.VISIBLE );
            }
        }, 2000 );
    }

    private void init(){
        api = ServiceAPI.getNewsService( NetworkAPI.class );

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onStop() {
        super.onStop();
        shimmerFrameLayout.stopShimmer();
    }

    private void getListData(){

        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                Call<ResponseAllNews> call = api.getAllNews( 0 );
                call.enqueue( new Callback<ResponseAllNews>() {
                    @Override
                    public void onResponse(Call<ResponseAllNews> call, Response<ResponseAllNews> response) {
                        if (response.body().getResult().equals( 1 )){
                            listNews = response.body().getArrCate();
                            Collections.sort( listNews, GetNews.comparatorNews );
                            adapter = new TrendAdapter( getContext(), listNews);
                            rvList_trends.setAdapter( adapter );

                            rvList_trends.addOnItemTouchListener(new RecyclerItemClickListener( getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    GetNews temp = listNews.get( position );
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
                            } ) );

                            Log.i( TAG, "Load data Congratulations" );
//                            Toast.makeText( getContext(), "NO.1: "+ listNews.get( 0 ).getmTitle(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAllNews> call, Throwable t) {
                        Log.e(TAG,"Errors: "+t.getMessage());
                    }
                } );
            }
        } );

    }

}
