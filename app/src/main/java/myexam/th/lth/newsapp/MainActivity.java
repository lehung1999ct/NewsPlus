package myexam.th.lth.newsapp;

import android.Manifest;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import myexam.th.lth.newsapp.fragment.FragmentProfileUser;
import myexam.th.lth.newsapp.fragment.FragmentHotNews;
import myexam.th.lth.newsapp.fragment.FragmentNews;
import myexam.th.lth.newsapp.fragment.FragmentTrending;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FusedLocationProviderClient fuse;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        BottomNavigationView bot = (BottomNavigationView)findViewById( R.id.bottomNav );
        bot.setOnNavigationItemSelectedListener( this );

        loadFragment( new FragmentHotNews() );

        gerCurrentWeather();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_hot:
                fragment = new FragmentHotNews();
                loadFragment(fragment);
                return true;
            case R.id.navigation_trend:
                fragment = new FragmentTrending();
                loadFragment(fragment);
                return true;
            case R.id.navigation_news:
                fragment = new FragmentNews();
                loadFragment(fragment);
                return true;
            case R.id.navigation_profile:
                fragment = new FragmentProfileUser();
                loadFragment(fragment);
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
    public void gerCurrentWeather(){
        Dexter.withActivity( this )
                .withPermissions( Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION )
                .withListener( new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        buildLocationRequest();
                        buildLocationCallBack();

                        fuse = LocationServices.getFusedLocationProviderClient( MainActivity.this );
                        fuse.requestLocationUpdates( locationRequest,locationCallback, Looper.myLooper() );
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText( MainActivity.this,"Đã từ chối quyền truy cập",Toast.LENGTH_SHORT ).show();
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

//                Toast.makeText( MainActivity.this,"Latitude: " +locationResult.getLastLocation().getLatitude()+"Longitude: " +locationResult.getLastLocation().getLongitude(), Toast.LENGTH_SHORT ).show();
            }
        };
    }

}
