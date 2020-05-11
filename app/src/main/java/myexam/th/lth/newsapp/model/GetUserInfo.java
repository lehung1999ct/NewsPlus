package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserInfo {

    @SerializedName( "id" )
    @Expose
    private String mId;

    @SerializedName( "username" )
    @Expose
    private String mUsername;

    @SerializedName( "full_name" )
    @Expose
    private String mFullname;

    @SerializedName( "date_of_birth" )
    @Expose
    private String mDob;

    @SerializedName( "gender" )
    @Expose
    private String mGender;

    @SerializedName( "address" )
    @Expose
    private String mAddress;

    @SerializedName( "email" )
    @Expose
    private String mEmail;

    @SerializedName( "phone" )
    @Expose
    private String mPhone;

    @SerializedName( "avatar" )
    @Expose
    private String mAvt;

    @SerializedName( "role_id" )
    @Expose
    private String mRole;


    public GetUserInfo(String mId, String mUsername, String mFullname, String mDob, String mGender, String mAddress, String mEmail, String mPhone, String mAvt, String mRole) {
        this.mId = mId;
        this.mUsername = mUsername;
        this.mFullname = mFullname;
        this.mDob = mDob;
        this.mGender = mGender;
        this.mAddress = mAddress;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mAvt = mAvt;
        this.mRole = mRole;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmFullname() {
        return mFullname;
    }

    public void setmFullname(String mFullname) {
        this.mFullname = mFullname;
    }

    public String getmDob() {
        return mDob;
    }

    public void setmDob(String mDob) {
        this.mDob = mDob;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmAvt() {
        return mAvt;
    }

    public void setmAvt(String mAvt) {
        this.mAvt = mAvt;
    }

    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }
}
