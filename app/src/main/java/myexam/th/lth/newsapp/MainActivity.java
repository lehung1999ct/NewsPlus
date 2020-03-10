package myexam.th.lth.newsapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import myexam.th.lth.newsapp.fragment.FragmentProfileUser;
import myexam.th.lth.newsapp.fragment.FragmentHotNews;
import myexam.th.lth.newsapp.fragment.FragmentNews;
import myexam.th.lth.newsapp.fragment.FragmentTrending;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        BottomNavigationView bot = (BottomNavigationView)findViewById( R.id.bottomNav );
        bot.setOnNavigationItemSelectedListener( this );

        loadFragment( new FragmentHotNews() );
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.com_facebook_button_icon)
                .setTitle(R.string.app_name)
                .setMessage("Thoát")
                .setPositiveButton("Có", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
