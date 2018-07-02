package com.ryu.dev.likeitgithub.network;

import com.ryu.dev.likeitgithub.model.Github;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("users")
    Call<Github> callUser(@Query("q") String param, @Query("page") int page, @Query("per_page") int perPage);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
