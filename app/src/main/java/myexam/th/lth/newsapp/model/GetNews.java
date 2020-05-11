package myexam.th.lth.newsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetNews implements Parcelable {
    @SerializedName( "id" )
    @Expose
    private String mId;

    @SerializedName( "title" )
    @Expose
    private String mTitle;

    @SerializedName( "description" )
    @Expose
    private String mDescription;

    @SerializedName( "thumb" )
    @Expose
    private String mThumb;

    @SerializedName( "content" )
    @Expose
    private String mContent;

    @SerializedName( "category_id" )
    @Expose
    private int mCateId;

    @SerializedName( "type_id" )
    @Expose
    private String mTypeId;

    @SerializedName( "post_date" )
    @Expose
    private String mPostDate;

    @SerializedName( "views_count" )
    @Expose
    private int mViewCount;

    public GetNews() {
    }

    public GetNews(String mId, String mTitle, String mDescription, String mThumb, String mContent, int mCateId, String mTypeId, String mPostDate, int mViewCount) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mThumb = mThumb;
        this.mContent = mContent;
        this.mCateId = mCateId;
        this.mTypeId = mTypeId;
        this.mPostDate = mPostDate;
        this.mViewCount = mViewCount;
    }

//    protected GetNews(Parcel in) {
//        mId = in.readString();
//        mTitle = in.readString();
//        mDescription = in.readString();
//        mThumb = in.readString();
//        mContent = in.readString();
//        mCateId = in.readString();
//        mTypeId = in.readString();
//        mPostDate = in.readString();
//        if (in.readByte() == 0) {
//            mViewCount = null;
//        } else {
//            mViewCount = in.readInt();
//        }
//    }

//    public static final Creator<GetNews> CREATOR = new Creator<GetNews>() {
//        @Override
//        public GetNews createFromParcel(Parcel in) {
//            return new GetNews( in );
//        }
//
//        @Override
//        public GetNews[] newArray(int size) {
//            return new GetNews[size];
//        }
//    };

    protected GetNews(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mThumb = in.readString();
        mContent = in.readString();
        mCateId = in.readInt();
        mTypeId = in.readString();
        mPostDate = in.readString();
        mViewCount = in.readInt();
    }

    public static final Creator<GetNews> CREATOR = new Creator<GetNews>() {
        @Override
        public GetNews createFromParcel(Parcel in) {
            return new GetNews( in );
        }

        @Override
        public GetNews[] newArray(int size) {
            return new GetNews[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmThumb() {
        return mThumb;
    }

    public void setmThumb(String mThumb) {
        this.mThumb = mThumb;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmCateId() {
        return mCateId;
    }

    public void setmCateId(int mCateId) {
        this.mCateId = mCateId;
    }

    public String getmTypeId() {
        return mTypeId;
    }

    public void setmTypeId(String mTypeId) {
        this.mTypeId = mTypeId;
    }

    public String getmPostDate() {
        return mPostDate;
    }

    public void setmPostDate(String mPostDate) {
        this.mPostDate = mPostDate;
    }

    public Integer getmViewCount() {
        return mViewCount;
    }

    public void setmViewCount(Integer mViewCount) {
        this.mViewCount = mViewCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( mId );
        dest.writeString( mTitle );
        dest.writeString( mDescription );
        dest.writeString( mThumb );
        dest.writeString( mContent );
        dest.writeInt( mCateId );
        dest.writeString( mTypeId );
        dest.writeString( mPostDate );
        dest.writeInt( mViewCount );
    }

public static Comparator<GetNews> comparatorNews = new Comparator<GetNews>() {
    @Override
    public int compare(GetNews o1, GetNews o2) {
        return Integer.compare( o2.mViewCount,o1.mViewCount );
    }
};
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString( mId );
//        dest.writeString( mTitle );
//        dest.writeString( mDescription );
//        dest.writeString( mThumb );
//        dest.writeString( mContent );
//        dest.writeString( mCateId );
//        dest.writeString( mTypeId );
//        dest.writeString( mPostDate );
//        if (mViewCount == null) {
//            dest.writeByte( (byte) 0 );
//        } else {
//            dest.writeByte( (byte) 1 );
//            dest.writeInt( mViewCount );
//        }
//    }
}
