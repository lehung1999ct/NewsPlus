package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAllNews {

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
    private ArrayList<GetNews> arrCate;

    @SerializedName( "total" )
    @Expose
    private Integer total;

    @SerializedName( "totalPerPage" )
    @Expose
    private Integer totalPerPage;

    public ResponseAllNews() {
    }

    public ResponseAllNews(Integer error, Integer result, String message, ArrayList<GetNews> arrCate, Integer total, Integer totalPerPage) {
        this.error = error;
        this.result = result;
        this.message = message;
        this.arrCate = arrCate;
        this.total = total;
        this.totalPerPage = totalPerPage;
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

    public ArrayList<GetNews> getArrCate() {
        return arrCate;
    }

    public void setArrCate(ArrayList<GetNews> arrCate) {
        this.arrCate = arrCate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPerPage() {
        return totalPerPage;
    }

    public void setTotalPerPage(Integer totalPerPage) {
        this.totalPerPage = totalPerPage;
    }
}
