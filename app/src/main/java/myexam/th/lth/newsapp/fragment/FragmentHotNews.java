package myexam.th.lth.newsapp.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.BuildConfig;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.HotNewsAdapter;
import myexam.th.lth.newsapp.adapter.PaginationAdapter;
import myexam.th.lth.newsapp.adapter.PaginationScroll;
import myexam.th.lth.newsapp.adapter.RecyclerItemClickListener;
import myexam.th.lth.newsapp.model.GetNews;
import myexam.th.lth.newsapp.model.ResponseAllNews;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHotNews extends Fragment {

    private ShimmerFrameLayout shimmer;
    private String TAG = "HomeNews";

    private RecyclerView rvMain;
    private ProgressBar mProgressBar;

    NetworkAPI api;
//    HotNewsAdapter adapter;
    PaginationAdapter adapter;
    LinearLayoutManager manager;

    private ArrayList<GetNews> arrGetNews;

    //Loading... Pagination
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGE = 9;
    private int CURRENT_PAGE = PAGE_START;

    private int TOTAL_VIEW;

    public FragmentHotNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot_news, container, false);
        shimmer= view.findViewById( R.id.shimmerLoading );
        rvMain = (RecyclerView)view.findViewById( R.id.rvMain );
//        adapter = new HotNewsAdapter( arrGetNews, getContext() );

        adapter = new PaginationAdapter( getContext() );
        manager = new LinearLayoutManager( getContext(),RecyclerView.VERTICAL,false );

        rvMain.setItemAnimator( new DefaultItemAnimator() );

        rvMain.setLayoutManager( manager );
        rvMain.setAdapter( adapter );

        api = ServiceAPI.getNewsService( NetworkAPI.class );

        rvMain.addOnScrollListener( new PaginationScroll(manager) {

            @Override
            public void loadMoreItem() {
                isLoading=true;
                CURRENT_PAGE += 1;
                new Handler(  ).postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 2500 );
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        } );

        waitHandler();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmer.stopShimmer();
    }

    //Handler waiting load page.
    private void waitHandler(){
        loadingFirstPage();
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {
                shimmer.stopShimmer();
                shimmer.setVisibility( View.GONE );
                rvMain.setVisibility( View.VISIBLE );
            }
        }, 3500 );
    }

    private ArrayList<GetNews> fetchResultNews(Response<ResponseAllNews> response){
        ResponseAllNews responseAllNews = response.body();
        return responseAllNews.getArrCate();
    }

    //Get api server.
    private void loadingFirstPage(){

        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                getResponseNews().enqueue( new Callback<ResponseAllNews>() {
                    @Override
                    public void onResponse(Call<ResponseAllNews> call, Response<ResponseAllNews> response) {

                        if (response.body().getResult()==1){
                            ArrayList<GetNews> arrGetNews = fetchResultNews( response );
                            isLoading = true;
//                            mProgressBar.setVisibility( View.GONE );
                            adapter.addAll(arrGetNews);
                            if (CURRENT_PAGE<=TOTAL_PAGE){
                                adapter.addLoadingFooter();
                            }else {
                                isLastPage=true;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAllNews> call, Throwable t) {
                        Log.e( TAG, t.getMessage() );
                        Toast.makeText( getContext(), "Errors: "+ t.getMessage(),Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        } );

    }

    private void loadNextPage(){

        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                getResponseNews().enqueue( new Callback<ResponseAllNews>() {
                    @Override
                    public void onResponse(Call<ResponseAllNews> call, Response<ResponseAllNews> response) {
                        if (response.body().getResult()==1){

                            adapter.removeLoadingFooter();
                            isLoading=false;

                            ArrayList<GetNews> arrGetNews = fetchResultNews( response );

                            adapter.addAll(arrGetNews);

                            if (CURRENT_PAGE!=TOTAL_PAGE){
                                adapter.addLoadingFooter();
                            }else {
                                isLastPage=true;
                            }
                        }else {
                            Toast.makeText( getContext(),"END",Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAllNews> call, Throwable t) {
                        Log.e( TAG, t.getMessage() );
                        Toast.makeText( getContext(), "Errors: "+ t.getMessage(),Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        } );

    }

    private Call<ResponseAllNews> getResponseNews(){
        return api.getAllNews( CURRENT_PAGE );
    }
}
