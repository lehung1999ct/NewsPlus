package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChangePass {

    @SerializedName( "user" )
    @Expose
    private String mUser;

    @SerializedName( "pass" )
    @Expose
    private String mPass;

    public GetChangePass() {
    }

    public GetChangePass(String mUser, String mPass) {
        this.mUser = mUser;
        this.mPass = mPass;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }
}
