package com.example.togaether;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitPlan {
    @GET("/getplaninfo.php")
    Call<PlanItem> getData(@Query("id") int id);

    @GET("/getplanlen.php")
    Call<PlanLenItem> getLenData();

    @FormUrlEncoded
    @POST("/getplaninfo.php")
    Call<PlanItem> postData(@FieldMap HashMap<String, Object> param);
}
