package myexam.th.lth.newsapp.fragment;


import android.Manifest;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.HotNewsAdapter;
import myexam.th.lth.newsapp.adapter.RecyclerItemClickListener;
import myexam.th.lth.newsapp.screen.DetailHotNewActivity;
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
    HotNewsAdapter adapter;
    LinearLayoutManager manager;
    ArrayList<GetNews> arrayList;

//    Get Loction (GEO Coord)
    private FusedLocationProviderClient fuse;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private CompositeDisposable compositeDisposable;

    private TextView tvLocation,tvWeather,tvDateOfWeek,tvToday;

//    Format Date
    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
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
        tvDateOfWeek = view.findViewById( R.id.tvDateOfWeek );
        tvToday = view.findViewById( R.id.tvToday );

        api = ServiceAPI.getNewsService( NetworkAPI.class );

        rvMain = (RecyclerView)view.findViewById( R.id.rvMain );

        manager = new LinearLayoutManager( getContext(),RecyclerView.VERTICAL,false );

        rvMain.setItemAnimator( new DefaultItemAnimator() );

        rvMain.setLayoutManager( manager );


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
        getNews();
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {
                shimmer.stopShimmer();
                shimmer.setVisibility( View.GONE );
                rvMain.setVisibility( View.VISIBLE );
            }
        }, 3500 );
    }
    private void getNews(){
        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                Call<ResponseAllNews> call = api.getAllNews( 0 );
                call.enqueue( new Callback<ResponseAllNews>() {
                    @Override
                    public void onResponse(Call<ResponseAllNews> call, Response<ResponseAllNews> response) {
                        if (response.body().getResult().equals( 1 )){
                            arrayList = response.body().getArrCate();
                            adapter = new HotNewsAdapter( arrayList, getContext() );
                            rvMain.setAdapter( adapter );

                            rvMain.addOnItemTouchListener(new RecyclerItemClickListener( getContext(), new RecyclerItemClickListener.OnItemClickListener() {
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
                                    intent.putExtra( "views_count_hot", String.valueOf( temp.getmViewCount()  ) );
                                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity( intent );
                                }
                            } ) );

                        }else {
                            Toast.makeText( getContext(), "Hết.", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAllNews> call, Throwable t) {
                        Toast.makeText( getContext(), "error: "+t.getMessage(), Toast.LENGTH_SHORT ).show();
                        Log.e( TAG, t.getMessage() );
                    }
                } );
            }
        } );
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
                                tvDateOfWeek.setText( Constant.getDateOfWeek( responseWeather.getmDt() ) );
                                tvToday.setText( Constant.getDate( responseWeather.getmDt() ) );
                                if(responseWeather.getmName().equals( "Can Tho" )){
                                    tvLocation.setText( "Cần Thơ" );
                                }else if (responseWeather.getmName().equals( "Ho Chi Minh City" )){
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
