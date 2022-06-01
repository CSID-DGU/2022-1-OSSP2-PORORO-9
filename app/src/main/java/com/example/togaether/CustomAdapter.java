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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<CustomItem> items = new ArrayList<>();

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
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_item, viewGroup, false);
        }
        ImageView imgCImg = (ImageView) view.findViewById(R.id.img_cimg);
        TextView tvCName = (TextView) view.findViewById(R.id.tv_cname);

        CustomItem item = getItem(i);

        Glide.with(viewGroup).load(item.getSourceUrl()).fitCenter().into(imgCImg);
        imgCImg.setColorFilter(Color.parseColor("#e8e8e8"), PorterDuff.Mode.MULTIPLY);
        tvCName.setText(item.getName());

        //리스너 정의

        return view;
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
