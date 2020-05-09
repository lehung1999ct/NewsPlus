package myexam.th.lth.newsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

public class NewsSeen implements Parcelable, Serializable {
    private String mId,mIdNews,mTitle,mThumb,mDesc,mDate,mCate;

    public NewsSeen() {
    }

    public NewsSeen(String mId, String mIdNews, String mTitle, String mThumb, String mDesc, String mDate, String mCate) {
        this.mId = mId;
        this.mIdNews = mIdNews;
        this.mTitle = mTitle;
        this.mThumb = mThumb;
        this.mDesc = mDesc;
        this.mDate = mDate;
        this.mCate = mCate;
    }

    protected NewsSeen(Parcel in) {
        mId = in.readString();
        mIdNews = in.readString();
        mTitle = in.readString();
        mThumb = in.readString();
        mDesc = in.readString();
        mDate = in.readString();
        mCate = in.readString();
    }


    public static Comparator<NewsSeen> comparatorNews = new Comparator<NewsSeen>() {
        @Override
        public int compare(NewsSeen o1, NewsSeen o2) {
            return o2.getmId().compareTo( o1.mId );
        }
    };

    public static final Creator<NewsSeen> CREATOR = new Creator<NewsSeen>() {
        @Override
        public NewsSeen createFromParcel(Parcel in) {
            return new NewsSeen( in );
        }

        @Override
        public NewsSeen[] newArray(int size) {
            return new NewsSeen[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmIdNews() {
        return mIdNews;
    }

    public void setmIdNews(String mIdNews) {
        this.mIdNews = mIdNews;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmThumb() {
        return mThumb;
    }

    public void setmThumb(String mThumb) {
        this.mThumb = mThumb;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmCate() {
        return mCate;
    }

    public void setmCate(String mCate) {
        this.mCate = mCate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( mId );
        dest.writeString( mIdNews );
        dest.writeString( mTitle );
        dest.writeString( mThumb );
        dest.writeString( mDesc );
        dest.writeString( mDate );
        dest.writeString( mCate );
    }
}
