package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNews {
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
    private String mCateId;

    @SerializedName( "type_id" )
    @Expose
    private String mTypeId;

    @SerializedName( "post_date" )
    @Expose
    private String mPostDate;

    @SerializedName( "views_count" )
    @Expose
    private Integer mViewCount;

    public GetNews() {
    }

    public GetNews(String mId, String mTitle, String mDescription, String mThumb, String mContent, String mCateId, String mTypeId, String mPostDate, Integer mViewCount) {
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

    public String getmCateId() {
        return mCateId;
    }

    public void setmCateId(String mCateId) {
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
}
