package com.kason.kwatching.services;

import com.kason.kwatching.entity.Movie;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by kason_zhang on 3/9/2017.
 * Team:TrendMicro VMI
 */

public interface MovieService {

    String API_KEY = "a48a0e99c72250d7eae29919fe77d29f";
    String LANGUAGE = "en-US";
    @GET("discover/movie?api_key={api_key}")
    Call<Integer> getMovies(@Path("api_key") String api_key);

    @GET("/movie/{movie_id}?api_key="+API_KEY+"&language="+LANGUAGE)
    Observable<Movie> getMovieById(@Path("movie_id") int movie_id);
}
