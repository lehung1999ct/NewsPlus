package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCategory {
    @SerializedName( "id" )
    @Expose
    private String mId;

    @SerializedName( "category_name" )
    @Expose
    private String mCateName;

    public GetCategory() {
    }

    public GetCategory(String mId, String mCateName) {
        this.mId = mId;
        this.mCateName = mCateName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCateName() {
        return mCateName;
    }

    public void setmCateName(String mCateName) {
        this.mCateName = mCateName;
    }
}
