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
import myexam.th.lth.newsapp.model.currentWeather.Main;
import myexam.th.lth.newsapp.model.currentWeather.Sys;
import myexam.th.lth.newsapp.model.currentWeather.Weather;
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

import com.bumptech.glide.Glide;
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
    private TextView tvCity_weather,tvTemp_weather,tvSunRise_weather,tvSunSet_weather,tvHumidity_weather,tvDate_weather,tvDecs_weather;

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
        tvHumidity_weather = findViewById( R.id.tvHumidity_weather );
        tvDate_weather = findViewById( R.id.tvDate_weather );
        tvDecs_weather = findViewById( R.id.tvDecs_weather );
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
        locationRequest.setInterval( 3000 );
        locationRequest.setFastestInterval( 1000 );
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

                                Main main = responseWeather.getmMain();
                                Weather wea = responseWeather.getmWeather().get( 0 );
                                Sys sys = responseWeather.getmSys();
                                tvSunSet_weather.setText( new StringBuilder( "Hoàng hôn vào: " ).append( Constant.getSunset( sys.getSunset() ) ).toString() );
                                tvSunRise_weather.setText( new StringBuilder( "Bình mình vào: " ).append( Constant.getSunrise( sys.getSunrise() ) ).toString() );

                                tvDate_weather.setText( Constant.convertToDate( responseWeather.getmDt() ) );
                                tvHumidity_weather.setText( new StringBuilder( "Độ ẩm không khí: " ).append( main.getHumidity() ).append( "%" ) );

                                if (wea.getDescription().equals( "broken clouds" )){
                                    tvDecs_weather.setText( "Trời mây nhiều, rải rác" );
                                }else if (wea.getDescription().equals( "clear sky" )){
                                    tvDecs_weather.setText( "Nắng nhẹ, thời tiếc đẹp" );
                                }else if (wea.getDescription().equals( "shower rain" )){
                                    tvDecs_weather.setText( "Mưa rào nhẹ" );
                                }else if (wea.getDescription().equals( "few clouds" )){
                                    tvDecs_weather.setText( "Mây thưa thớt" );
                                }else if (wea.getDescription().equals( "rain" )){
                                    tvDecs_weather.setText( "Có mưa" );
                                }else if (wea.getDescription().equals( "thunderstorm" )){
                                    tvDecs_weather.setText( "Trời giông bão" );
                                }else if (wea.getDescription().equals( "snow" )){
                                    tvDecs_weather.setText( "Có tuyết rơi" );
                                } else if (wea.getDescription().equals( "mist" )){
                                    tvDecs_weather.setText( "Sương mù dày đặc" );
                                }else {
                                    tvDecs_weather.setText( wea.getDescription() );
                                }

                                Glide.with( WeatherActivity.this ).load( "https://openweathermap.org/img/wn/"+wea.getIcon()+"@2x.png" ).placeholder( R.drawable.ic_sun ).into( ivIcon_weather );
                                tvTemp_weather.setText( new StringBuilder( "Nhiệt độ: " ).append( main.getTemp() ).append( "˚C" ).toString() );
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
