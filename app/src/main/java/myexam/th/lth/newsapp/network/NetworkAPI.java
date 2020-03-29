package myexam.th.lth.newsapp.network;


import myexam.th.lth.newsapp.model.ResponseAllNews;
import myexam.th.lth.newsapp.model.ResponseServer;
import myexam.th.lth.newsapp.model.ResponseView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkAPI {

    @GET("getAll/")
    Call<ResponseAllNews> getAllNews(@Query( "page" ) int index);

    @GET("getByCate/")
    Call<ResponseServer> getByCategory(@Query("page") int index,
                                       @Query("cate") String cate_id);

    @GET("getCate/")
    Call<ResponseServer> getCategory();

    @GET("get/")
    Call<ResponseServer> checkLoginUser(@Query("user") String email);

    //.. View Count ..//
    @POST("viewCount/")
    @FormUrlEncoded
    Call<ResponseView> setViewCount(@Field("id") String idNews,
                                    @Field("count") String viewCount);

    //.. Register ..//
    @POST("register/")
    @FormUrlEncoded
    Call<ResponseServer> registerUser(@Field("user") String user,
                                      @Field("pass") String pass,
                                      @Field("name") String name,
                                      @Field("dob") String dob,
                                      @Field("email") String email,
                                      @Field("phone") String phone,
                                      @Field("avatar") String avt);

    //.. Change Info ..//
    @POST("update/info/")
    @FormUrlEncoded
    Call<ResponseServer> registerUser(@Field("user") String user,
                                      @Field("pass") String pass,
                                      @Field("name") String name,
                                      @Field("dob") String dob,
                                      @Field("gender") String gender,
                                      @Field("address") String address,
                                      @Field("email") String email,
                                      @Field("phone") String phone,
                                      @Field("avatar") String avt);

    //.. Change Pass ..//
    @POST("update/pass/")
    @FormUrlEncoded
    Call<ResponseServer> registerUser(@Field("pass") String pass);
}
