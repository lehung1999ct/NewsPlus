package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseView {

    @SerializedName( "error" )
    @Expose
    private Integer error;

    @SerializedName( "result" )
    @Expose
    private Integer result;

    @SerializedName( "message" )
    @Expose
    private String message;

    public ResponseView() {
    }

    public ResponseView(Integer error, Integer result, String message) {
        this.error = error;
        this.result = result;
        this.message = message;
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
}
