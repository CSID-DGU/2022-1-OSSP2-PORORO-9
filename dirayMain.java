package com.example.test2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class dirayMain extends AppCompatActivity {

    boolean check = true;
    Bitmap bitmap;
    ImageView imageView;
    Button SelectImageGallery;
    EditText editTextTitle, editTextDiary;
    ProgressDialog progressDialog;
    int on_private;
    TextView date;
    String title;
    String content;
    String Private;
    String Title = "diary_title";
    String Content = "diary_content";
    String ImagePath = "image_path";
    String private_checked = "privateOn";
    String ServerUploadPath = "https://togaether.cafe24.com/diary_javainsert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarymain);

        // 상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        editTextTitle = (EditText) findViewById(R.id.title);
        editTextDiary = (EditText) findViewById(R.id.editText3);

        date = (TextView) findViewById(R.id.date);
        date.setText(getTime());

        RadioButton private_on = (RadioButton) findViewById(R.id.on_private);
        RadioButton public_on = (RadioButton) findViewById(R.id.on_public);

        imageView = (ImageView) findViewById(R.id.imageView3);
        SelectImageGallery = (Button) findViewById(R.id.image_upload);
        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });

        Button upload = (Button)findViewById(R.id.button5);
        upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                title = editTextTitle.getText().toString();
                content = editTextDiary.getText().toString();

                if(private_on.isChecked()){
                    on_private = 1;
                } else if (public_on.isChecked()){
                    on_private = 0;
                }

                Private = Integer.toString(on_private);

                UploadToServerFunction();
            }
        });
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I){
        super.onActivityResult(RC, RQC, I);
        if(RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null){
            Uri uri = I.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    // Ver. HttpConnection
    public void UploadToServerFunction() {
        ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();
        //bitmap = Bitmap.createScaledBitmap(bitmap, 100,100,true);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayBar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayBar, Base64.DEFAULT);
        class InsertData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(dirayMain.this,
                        "Diary is uploading", "Please wait", true, true);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(dirayMain.this, string1, Toast.LENGTH_LONG).show();
                imageView.setImageResource(android.R.color.transparent);
            }

            @Override
            protected String doInBackground(Void... params) {
                DiaryProcessClass diaryProcessClass = new DiaryProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put(Title, title);
                HashMapParams.put(Content, content);
                HashMapParams.put(ImagePath, ConvertImage);
                HashMapParams.put(private_checked, Private);
                String FinalData = diaryProcessClass.DiaryHttpRequest(ServerUploadPath, HashMapParams);
                return FinalData;
            }
        }
        InsertData InsertDataOBJ = new InsertData();
        InsertDataOBJ.execute();
    }

    public class DiaryProcessClass{
        public String DiaryHttpRequest(String requestURL, HashMap<String, String> PData){
            StringBuilder stringBuilder = new StringBuilder();
            try{
                URL url = new URL(requestURL);

                HttpURLConnection httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);

                OutputStream OutPutStream = httpURLConnectionObject.getOutputStream();

                BufferedWriter bufferedWriterObject = new BufferedWriter(new OutputStreamWriter(OutPutStream, "UTF-8"));
                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();

                OutPutStream.close();
                int RC = httpURLConnectionObject.getResponseCode();

                if(RC == HttpsURLConnection.HTTP_OK){
                    BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    String RC2 = bufferedReaderObject.readLine();
                    while(RC2  != null){
                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject = new StringBuilder();
            for(Map.Entry<String, String> KEY : HashMapParams.entrySet()){
                if(check) check = false;
                else stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }

    /* Ver. Retrofit2
    private void Upload(){
        ByteArrayOutputStream byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayBar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayBar, Base64.DEFAULT);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), ConvertImage);

        ArrayList<MultipartBody.Part> diary = new ArrayList<>();
        diary.add(MultipartBody.Part.createFormData("image_path", ConvertImage, requestFile));
        diary.add(MultipartBody.Part.createFormData("diary_title", title));
        diary.add(MultipartBody.Part.createFormData("diary_content", content));
        diary.add(MultipartBody.Part.createFormData("privateOn", Private));

        RetrofitInterface retrofitInterface = ApiClient.getApiClient().create(RetrofitInterface.class);
        Call<String> call = retrofitInterface.request(diary);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Upload()", "성공 : ");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Upload()", "에러: " + t.getMessage());
            }
        });
    }*/

    private String getTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}