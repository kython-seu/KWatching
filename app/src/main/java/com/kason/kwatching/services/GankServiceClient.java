package com.kason.kwatching.services;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kason.kwatching.entity.GankData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kason_zhang on 3/10/2017.
 * Team:TrendMicro VMI
 */

public class GankServiceClient {

    private static final String gank_api = "http://gank.io/api/";

    private GankService gankService;

    private GankServiceClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(gank_api)
                .build();

        gankService = retrofit.create(GankService.class);

    }

    private static class SINGLEINSTANCE{
        private static GankServiceClient INSTANCE = new GankServiceClient();
    }

    public static GankServiceClient getInstance(){
        return SINGLEINSTANCE.INSTANCE;
    }

    public void getByDay(Consumer<GankData> consumer,int year, int month, int day){
        gankService.getDailyData(year,month,day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }
}
