package myexam.th.lth.newsapp.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import myexam.th.lth.newsapp.Constant;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.model.ResponseWeather;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.RetrofitWeather;
import retrofit2.Retrofit;

import android.Manifest;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static myexam.th.lth.newsapp.Constant.LOCATION_CURRENT;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private ImageView ivIcon_weather;
    private TextView tvCity_weather,tvTemp_weather,tvSunRise_weather,tvSunSet_weather;

    //api
    NetworkAPI api;

    //location request
    private FusedLocationProviderClient fuse;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private CompositeDisposable compositeDisposable;

    private Toolbar toolbar_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_weather );
        compositeDisposable = new CompositeDisposable(  );

        toolbar_weather = (Toolbar) findViewById(R.id.toolbar_weather);
        setSupportActionBar(toolbar_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( "Thời tiết hôm nay" );

        ivIcon_weather = findViewById( R.id.ivIcon_weather );

        tvCity_weather = findViewById( R.id.tvCity_weather );
        tvTemp_weather = findViewById( R.id.tvTemp_weather );
        tvSunRise_weather = findViewById( R.id.tvSunRise_weather );
        tvSunSet_weather = findViewById( R.id.tvSunSet_weather );

        getCurrentWeather();


    }
    private void getCurrentWeather(){
        Dexter.withActivity( this )
                .withPermissions( Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION )
                .withListener( new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        buildLocationRequest();
                        buildLocationCallBack();

                        fuse = LocationServices.getFusedLocationProviderClient( WeatherActivity.this );
                        fuse.requestLocationUpdates( locationRequest,locationCallback, Looper.myLooper() );
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText( WeatherActivity.this ,"Đã từ chối quyền truy cập",Toast.LENGTH_SHORT ).show();
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
//                                int tempi = (int) Math.round(  );
                                if(responseWeather.getmName().equals( "Can Tho" )){
                                    tvCity_weather.setText( "Thành Phố Cần Thơ" );
                                }else if (responseWeather.getmName().equals( "Ho Chi Minh" )){
                                    tvCity_weather.setText( "Thành Phố Hồ Chí Minh" );
                                }else if (responseWeather.getmName().equals( "Long Xuyen" )){
                                    tvCity_weather.setText( "Long Xuyên" );
                                }else {
                                    tvCity_weather.setText( responseWeather.getmName() );
                                }

                                tvTemp_weather.setText( new StringBuilder( String.valueOf( responseWeather.getmMain().getTemp() ) ).append( "˚C" ).toString() );
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
//                                Toast.makeText( getActivity(),"error: "+ throwable.getMessage(), Toast.LENGTH_SHORT ).show();
                                Log.e( TAG, "error: "+ throwable.getMessage());
                            }
                        } )
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
