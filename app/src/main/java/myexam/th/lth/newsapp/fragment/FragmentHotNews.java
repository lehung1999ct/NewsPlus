package myexam.th.lth.newsapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.fragment.app.Fragment;
import myexam.th.lth.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHotNews extends Fragment {

    private ShimmerFrameLayout shimmer;
    private String TAG = "HomeNews";
    public FragmentHotNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot_news, container, false);
        shimmer= view.findViewById( R.id.shimmerLoading );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
