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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.model.GetNews;
import myexam.th.lth.newsapp.model.ResponseServer;
import myexam.th.lth.newsapp.network.NetworkAPI;
import myexam.th.lth.newsapp.network.ServiceAPI;
import myexam.th.lth.newsapp.screen.DetailHotNewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                movieVH.tvContent_hotNews.setText(temp.getmDescription());
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

                movieVH.tvViewer_hotNews.setText( new StringBuilder( "Lượt xem: " ).append( temp.getmViewCount() ).toString() );

//                for (int i=0;i<=)

                switch (temp.getmCateId()){
                    case 1:
                        movieVH.tvCate_hotNews.setText( "Thể Thao" );
                        break;
                    case 2:
                        movieVH.tvCate_hotNews.setText( "Gia Đình" );
                        break;
                    case 3:
                        movieVH.tvCate_hotNews.setText( "Thế Giới" );
                        break;
                    case 4:
                        movieVH.tvCate_hotNews.setText( "Kinh Tế" );
                        break;
                    case 5:
                        movieVH.tvCate_hotNews.setText( "Giải Trí" );
                        break;
                    case 6:
                        movieVH.tvCate_hotNews.setText( "Công Nghệ" );
                        break;
                    case 7:
                        movieVH.tvCate_hotNews.setText( "Nhà Đất" );
                        break;
                    case 8:
                        movieVH.tvCate_hotNews.setText( "Thế Giới Xe" );
                        break;
                    case 9:
                        movieVH.tvCate_hotNews.setText( "Kinh Doanh" );
                        break;
                    case 10:
                        movieVH.tvCate_hotNews.setText( "KXD0" );
                        break;
                    case 11:
                        movieVH.tvCate_hotNews.setText( "Sức Khỏe" );
                        break;
                    case 12:
                        movieVH.tvCate_hotNews.setText( "KXD1" );
                        break;
                }
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
        private TextView tvTitle,tvDateTime_hotNews,tvContent_hotNews,tvCate_hotNews,tvViewer_hotNews;
        private ImageView ivImage_hotNews;
        public MovieVH(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle_hotNews);
            tvDateTime_hotNews = (TextView) itemView.findViewById(R.id.tvDateTime_hotNews);
            tvContent_hotNews = (TextView) itemView.findViewById(R.id.tvContent_hotNews);
            tvViewer_hotNews = (TextView) itemView.findViewById(R.id.tvViewer_hotNews);
            tvCate_hotNews = (TextView) itemView.findViewById(R.id.tvCate_hotNews);

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
                        intent.putExtra( "category_id_hot", tvCate_hotNews.getText() );
                        intent.putExtra( "post_date_hot", tvDateTime_hotNews.getText() );
                        intent.putExtra( "views_count_hot", String.valueOf( temp.getmViewCount() + 1 ) );
                        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
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
