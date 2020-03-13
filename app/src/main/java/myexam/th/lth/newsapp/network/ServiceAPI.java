package myexam.th.lth.newsapp.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAPI {

    public static String BASE_URL = "https://mangahay.net/newsplus/api/";

    public static String FOLDER_USER = "user/";
    public static String FOLDER_NEWS = "news/";


    public static <S> S getUserService(Class<S> serviceClass) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + FOLDER_USER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit.create(serviceClass);

    }

//    //.. TEST GET ALL USER Info ..//
//    private static Retrofit getRetrofitDataUser() {
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL + FOLDER_USER)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

    //.. TEST GET ALL NEWS Info ..//
    public static <S> S getNewsService(Class<S> serviceClass) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + FOLDER_NEWS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit.create(serviceClass);

    }
}
