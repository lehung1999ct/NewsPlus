package myexam.th.lth.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.model.GetNews;
import myexam.th.lth.newsapp.screen.DetailHotNewActivity;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<GetNews> arrNews;
    private Context context;

    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        arrNews = new ArrayList<>();
    }

    public ArrayList<GetNews> getNews() {
        return arrNews;
    }

    public void setNews(ArrayList<GetNews> arrNews) {
        this.arrNews = arrNews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_load, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate( R.layout.item_hot_news, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GetNews temp = arrNews.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH movieVH = (MovieVH) holder;

                movieVH.tvTitle.setText(temp.getmTitle());

//                movieVH.tvContent_hotNews.setText( temp.describeContents() );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    movieVH.tvContent_hotNews.setText( Html.fromHtml(temp.getmContent(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    movieVH.tvContent_hotNews.setText(Html.fromHtml(temp.getmContent()));
                }
//        holder.tvContent_hotNews.setText( news.getmContent() );
//        holder.tvViewer_hotNews.setText( news.getmViewCount() );
                try {
                    date = fmt.parse(temp.getmPostDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                movieVH.tvDateTime_hotNews.setText(new StringBuilder("Ngày ").append( fmtOut.format(date) ).toString());

                Glide.with( context )
                        .load( temp.getmThumb() )
                        .placeholder( R.drawable.image_icon_load )
                        .into( movieVH.ivImage_hotNews );
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return arrNews == null ? 0 : arrNews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == arrNews.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(GetNews mc) {
        arrNews.add(mc);
        notifyItemInserted(arrNews.size() - 1);
    }

    public void addAll(ArrayList<GetNews> mcList) {
        for (GetNews mc : mcList) {
            add(mc);
        }
    }

    public void remove(GetNews temp) {
        int position = arrNews.indexOf(temp);
        if (position > -1) {
            arrNews.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new GetNews());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = arrNews.size() - 1;
        GetNews item = getItem(position);

        if (item != null) {
            arrNews.remove(position);
            notifyItemRemoved(position);
        }
    }

    public GetNews getItem(int position) {
        return arrNews.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvDateTime_hotNews,tvContent_hotNews;
        private ImageView ivImage_hotNews;
        public MovieVH(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle_hotNews);
            tvDateTime_hotNews = (TextView) itemView.findViewById(R.id.tvDateTime_hotNews);
            tvContent_hotNews = (TextView) itemView.findViewById(R.id.tvContent_hotNews);

            ivImage_hotNews = (ImageView) itemView.findViewById(R.id.ivImage_hotNews);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    if (i!=RecyclerView.NO_POSITION){
                        GetNews temp = arrNews.get( i );

                        Intent intent = new Intent( context, DetailHotNewActivity.class );
                        intent.putExtra( "id_hot", temp.getmId() );
                        intent.putExtra( "title_hot", temp.getmTitle() );
                        intent.putExtra( "description_hot", temp.getmDescription() );
                        intent.putExtra( "thumb_hot", temp.getmThumb() );
                        intent.putExtra( "content_hot", temp.getmContent() );
                        intent.putExtra( "category_id_hot", String.valueOf( temp.getmCateId()  ) );
                        intent.putExtra( "post_date_hot", temp.getmPostDate() );
                        intent.putExtra( "views_count_hot", String.valueOf( temp.getmViewCount() ) );

//                        intent.addFlags( Integer.FLAG )
                        context.startActivity( intent );
//                        Toast.makeText( context, temp.getmId(), Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
