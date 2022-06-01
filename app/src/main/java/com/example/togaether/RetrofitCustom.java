package com.example.togaether;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitCustom {
    @GET("/customSource.php")
    Call<List<CustomItem>> getData(@Query("stype") String tname);

    @FormUrlEncoded
    @POST("/customSource.php")
    Call<CustomItem> postData(@FieldMap HashMap<String, Object> param);
}
