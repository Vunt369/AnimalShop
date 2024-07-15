package com.example.petshop.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.petshop.R;
import com.example.petshop.model.Contact;
import com.example.petshop.utils.DateHelper;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private static final String TAG = "ContactAdapter";
    private List<Contact> contacts = new ArrayList<>();
    private Context context;
    private OnContactItemAction action;

    public ContactAdapter(Context context, OnContactItemAction action) {
        this.context = context;
        this.action = action;
    }

    public void setData(List<Contact> contacts) {
        this.contacts = contacts != null ? contacts : new ArrayList<>();
        notifyDataSetChanged();
        Log.d(TAG, "setData: Contacts set, size = " + this.contacts.size());
    }

    public void addOrUpdateContact(Contact contact) {
        if (contact == null || contact.getUsername() == null) {
            Log.e(TAG, "addOrUpdateContact: Contact or ContactChild is null: " + contact);
            return;
        }

        String contactName = contact.getUsername();
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getUsername() != null && contacts.get(i).getUsername().equals(contactName)) {
                contacts.set(i, contact);
                notifyItemChanged(i);
                Log.d(TAG, "addOrUpdateContact: Contact updated at position " + i);
                return;
            }
        }
        contacts.add(0, contact);
        notifyItemInserted(contacts.size() - 1);
        Log.d(TAG, "addOrUpdateContact: Contact added at position " + (contacts.size() - 1));
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.onBind(contacts.get(position), context, action);
        Log.d(TAG, "onBindViewHolder: Binding contact at position " + position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView username;
        private TextView lastMessage;
        private TextView lastSent;
        private View isNew;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imv_contact_avt);
            username = itemView.findViewById(R.id.txt_username);
            lastMessage = itemView.findViewById(R.id.txt_last_message);
            lastSent = itemView.findViewById(R.id.txt_last_sent_time);
            isNew = itemView.findViewById(R.id.has_new_msg);
        }

        public void onBind(Contact contact, Context context, OnContactItemAction action) {
            if (contact == null || contact.getUsername() == null) {
                Log.e(TAG, "onBind: Contact or ContactChild is null: " + contact);
                return;
            }
            Glide.with(context).load(R.drawable.avatar).circleCrop().into(imgAvatar);
            username.setText(contact.getUsername());
            lastMessage.setText(contact.getContent() != null ? contact.getContent() : "");
            itemView.setOnClickListener((e) -> {
                action.onItemSelect(contact);
            });
        }
    }

    public interface OnContactItemAction {
        void onItemSelect(Contact contact);
    }
}
