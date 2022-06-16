package com.example.togaether;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiaryAdapter extends BaseAdapter {
    private ArrayList<DiaryItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DiaryItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        ViewHolder holder = null;
        if(view == null || ((ViewHolder)view.getTag()).id != this.items.get(i).getDid()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.diary_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvText = (TextView) view.findViewById(R.id.tv_text);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
            holder.tvPrivate = (TextView) view.findViewById(R.id.tv_private);
            holder.tvHeart = (TextView) view.findViewById(R.id.tv_heart);
            holder.tvComment = (TextView) view.findViewById(R.id.tv_comment);
            holder.imgPrivate = (ImageView) view.findViewById(R.id.img_private);
            holder.imgImg = (ImageView) view.findViewById(R.id.img_img);
            holder.imgHeart = (ImageView) view.findViewById(R.id.img_heart);
            holder.layHeart = (LinearLayout) view.findViewById(R.id.lay_heart);
            holder.layComment = (LinearLayout) view.findViewById(R.id.lay_comment);
            holder.layCommu = (LinearLayout) view.findViewById(R.id.lay_commu);
            holder.id = this.items.get(i).getDid();
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(this.items.get(i).getDtitle());
        holder.tvText.setText(this.items.get(i).getDcontent());
        holder.tvName.setText(this.items.get(i).getUname());
        holder.tvDate.setText(this.items.get(i).getDdate());
        holder.tvHeart.setText("하트 " + this.items.get(i).getDheart() + " 개");
        holder.tvComment.setText("댓글 " + this.items.get(i).getDcomment() + " 개");
        setHeart(viewGroup, holder, i);
        //하트 이미지 처리 필요
        if(this.items.get(i).getDimg() == null) {
            holder.imgImg.setVisibility(View.GONE);
        }
        else {
            Glide.with(viewGroup).load(this.items.get(i).getDimg()).centerCrop().into(holder.imgImg);
        }
        if(this.items.get(i).isDprivate()) {
            holder.layCommu.setVisibility(View.GONE);
            holder.tvPrivate.setText("비공개");
            Glide.with(viewGroup).load(R.drawable.ic_baseline_lock_24).fitCenter().into(holder.imgPrivate);
        }
        else {
            holder.tvPrivate.setText("공개");
            Glide.with(viewGroup).load(R.drawable.ic_baseline_lock_open_24).fitCenter().into(holder.imgPrivate);
        }
        //클릭 리스너
        final ViewHolder tempHolder = holder;
        holder.layHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // did에 하트 요청 (parameter: 유저 아이디, did)
                UserData userData = UserData.getInstance();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://togaether.cafe24.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitDiary retrofitAPI = retrofit.create(RetrofitDiary.class);
                HashMap<String, Object> input = new HashMap<>();
                input.put("did", items.get(i).getDid());
                input.put("uid", userData.getUserId());
                retrofitAPI.flipHeart(input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                String data = response.body().string();
                                items.get(i).setDheart(Integer.parseInt(data));
                                items.get(i).setHeart(!items.get(i).isHeart());
                                setHeart(viewGroup, tempHolder, i);
                                ((TextView)view.findViewById(R.id.tv_heart)).setText("하트 " + (items.get(i).getDheart()) + " 개");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("=========OMG","here" + t);
                    }
                });
            }
        });
        holder.layCommu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // did 글 자세히 보기
            }
        });

        return view;
    }

    public static class ViewHolder {
        public int id;
        public TextView tvTitle;
        public TextView tvText;
        public TextView tvName;
        public TextView tvDate;
        public TextView tvPrivate;
        public TextView tvHeart;
        public TextView tvComment;
        public ImageView imgPrivate;
        public ImageView imgImg;
        public ImageView imgHeart;
        public LinearLayout layHeart;
        public LinearLayout layComment;
        public LinearLayout layCommu;
    }

    private void setHeart(ViewGroup viewGroup, ViewHolder holder, int i) {
        if(this.items.get(i).isHeart()) {
            Glide.with(viewGroup).load(R.drawable.ic_baseline_favorite_24).into(holder.imgHeart);
            holder.tvHeart.setTextColor(Color.parseColor("#E74141"));
        }
        else {
            Glide.with(viewGroup).load(R.drawable.ic_baseline_favorite_border_24).into(holder.imgHeart);
            holder.tvHeart.setTextColor(Color.parseColor("#737373"));
        }
    }

    public void addItem(DiaryItem item) {
        items.add(item);
    }

    public void addItem(List<DiaryItem> items) {
        this.items.addAll(items);
    }

    public void clear() {
        this.items.clear();
    }
}
