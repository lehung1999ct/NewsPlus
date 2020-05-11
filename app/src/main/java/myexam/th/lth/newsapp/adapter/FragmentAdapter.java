package myexam.th.lth.newsapp.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.fragment.Bussines_Fragment;
import myexam.th.lth.newsapp.fragment.Car_Fragment;
import myexam.th.lth.newsapp.fragment.Eco_Fragment;
import myexam.th.lth.newsapp.fragment.Entertain_Fragment;
import myexam.th.lth.newsapp.fragment.Familia_Fragment;
import myexam.th.lth.newsapp.fragment.Hearth_Fragment;
import myexam.th.lth.newsapp.fragment.Housing_Fragment;
import myexam.th.lth.newsapp.fragment.Sport_Fragment;
import myexam.th.lth.newsapp.fragment.Tech_Fragment;
import myexam.th.lth.newsapp.fragment.World_Fragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private Context context;
    public FragmentAdapter(FragmentManager fm, Context ct) {
        super(fm);
        this.context = ct;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Sport_Fragment();
            case 1:
                return new World_Fragment();
            case 2:
                return new Tech_Fragment();
            case 4:
                return new Entertain_Fragment();
            case 3:
                return new Bussines_Fragment();
        }
        return null;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return context.getString(R.string.thethao);
            case 1:
                return context.getString(R.string.thegioi);
            case 2:
                return context.getString(R.string.congnghe);
            case 3:
                return context.getString(R.string.giaitri);
            case 4:
                return context.getString(R.string.kinhdoanh);

        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
