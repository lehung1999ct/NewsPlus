package myexam.th.lth.newsapp.adapter;

import android.content.Context;
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
import myexam.th.lth.newsapp.dao.NewsSeenDAO;
import myexam.th.lth.newsapp.model.NewsSeen;


public class SeenAdapter extends RecyclerView.Adapter<SeenAdapter.ViewHolder> {

    private Context context;
    ArrayList<NewsSeen> arrayList;

    NewsSeenDAO dao;

    public SeenAdapter(Context context, ArrayList<NewsSeen> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi;

        LayoutInflater inflater = LayoutInflater.from( context );
        vi = inflater.inflate( R.layout.item_seen,parent,false);

        dao=NewsSeenDAO.getInstance( context );
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsSeen temp = arrayList.get( position );

        holder.tvTitle_seen.setText( temp.getmTitle() );
        holder.tvDate_seen.setText( temp.getmDate() );
        holder.tvDesc_seen.setText( temp.getmDesc() );
        holder.tvCate_seen.setText( temp.getmCate() );

        Glide.with( context )
                .load( temp.getmThumb() )
                .placeholder( R.drawable.image_icon_load )
                .into( holder.ivSeen );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivSeen;
        private TextView tvTitle_seen,tvDesc_seen,tvCate_seen,tvDate_seen;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            tvTitle_seen = (TextView) itemView.findViewById(R.id.tvTitle_seen);
            tvDesc_seen = (TextView) itemView.findViewById( R.id.tvDesc_seen);
            tvCate_seen = (TextView) itemView.findViewById( R.id.tvCate_seen);
            tvDate_seen = (TextView) itemView.findViewById( R.id.tvDate_seen);

            ivSeen = (ImageView) itemView.findViewById(R.id.ivSeen);
        }
    }
}
