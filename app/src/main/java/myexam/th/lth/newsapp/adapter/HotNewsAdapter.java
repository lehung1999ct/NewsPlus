package myexam.th.lth.newsapp.adapter;

import android.content.Context;
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

public class HotNewsAdapter extends RecyclerView.Adapter<HotNewsAdapter.ViewHolder> {

    private ArrayList<GetNews> arrNews;
    private Context context;

    private Date date;
    private SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    public HotNewsAdapter(ArrayList<GetNews> arrNews, Context context) {
        this.arrNews = arrNews;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi;
        LayoutInflater inflater = LayoutInflater.from( context );
        vi = inflater.inflate( R.layout.item_hot_news,parent,false);
        return new HotNewsAdapter.ViewHolder( vi );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetNews temp = arrNews.get(position);

        holder.tvTitle.setText(temp.getmTitle());
        holder.tvContent_hotNews.setText(temp.getmDescription());
        try {
            date = fmt.parse(temp.getmPostDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDateTime_hotNews.setText(new StringBuilder("Ngày ").append( fmtOut.format(date) ).toString());

        Glide.with( context )
                .load( temp.getmThumb() )
                .placeholder( R.drawable.image_icon_load )
                .into( holder.ivImage_hotNews );

        holder.tvViewer_hotNews.setText( new StringBuilder( "Lượt xem: " ).append( temp.getmViewCount() ).toString() );

        switch (temp.getmCateId()){
            case 1:
                holder.tvCate_hotNews.setText( "Thể Thao" );
                break;
            case 2:
                holder.tvCate_hotNews.setText( "Gia Đình" );
                break;
            case 3:
                holder.tvCate_hotNews.setText( "Thế Giới" );
                break;
            case 4:
                holder.tvCate_hotNews.setText( "Kinh Tế" );
                break;
            case 5:
                holder.tvCate_hotNews.setText( "Giải Trí" );
                break;
            case 6:
                holder.tvCate_hotNews.setText( "Công Nghệ" );
                break;
            case 7:
                holder.tvCate_hotNews.setText( "Nhà Đất" );
                break;
            case 8:
                holder.tvCate_hotNews.setText( "Thế Giới Xe" );
                break;
            case 9:
                holder.tvCate_hotNews.setText( "Kinh Doanh" );
                break;
            case 10:
                holder.tvCate_hotNews.setText( "KXD0" );
                break;
            case 11:
                holder.tvCate_hotNews.setText( "Sức Khỏe" );
                break;
            case 12:
                holder.tvCate_hotNews.setText( "KXD1" );
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvDateTime_hotNews,tvContent_hotNews,tvCate_hotNews,tvViewer_hotNews;
        private ImageView ivImage_hotNews;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle_hotNews);
            tvDateTime_hotNews = (TextView) itemView.findViewById(R.id.tvDateTime_hotNews);
            tvContent_hotNews = (TextView) itemView.findViewById(R.id.tvContent_hotNews);
            tvViewer_hotNews = (TextView) itemView.findViewById(R.id.tvViewer_hotNews);
            tvCate_hotNews = (TextView) itemView.findViewById(R.id.tvCate_hotNews);

            ivImage_hotNews = (ImageView) itemView.findViewById(R.id.ivImage_hotNews);
        }
    }
}
