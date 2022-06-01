package com.example.test2;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {
    @Multipart
    @POST("diary_javainsert.php")
    Call<String> request(@Part List<MultipartBody.Part> file);
}
