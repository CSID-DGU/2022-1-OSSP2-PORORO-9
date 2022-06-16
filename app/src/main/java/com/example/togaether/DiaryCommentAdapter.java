package com.example.togaether;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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

public class DiaryCommentAdapter extends BaseAdapter {
    private ArrayList<CommentItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CommentItem getItem(int i) {
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
            view = inflater.inflate(R.layout.comment_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tvText = (TextView) view.findViewById(R.id.tv_ccomment);
            holder.tvName = (TextView) view.findViewById(R.id.tv_cname);
            holder.tvDate = (TextView) view.findViewById(R.id.tv_cdate);
            holder.layComment = (LinearLayout) view.findViewById(R.id.lay_ccomment);
            holder.imgSub = (ImageView) view.findViewById(R.id.img_csub);
            holder.id = this.items.get(i).getDcid();
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if(this.items.get(i).isDcdelete()) {
            holder.tvText.setTextColor(ContextCompat.getColor(context, R.color.gray));
            holder.tvText.setText("삭제 된 댓글입니다.");
            holder.tvName.setVisibility(View.GONE);
            holder.tvDate.setVisibility(View.GONE);
        }
        else {
            holder.tvText.setText(this.items.get(i).getDccontent());
            holder.tvName.setText(this.items.get(i).getUname());
            holder.tvDate.setText(this.items.get(i).getDcdate());
        }
        if (this.items.get(i).getPdcid() != this.items.get(i).getDcid()) {
            //DisplayMetrics dm = viewGroup.getResources().getDisplayMetrics();
            //holder.layComment.setPadding(Math.round(50 * dm.density),Math.round(10 * dm.density),Math.round(20 * dm.density),Math.round(10 * dm.density));
            holder.imgSub.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public static class ViewHolder {
        public int id;
        public TextView tvName;
        public TextView tvText;
        public TextView tvDate;
        public LinearLayout layComment;
        public ImageView imgSub;
    }

    public void addItem(CommentItem item) {
        items.add(item);
    }

    public void addItem(List<CommentItem> items) {
        this.items.addAll(items);
    }

    public void clear() {
        this.items.clear();
    }
}
