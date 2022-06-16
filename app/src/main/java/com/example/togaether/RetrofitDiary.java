package com.example.togaether;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitDiary {
    @FormUrlEncoded
    @POST("/diaryList.php")
    Call<List<DiaryItem>> getDiaryList(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/diaryDetail.php")
    Call<DiaryItem> getDiaryDetail(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/diaryCommentInsert.php")
    Call<List<CommentItem>> insertDiaryComment(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/diaryCommentDelete.php")
    Call<ResponseBody> deleteDiaryComment(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/guestInsert.php")
    Call<List<GuestItem>> insertGuestMessage(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/guestList.php")
    Call<List<GuestItem>> getGuestMessageList(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/diaryInsert.php")
    Call<ResponseBody> insertDiary(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("/diaryHeart.php")
    Call<ResponseBody> flipHeart(@FieldMap HashMap<String, Object> param);
}
