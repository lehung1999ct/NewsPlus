package myexam.th.lth.newsapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScroll extends RecyclerView.OnScrollListener {

    private static final String TAG = "PaginationScroll";
    LinearLayoutManager layoutManager;


    public PaginationScroll(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled( recyclerView, dx, dy );

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()){
            if ((visibleItemCount + firstVisibleItemPosition)>= totalItemCount
            && firstVisibleItemPosition >= 0 && totalItemCount >= getTotalPageCount()){
                loadMoreItem();
            }
            else {
                Log.d(TAG, "Page Scroll"+totalItemCount);
            }
        }else {

        }
    }

    public abstract void loadMoreItem();
    public abstract int getTotalPageCount();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();

}
