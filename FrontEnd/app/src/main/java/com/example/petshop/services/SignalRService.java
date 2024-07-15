package com.example.petshop.services;

import android.content.Context;
import android.util.Log;

import com.example.petshop.database.MessageRepository;
import com.example.petshop.helper.BaseUtils;
import com.example.petshop.model.Contact;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class SignalRService {
    private static final String TAG = "SignalRService";
    private static final String HUB_URL = BaseUtils.API_URI + "chathub";
    private HubConnection hubConnection;
    private Context context;
    private MessageRepository messageRepository;

    public SignalRService(Context context) {
        this.context = context;
        this.messageRepository = new MessageRepository(context);
    }

    public void startConnection() {
        hubConnection = HubConnectionBuilder.create(HUB_URL).build();

        hubConnection.on("ReceiveMessage", (userId, username, message, chatRoom, filePath) -> {
            Log.d(TAG, "Message received: userId=" + userId + ", username=" + username + ", message=" + message + ", chatRoom=" + chatRoom + ", filePath=" + filePath);
            Contact contact = new Contact();
            contact.setFile(filePath);
            contact.setContent(message);
            contact.setUsername(username);
            contact.setChatRoom(chatRoom);
            // Save message to database
            messageRepository.saveMessage(username, message, chatRoom, filePath);
            if (onNewMessageReceivedListener != null) {
                onNewMessageReceivedListener.onNewMessage(contact);
            }
        }, Long.class, String.class, String.class, String.class, String.class);

        hubConnection.start().blockingAwait();
    }

    public interface OnNewMessageReceivedListener {
        void onNewMessage(Contact contact);
    }

    private OnNewMessageReceivedListener onNewMessageReceivedListener;

    public void setOnNewMessageReceivedListener(OnNewMessageReceivedListener listener) {
        this.onNewMessageReceivedListener = listener;
    }
}
