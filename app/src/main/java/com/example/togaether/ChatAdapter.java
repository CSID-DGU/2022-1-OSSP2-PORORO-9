package com.example.togaether;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.togaether.R;
import com.example.togaether.Message;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<Message> messageList;
    private Activity activity;
    public ChatAdapter(List<Message> messageList, Activity activity) {
        this.messageList = messageList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.adapter_message_one, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String message = messageList.get(position).getMessage();
        boolean isReceived = messageList.get(position).getIsReceived();
        LocalTime now = LocalTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("a hh:mm").withLocale(Locale.forLanguageTag("ko"));
        String formattedNow = now.format(df);
        if (isReceived) {
            holder.messageReceive.setVisibility(View.VISIBLE);
            holder.messageSend.setVisibility(View.GONE);
            ((TextView)holder.messageReceive.findViewById(R.id.tv_message_receive)).setText(message);
            ((TextView)holder.messageReceive.findViewById(R.id.tv_message_receive_date)).setText(formattedNow);
        } else {
            holder.messageSend.setVisibility(View.VISIBLE);
            holder.messageReceive.setVisibility(View.GONE);
            ((TextView)holder.messageSend.findViewById(R.id.tv_message_send)).setText(message);
            ((TextView)holder.messageSend.findViewById(R.id.tv_message_send_date)).setText(formattedNow);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout messageSend;
        LinearLayout messageReceive;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageSend = itemView.findViewById(R.id.lay_message_send);
            messageReceive = itemView.findViewById(R.id.lay_message_receive);
        }
    }
}
