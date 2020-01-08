package myexam.th.lth.newsapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import myexam.th.lth.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrending extends Fragment {


    public FragmentTrending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_trending, container, false );
    }

}
