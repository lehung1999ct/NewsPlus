package myexam.th.lth.newsapp.fragment;


import android.Manifest;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
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

import androidx.fragment.app.Fragment;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import myexam.th.lth.newsapp.Constant;
import myexam.th.lth.newsapp.MainActivity;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.model.ResponseWeather;
import myexam.th.lth.newsapp.model.currentWeather.Main;
import myexam.th.lth.newsapp.model.currentWeather.Weather;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.RetrofitWeather;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHotNews extends Fragment {

    private ShimmerFrameLayout shimmer;
    private String TAG = "HomeNews";

    private FusedLocationProviderClient fuse;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private CompositeDisposable compositeDisposable;
    private NetworkAPI api;
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
        compositeDisposable = new CompositeDisposable(  );
//
        Retrofit retrofit = RetrofitWeather.getInstance();
        api = retrofit.create( NetworkAPI.class );


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot_news, container, false);
        shimmer = view.findViewById( R.id.shimmerLoading );
        tvLocation = view.findViewById( R.id.tvLocation );
        tvWeather = view.findViewById( R.id.tvWeather );

        gerCurrentWeather();

        return view;
    }

//    private void gerCurrentWeather() {
//        getActivity().runOnUiThread( new Runnable() {
//            @Override
//            public void run() {
//                compositeDisposable.add(
//                        api.getWheatherResult(
//                                String.valueOf( Constant.LOCATION_CURRENT.getLatitude() ),
//                                String.valueOf( Constant.LOCATION_CURRENT.getLongitude() ),
//                                Constant.API_KEY,
//                                "metric" )
//                                .subscribeOn( Schedulers.io() )
//                                .observeOn( AndroidSchedulers.mainThread() )
//                                .subscribe( new Consumer<ResponseWeather>() {
//                                    @Override
//                                    public void accept(ResponseWeather responseWeather) throws Exception {
//                                        mainTemp = responseWeather.getmMain();
//                                        tvLocation.setText( responseWeather.getmBase() );
//                                        tvWeather.setText( mainTemp.getTemp() );
////                        List<Weather> w3s = responseWeather.getmWeather();
//                                        Toast.makeText( getActivity(),responseWeather.getmMain().getTemp(),Toast.LENGTH_SHORT ).show();
//                                    }
//                                }, new Consumer<Throwable>() {
//                                    @Override
//                                    public void accept(Throwable throwable) throws Exception {
//                                        Toast.makeText( getActivity(),"error: "+ throwable.getMessage(), Toast.LENGTH_SHORT ).show();
//                                        Log.e( TAG, "error: "+ throwable.getMessage());
//                                    }
//                                } )
//                );
//            }
//        } );
//    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void gerCurrentWeather(){
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

                Constant.LOCATION_CURRENT = locationResult.getLastLocation();

//                Toast.makeText( getActivity(),"Latitude: " +locationResult.getLastLocation().getLatitude()+"Longitude: " +locationResult.getLastLocation().getLongitude(), Toast.LENGTH_SHORT ).show();
                dcm( locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
            }
        };
    }

    //

    private void dcm(double lat, double lon){
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
//                                mainTemp = responseWeather.getmMain();
                                int tempi = (int) Math.round( responseWeather.getmMain().getTemp() );
                                tvLocation.setText( responseWeather.getmName() );
                                tvWeather.setText( String.valueOf( tempi ) );
//                        List<Weather> w3s = responseWeather.getmWeather();
//                                Toast.makeText( getActivity(),String.valueOf( responseWeather.getmMain().getTemp() ),Toast.LENGTH_SHORT ).show();
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
