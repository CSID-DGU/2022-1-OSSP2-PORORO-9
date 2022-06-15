package com.example.togaether;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<CustomItem> items = new ArrayList<>();
    private CustomType type;

    public CustomAdapter(CustomType type) {
        super();
        this.type = type;
    }

    public CustomAdapter() {
        this(CustomType.FACE1);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CustomItem getItem(int i) {
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
        if(view == null || (((ViewHolder)view.getTag()).id != null && !((ViewHolder)view.getTag()).id.equals(this.items.get(i).getSourceUrl()))) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_item, viewGroup, false);
            holder = new ViewHolder();
            holder.imgCImg = (ImageView) view.findViewById(R.id.img_cimg);
            holder.imgCImgSub = (ImageView) view.findViewById(R.id.img_cimg_sub);
            holder.tvCName = (TextView) view.findViewById(R.id.tv_cname);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if(type == CustomType.EYE_L) {
            Glide.with(viewGroup).load(this.items.get(i).getSourceUrl())
                    .fitCenter().into(holder.imgCImg);
            Glide.with(viewGroup).load(this.items.get(i).getSourceUrl().replace("eye","eyew"))
                    .fitCenter().into(holder.imgCImgSub);
            holder.imgCImg.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.MULTIPLY);
        }
        else {
            Glide.with(viewGroup).load(this.items.get(i).getSourceUrl())
                    .fitCenter().into(holder.imgCImg);
            holder.imgCImg.setColorFilter(Color.parseColor("#e8e8e8"), PorterDuff.Mode.MULTIPLY);
        }
        holder.tvCName.setText(this.items.get(i).getName());

        //리스너 정의

        return view;
    }

    public static class ViewHolder {
        public String id;
        public ImageView imgCImg;
        public ImageView imgCImgSub;
        public TextView tvCName;
    }

    public void addItem(String url, String name, String type) {
        CustomItem item = new CustomItem(url, name, type);
        items.add(item);
    }

    public void addItem(CustomItem item) {
        items.add(item);
    }

    public void addItem(List<CustomItem> items) {
        this.items.addAll(items);
    }
}
