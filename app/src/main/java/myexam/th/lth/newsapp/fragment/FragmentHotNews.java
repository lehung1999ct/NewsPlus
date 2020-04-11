package myexam.th.lth.newsapp.fragment;


import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.List;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.MainActivity;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.PaginationAdapter;
import myexam.th.lth.newsapp.utils.PaginationScroll;
import myexam.th.lth.newsapp.model.GetNews;
import myexam.th.lth.newsapp.model.ResponseAllNews;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import myexam.th.lth.newsapp.Constant;
import myexam.th.lth.newsapp.model.ResponseWeather;
import myexam.th.lth.newsapp.model.currentWeather.Main;
import myexam.th.lth.newsapp.network.RetrofitWeather;
import retrofit2.Retrofit;

import static myexam.th.lth.newsapp.Constant.LOCATION_CURRENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHotNews extends Fragment {

    private ShimmerFrameLayout shimmer;
    private String TAG = "HomeNews";

    private RecyclerView rvMain;
    private ProgressBar mProgressBar;

    NetworkAPI api;
    PaginationAdapter adapter;
    LinearLayoutManager manager;

    //Loading... Pagination
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGE = 9;
    private int CURRENT_PAGE = PAGE_START;

    private int TOTAL_VIEW;

    private FusedLocationProviderClient fuse;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private CompositeDisposable compositeDisposable;
    private Main mainTemp;
    private TextView tvLocation,tvWeather;

    static FragmentHotNews instance;

    public static FragmentHotNews getInstance(){
        if(instance==null){
            instance = new FragmentHotNews();
        }
        return instance;
    }

    public FragmentHotNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot_news, container, false);
        compositeDisposable = new CompositeDisposable(  );
        gerCurrentWeather();
        shimmer= view.findViewById( R.id.shimmerLoading );
        tvWeather = view.findViewById( R.id.tvWeather );
        tvLocation = view.findViewById( R.id.tvLocation );

        rvMain = (RecyclerView)view.findViewById( R.id.rvMain );

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
//        shimmer.startShimmer();
        shimmer.stopShimmer();
        adapter.notifyDataSetChanged();
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


    private void gerCurrentWeather(){
        Dexter.withActivity( getActivity() )
                .withPermissions( Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION )
                .withListener( new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                buildLocationRequest();
                buildLocationCallBack();

                fuse = LocationServices.getFusedLocationProviderClient( getActivity() );
                fuse.requestLocationUpdates( locationRequest,locationCallback, Looper.myLooper() );
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                Toast.makeText( getActivity(),"Đã từ chối quyền truy cập",Toast.LENGTH_SHORT ).show();
            }
        } ).check();
    }

    //.. CHECK PERMISSION AND GET CURRENT WEATHER ..//

    //LOCATION REQUEST
    private void buildLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        locationRequest.setInterval( 5000 );
        locationRequest.setFastestInterval( 3000 );
        locationRequest.setSmallestDisplacement( 10.0f );
    }
    //LOCATION CALL BACK
    private void buildLocationCallBack(){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult( locationResult );

                LOCATION_CURRENT = locationResult.getLastLocation();
                dcm( LOCATION_CURRENT.getLatitude(), LOCATION_CURRENT.getLongitude());
            }
        };
    }

    //GET CURRENT WEATHER
    private void dcm(double lat, double lon){
        Retrofit retrofit = RetrofitWeather.getInstance();
        api = retrofit.create( NetworkAPI.class );
        compositeDisposable.add(
                api.getWheatherResult(
                        String.valueOf( lat ),
                        String.valueOf( lon ),
                        Constant.API_KEY,
                        "metric" )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe( new Consumer<ResponseWeather>() {
                            @Override
                            public void accept(ResponseWeather responseWeather) throws Exception {
//                                lam tron ˚C
                                int tempi = (int) Math.round( responseWeather.getmMain().getTemp() );
                                if(responseWeather.getmName().equals( "Can Tho" )){
                                    tvLocation.setText( "Cần Thơ" );
                                }else if (responseWeather.getmName().equals( "Ho Chi Minh" )){
                                    tvLocation.setText( "Hồ Chí Minh" );
                                }else if (responseWeather.getmName().equals( "Long Xuyen" )){
                                    tvLocation.setText( "Long Xuyên" );
                                }else {
                                    tvLocation.setText( responseWeather.getmName() );
                                }

                                tvWeather.setText( new StringBuilder( String.valueOf( tempi ) ).append( "˚C" ).toString() );
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText( getActivity(),"error: "+ throwable.getMessage(), Toast.LENGTH_SHORT ).show();
                                Log.e( TAG, "error: "+ throwable.getMessage());
                            }
                        } )
        );
    }
}
