package myexam.th.lth.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.model.GetNews;
import myexam.th.lth.newsapp.screen.DetailHotNewActivity;

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.Holer> {

    private ArrayList<GetNews> list;
    private Context context;

    public TrendAdapter(Context context, ArrayList<GetNews> list){
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public TrendAdapter.Holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi;
        LayoutInflater inflater = LayoutInflater.from( context );
        vi = inflater.inflate(R.layout.item_trending,parent,false);
        return new Holer( vi );
    }

    @Override
    public void onBindViewHolder(@NonNull TrendAdapter.Holer holder, int position) {

        GetNews news = list.get( position );
        holder.tvTitle.setText( news.getmTitle() );
        holder.tvViewer_trending.setText( String.valueOf( news.getmViewCount() ) );
        holder.tvContent_hotNews.setText( news.getmDescription() );

        Glide.with( context ).load( news.getmThumb() ).placeholder( R.drawable.image_icon_load ).into( holder.ivImage_hotNews );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holer extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvDateTime_hotNews,tvContent_hotNews,tvCate_hotNews,tvViewer_trending;
        private ImageView ivImage_hotNews;

        public Holer(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById( R.id.tvTitle_trending);
            tvContent_hotNews = (TextView) itemView.findViewById(R.id.tvDesc_trending);
            tvViewer_trending = (TextView) itemView.findViewById(R.id.tvViewCount_trending);

            ivImage_hotNews = (ImageView) itemView.findViewById(R.id.ivImage_trending);
        }
    }
}
