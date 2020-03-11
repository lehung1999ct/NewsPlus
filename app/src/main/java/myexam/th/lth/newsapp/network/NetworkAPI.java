package myexam.th.lth.newsapp.network;


import io.reactivex.Observable;
import myexam.th.lth.newsapp.model.ResponseWeather;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkAPI {

    @GET("weather")
    Observable<ResponseWeather> getWheatherResult(@Query( "lat" ) String latitude,
                                                  @Query( "lon" ) String longitude,
                                                  @Query( "appid" ) String appid,
                                                  @Query( "units" ) String unit);

}
