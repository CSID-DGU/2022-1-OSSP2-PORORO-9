package com.example.togaether;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitUser {
    @FormUrlEncoded
    @POST("/signUp.php")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<ResponseBody> postSignUp(@FieldMap HashMap<String, Object> param);


    @FormUrlEncoded
    @POST("/signIn.php")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<LoginItem> postLogIn(@FieldMap HashMap<String, Object> param);
}
