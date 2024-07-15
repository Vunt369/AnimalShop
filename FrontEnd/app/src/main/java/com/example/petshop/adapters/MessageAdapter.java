package com.example.petshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.petshop.MessageActivity;
import com.example.petshop.R;
import com.example.petshop.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages = new ArrayList<>();

    public static boolean isSender(long sender, int user) {
        return sender == user;
    }

    public static int SENDER_VIEW_TYPE = 1;
    public static int RECEIVER_VIEW_TYPE = 2;

    private Context context;

    public void setData(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addData(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = null;
        if (viewType == SENDER_VIEW_TYPE) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_sender, parent, false);
        } else {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_receiver, parent, false);
        }
        return new MessageViewHolder(root);
    }

    @Override
    public int getItemViewType(int position) {
        boolean isSender = Objects.equals(messages.get(position).getSender().getUsername(), MessageActivity.CURRENT_USERNAME);
        return isSender ? SENDER_VIEW_TYPE : RECEIVER_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.onBind(messages.get(position), context);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView txtContent;
        private ImageView file;
        private TextView txtName;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content);
            file = itemView.findViewById(R.id.imv_file);
            txtName = itemView.findViewById(R.id.txt_username);
        }

        public void onBind(Message message, Context context) {
            txtContent.setText(message.getContent());
            txtName.setText(message.getSender().getUsername());

            ViewGroup.LayoutParams layoutParams = file.getLayoutParams();
            if (message.getFile() != null && !Objects.equals(message.getFile(), "")) {
                layoutParams.width = 600;
                layoutParams.height = 447;
                file.setLayoutParams(layoutParams);
                file.setVisibility(View.VISIBLE);
                Glide.with(context).load(message.getFile()).error(R.drawable.error).override(600, 447).centerCrop().into(file);
            } else {
                layoutParams.width = 0;
                layoutParams.height = 0;
                file.setLayoutParams(layoutParams);
                file.setVisibility(View.INVISIBLE);
            }
        }
    }
}
