package com.kason.kwatching.services;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kason.kwatching.entity.Movie;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




//import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by kason_zhang on 3/9/2017.
 * Team:TrendMicro VMI
 */

public class MovieServiceClient {
    private final static String base_url = "https://developers.themoviedb.org/3/";
    private static final int TIMEOUT_DEFAULT = 5;
    private MovieService movieService;

    private MovieServiceClient(){

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl(base_url)
                //让retrofit支持GSON
                .addConverterFactory(GsonConverterFactory.create())
                //让retrofit 支持rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        movieService = retrofit.create(MovieService.class);
    }
    //在访问MovieServiceImpl的时候去创建单例类。
    private static class Singleton {
        private static final MovieServiceClient INSTANCE = new MovieServiceClient();
    }
    public static MovieServiceClient getInstance(){
        return Singleton.INSTANCE;
    }

    public void getMovieById(Consumer<Movie> consumer, int movie_id){
        movieService.getMovieById(movie_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
                //.subscribe(subscriber);

    }
}
