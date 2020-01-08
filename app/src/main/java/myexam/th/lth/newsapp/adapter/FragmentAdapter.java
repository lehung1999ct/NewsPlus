package myexam.th.lth.newsapp.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.fragment.Tab_Four;
import myexam.th.lth.newsapp.fragment.Tab_One;
import myexam.th.lth.newsapp.fragment.Tab_Three;
import myexam.th.lth.newsapp.fragment.Tab_Two;

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
                return new Tab_One();
            case 1:
                return new Tab_Two();
            case 2:
                return new Tab_Three();
            case 3:
                return new Tab_Four();
        }
        return null;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return context.getString(R.string.frag_one);
            case 1:
                return context.getString(R.string.frag_two);
            case 2:
                return context.getString(R.string.frag_three);
            case 3:
                return context.getString(R.string.frag_four);
        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
