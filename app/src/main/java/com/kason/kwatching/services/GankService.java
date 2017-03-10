package com.kason.kwatching.services;

import com.kason.kwatching.entity.GankData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kason_zhang on 3/10/2017.
 * Team:TrendMicro VMI
 */

public interface GankService {


    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getDailyData(@Path("year") int year, @Path("month") int month,
            @Path("day") int day);
}
