package com.example.togaetherlib;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitPuppy {
    @POST("/puppyListForPid.php")
    Call<PuppyInfoItem> getData(@Query("pid") int id);

    @FormUrlEncoded
    @POST("/puppyInfoForPid.php")
    Call<PuppyInfoItem> getMoreInfo(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/test.json")
    Call<PuppyItem> postData(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/puppyInsert.php")
    Call<ResponseBody> insertPuppy(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/puppyListForUid.php")
    Call<List<PuppyInfoItem>> getPuppyListById(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/puppyFriendList.php")
    Call<List<PuppyInfoItem>> getPuppyFriendList(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/puppyListForRandom.php")
    Call<PuppyInfoItem> getVisitPuppy(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/puppySetFriend.php")
    Call<ResponseBody> setFriend(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/puppyListForAddrPid.php")
    Call<List<PuppyInfoItem>> getPuppyListByAddrPid(@FieldMap HashMap<String, Object> param);
}
