package myexam.th.lth.newsapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.FragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {


    private ViewPager vp;
    private TabLayout scrollTab;
    public FragmentNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_news, container, false );
        vp = (ViewPager)view.findViewById(R.id.vp);
        FragmentAdapter adapter = new FragmentAdapter(getFragmentManager(),getContext());
        vp.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.scrollTab);
        tabLayout.setupWithViewPager(vp);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        return view;
    }

}
