package myexam.th.lth.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import myexam.th.lth.newsapp.model.currentWeather.Clouds;
import myexam.th.lth.newsapp.model.currentWeather.Coord;
import myexam.th.lth.newsapp.model.currentWeather.Main;
import myexam.th.lth.newsapp.model.currentWeather.Sys;
import myexam.th.lth.newsapp.model.currentWeather.Weather;
import myexam.th.lth.newsapp.model.currentWeather.Wind;

public class ResponseWeather {
    private Coord mCoord;

    @SerializedName( "weather" )
    @Expose
    private List<Weather> mWeather;

    private String mBase;

    @SerializedName( "main" )
    @Expose
    private Main mMain;

    private int mVisibility;
    private Wind mWind;
    private Clouds mClouds;

    @SerializedName( "dt" )
    @Expose
    private int mDt;

    @SerializedName( "sys" )
    @Expose
    private Sys mSys;

    private int mTimezone;
    private int mId;

    @SerializedName( "name" )
    @Expose
    private String mName;
    private int mCod;

    public ResponseWeather() {
    }

    public ResponseWeather(Coord mCoord, List<Weather> mWeather, String mBase, Main mMain, int mVisibility, Wind mWind, Clouds mClouds, int mDt, Sys mSys, int mTimezone, int mId, String mName, int mCod) {
        this.mCoord = mCoord;
        this.mWeather = mWeather;
        this.mBase = mBase;
        this.mMain = mMain;
        this.mVisibility = mVisibility;
        this.mWind = mWind;
        this.mClouds = mClouds;
        this.mDt = mDt;
        this.mSys = mSys;
        this.mTimezone = mTimezone;
        this.mId = mId;
        this.mName = mName;
        this.mCod = mCod;
    }

    public Coord getmCoord() {
        return mCoord;
    }

    public void setmCoord(Coord mCoord) {
        this.mCoord = mCoord;
    }

    public List<Weather> getmWeather() {
        return mWeather;
    }

    public void setmWeather(List<Weather> mWeather) {
        this.mWeather = mWeather;
    }

    public String getmBase() {
        return mBase;
    }

    public void setmBase(String mBase) {
        this.mBase = mBase;
    }

    public Main getmMain() {
        return mMain;
    }

    public void setmMain(Main mMain) {
        this.mMain = mMain;
    }

    public int getmVisibility() {
        return mVisibility;
    }

    public void setmVisibility(int mVisibility) {
        this.mVisibility = mVisibility;
    }

    public Wind getmWind() {
        return mWind;
    }

    public void setmWind(Wind mWind) {
        this.mWind = mWind;
    }

    public Clouds getmClouds() {
        return mClouds;
    }

    public void setmClouds(Clouds mClouds) {
        this.mClouds = mClouds;
    }

    public int getmDt() {
        return mDt;
    }

    public void setmDt(int mDt) {
        this.mDt = mDt;
    }

    public Sys getmSys() {
        return mSys;
    }

    public void setmSys(Sys mSys) {
        this.mSys = mSys;
    }

    public int getmTimezone() {
        return mTimezone;
    }

    public void setmTimezone(int mTimezone) {
        this.mTimezone = mTimezone;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmCod() {
        return mCod;
    }

    public void setmCod(int mCod) {
        this.mCod = mCod;
    }
}
