package com.example.togaether;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PuppyListAdapter extends BaseAdapter {
    private ArrayList<PuppyInfoItem> items = new ArrayList<>();
    private int listType;
    private Context context;
    AddressConverter addressConverter;
    UserData userData;


    public PuppyListAdapter(int listType, Context context) {
        userData = UserData.getInstance();
        addressConverter = AddressConverter.getInstance();
        this.context = context;
        this.listType = listType;
    }

    public PuppyListAdapter() {
        this(0, null);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PuppyInfoItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(listType == 0) {
            context = viewGroup.getContext();
        }
        ViewHolder holder = null;
        if(view == null || ((ViewHolder)view.getTag()).id != this.items.get(i).getPid()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.puppylist_item, viewGroup, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.tvText = (TextView) view.findViewById(R.id.tv_text);
            holder.layPuppyItem = (ConstraintLayout) view.findViewById(R.id.lay_puppyitem);
            holder.puppyView = new PuppyView(holder.layPuppyItem, (AppCompatActivity) context, this.items.get(i).getPcustom(), 0.5f);
            holder.puppyView.setPosition(40,20);
            //친구 리스트
            holder.layFriend = (LinearLayout) view.findViewById(R.id.lay_friend);
            holder.tvUname = (TextView) view.findViewById(R.id.tv_uname);
            holder.btnVisit = (AppCompatButton) view.findViewById(R.id.btn_visit);
            holder.btnUnfriend = (AppCompatButton) view.findViewById(R.id.btn_unfriend);
            //------------
            holder.id = this.items.get(i).getPid();
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        PuppyInfoItem pitem = this.items.get(i);
        holder.tvName.setText(this.items.get(i).getPname());
        String infoString;
        infoString = this.items.get(i).getPbdate().format(DateTimeFormatter.ofPattern("MM월 dd일")) + "생일, ";
        infoString += this.items.get(i).getPsex().equals("W")? "여자 아이" : "남자 아이";
        holder.tvText.setText(infoString);

        if(listType >= 1) {
            if(userData.getUserId().equals(pitem.getUid())) {
                holder.layFriend.setVisibility(View.GONE);
            }
            else {
                holder.addrStr = addressConverter.getAddrByPid(pitem.getPid());
                holder.tvUname.setText(this.items.get(i).getPcall() + ": " + this.items.get(i).getUname() + "\n주소: " + holder.addrStr);

                holder.btnVisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("puppyItem", (new Gson()).toJson(items.get(i)));
                        ((AppCompatActivity) context).startActivity(intent);
                    }
                });
                holder.layFriend.setVisibility(View.VISIBLE);
                holder.tvUname.setVisibility(View.VISIBLE);
                if (listType == 2) {
                    holder.btnUnfriend.setVisibility(View.GONE);
                }
            }
        }

        return view;
    }

    public static class ViewHolder {
        public String addrStr;
        public int id;
        public TextView tvName;
        public TextView tvText;
        public ConstraintLayout layPuppyItem;
        public PuppyView puppyView;
        //친구 리스트
        public LinearLayout layFriend;
        public TextView tvUname;
        public AppCompatButton btnVisit, btnUnfriend;
    }

    public void addItem(PuppyInfoItem item) {
        items.add(item);
    }

    public void addItem(List<PuppyInfoItem> items) {
        this.items.addAll(items);
    }
}
