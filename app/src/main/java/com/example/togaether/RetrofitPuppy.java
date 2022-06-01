package com.example.togaether;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitPuppy {
    @GET("/test.json")
    Call<PuppyItem> getData(@Query("id") int id);

    @FormUrlEncoded
    @POST("/test.json")
    Call<PuppyItem> postData(@FieldMap HashMap<String, Object> param);
}
