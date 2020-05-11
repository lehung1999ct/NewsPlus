package myexam.th.lth.newsapp.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BookmarkNews implements Parcelable, Serializable {

    private String bId,bIdNews,bTitle,bContent,bDescription,bCate,bDate,bImage;

    public BookmarkNews() {
    }

    public BookmarkNews(String bId, String bIdNews, String bTitle, String bContent, String bDescription, String bCate, String bDate, String bImage) {
        this.bId = bId;
        this.bIdNews = bIdNews;
        this.bTitle = bTitle;
        this.bContent = bContent;
        this.bDescription = bDescription;
        this.bCate = bCate;
        this.bDate = bDate;
        this.bImage = bImage;
    }

    protected BookmarkNews(Parcel in) {
        bId = in.readString();
        bIdNews = in.readString();
        bTitle = in.readString();
        bContent = in.readString();
        bDescription = in.readString();
        bCate = in.readString();
        bDate = in.readString();
        bImage = in.readString();
    }

    public static final Creator<BookmarkNews> CREATOR = new Creator<BookmarkNews>() {
        @Override
        public BookmarkNews createFromParcel(Parcel in) {
            return new BookmarkNews( in );
        }

        @Override
        public BookmarkNews[] newArray(int size) {
            return new BookmarkNews[size];
        }
    };

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbIdNews() {
        return bIdNews;
    }

    public void setbIdNews(String bIdNews) {
        this.bIdNews = bIdNews;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }

    public String getbDescription() {
        return bDescription;
    }

    public void setbDescription(String bDescription) {
        this.bDescription = bDescription;
    }

    public String getbCate() {
        return bCate;
    }

    public void setbCate(String bCate) {
        this.bCate = bCate;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public String getbImage() {
        return bImage;
    }

    public void setbImage(String bImage) {
        this.bImage = bImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( bId );
        dest.writeString( bIdNews );
        dest.writeString( bTitle );
        dest.writeString( bContent );
        dest.writeString( bDescription );
        dest.writeString( bCate );
        dest.writeString( bDate );
        dest.writeString( bImage );
    }
}
