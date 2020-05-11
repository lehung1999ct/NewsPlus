package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseServer {

    @SerializedName( "error" )
    @Expose
    private Integer error;

    @SerializedName( "result" )
    @Expose
    private Integer result;

    @SerializedName( "message" )
    @Expose
    private String message;

    @SerializedName( "data" )
    @Expose
    private ArrayList<GetCategory> arrCate;

    @SerializedName( "data" )
    @Expose
    private ArrayList<GetUserInfo> arrUser;

    @SerializedName( "data" )
    @Expose
    private ArrayList<GetNews> arrNews;

    public ResponseServer() {
    }

    public ResponseServer(Integer error, Integer result, String message, ArrayList<GetNews> arrNews) {
        this.error = error;
        this.result = result;
        this.message = message;
        this.arrNews = arrNews;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<GetCategory> getArrCate() {
        return arrCate;
    }

    public void setArrCate(ArrayList<GetCategory> arrCate) {
        this.arrCate = arrCate;
    }

    public ArrayList<GetUserInfo> getArrUser() {
        return arrUser;
    }

    public void setArrUser(ArrayList<GetUserInfo> arrUser) {
        this.arrUser = arrUser;
    }

    public ArrayList<GetNews> getArrNews() {
        return arrNews;
    }

    public void setArrNews(ArrayList<GetNews> arrNews) {
        this.arrNews = arrNews;
    }
}
