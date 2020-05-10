package myexam.th.lth.newsapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.dao.NewsDAO;
import myexam.th.lth.newsapp.model.BookmarkNews;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private Context context;
    ArrayList<BookmarkNews> arrBookmarkNews;

    NewsDAO dao;
    public BookmarkAdapter(Context context, ArrayList<BookmarkNews> arrBookmarkNews) {
        this.context = context;
        this.arrBookmarkNews = arrBookmarkNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi;
        LayoutInflater inflater = LayoutInflater.from( context );
        vi = inflater.inflate(R.layout.item_bookmark,parent,false);

        dao=NewsDAO.getInstance( context );
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final BookmarkNews temp = arrBookmarkNews.get( position );

        holder.tvBookmark_title.setText( temp.getbTitle() );
        holder.tvBookmark_desc.setText( temp.getbDescription() );
        holder.tvBookmark_cate.setText( temp.getbCate() );
        Glide.with( context )
                .load( temp.getbImage() )
                .placeholder( R.drawable.image_icon_load )
                .into( holder.ivBookmark_image );

//        holder.ivBookmark_delete.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
//                mBuilder.setTitle( "Xoa xem sau ");
//                mBuilder.setMessage( "may co chac chan khong ha thang ml nay: "+temp.getbId() );
//                mBuilder.setCancelable( false );
//                mBuilder.setPositiveButton( "XOA CMN LUON", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dao.DelBookmark( temp );
//                        BookmarkAdapter.super.notifyDataSetChanged();
//                    }
//                } );
//                mBuilder.setNegativeButton( "HUY", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        dialog.dismiss();
//                    }
//                } );
//                mBuilder.create().show();
//
//            }
//        } );

    }


    @Override
    public int getItemCount() {
        return arrBookmarkNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBookmark_image,ivBookmark_delete;
        private TextView tvBookmark_title,tvBookmark_desc,tvBookmark_cate;

        ViewHolder(@NonNull View vi) {
            super(vi);
            tvBookmark_title = (TextView) vi.findViewById(R.id.tvBookmark_title);
            tvBookmark_desc = (TextView)vi.findViewById( R.id.tvBookmark_desc);
            tvBookmark_cate = (TextView)vi.findViewById( R.id.tvBookmark_cate);

            ivBookmark_image = (ImageView)vi.findViewById(R.id.ivBookmark_image);
            ivBookmark_delete = (ImageView)vi.findViewById(R.id.ivBookmark_delete);

        }
    }
}
