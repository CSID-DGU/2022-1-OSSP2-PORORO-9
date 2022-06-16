package com.example.togaether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private ArrayList<MenuItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MenuItem getItem(int i) {
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
        if(view == null || ((ViewHolder)view.getTag()).id != this.items.get(i).getId()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.menu_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_mtitle);
            holder.tvText = (TextView) view.findViewById(R.id.tv_mtext);
            holder.imgIcon = (ImageView) view.findViewById(R.id.img_micon);
            holder.id = this.items.get(i).getId();
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(this.items.get(i).getTitle());
        holder.tvText.setText(this.items.get(i).getText());
        Glide.with(viewGroup).load(this.items.get(i).getIcon()).fitCenter().into(holder.imgIcon);

        return view;
    }

    public static class ViewHolder {
        public int id;
        public TextView tvTitle;
        public TextView tvText;
        public ImageView imgIcon;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void addItem(List<MenuItem> items) {
        this.items.addAll(items);
    }
}
