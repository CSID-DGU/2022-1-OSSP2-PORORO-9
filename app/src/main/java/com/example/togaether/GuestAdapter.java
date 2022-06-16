package com.example.togaether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GuestAdapter extends BaseAdapter {
    private ArrayList<GuestItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public GuestItem getItem(int i) {
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
        if(view == null || ((ViewHolder)view.getTag()).id != this.items.get(i).getGid()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.guest_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tvText = (TextView) view.findViewById(R.id.tv_ccomment);
            holder.tvName = (TextView) view.findViewById(R.id.tv_cname);
            holder.tvDate = (TextView) view.findViewById(R.id.tv_cdate);
            holder.id = this.items.get(i).getGid();
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvText.setText(this.items.get(i).getGcontent());
        holder.tvName.setText(this.items.get(i).getUname());
        holder.tvDate.setText(this.items.get(i).getGdate());

        return view;
    }

    public static class ViewHolder {
        public int id;
        public TextView tvName;
        public TextView tvText;
        public TextView tvDate;
    }

    public void addItem(GuestItem item) {
        items.add(item);
    }

    public void addItem(List<GuestItem> items) {
        this.items.addAll(items);
    }

    public void clear() {
        this.items.clear();
    }
}
