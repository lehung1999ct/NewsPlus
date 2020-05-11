package myexam.th.lth.newsapp.network;

import myexam.th.lth.newsapp.model.ResponseAllNews;
import myexam.th.lth.newsapp.model.ResponseView;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import io.reactivex.Observable;
import myexam.th.lth.newsapp.model.ResponseWeather;
import retrofit2.http.Query;

public interface NetworkAPI {

    @GET("getAll/")
    Call<ResponseAllNews> getAllNews(@Query( "page" ) int index);

    @GET("getByCate/")
    Call<ResponseAllNews> getByCategory(@Query("page") int index,
                                       @Query("cate") int cate_id);

    //.. View Count ..//
    @POST("viewCount/")
    @FormUrlEncoded
    Call<ResponseView> setViewCount(@Field("id") String idNews,
                                    @Field("count") String viewCount);

    @GET("weather")
    Observable<ResponseWeather> getWheatherResult(@Query( "lat" ) String latitude,
                                                  @Query( "lon" ) String longitude,
                                                  @Query( "appid" ) String appid,
                                                  @Query( "units" ) String unit);
}
