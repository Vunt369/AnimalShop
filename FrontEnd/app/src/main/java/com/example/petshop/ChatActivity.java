package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.petshop.adapters.ContactAdapter;
import com.example.petshop.database.MessageRepository;
import com.example.petshop.model.Contact;
import com.example.petshop.services.SignalRService;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private SignalRService signalRService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.rcy_contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactAdapter = new ContactAdapter(this, contact -> {
            // Handle item selection
            Log.d(TAG, "Contact item selected: " + contact);
            openMessageActivity(contact);
        });
        recyclerView.setAdapter(contactAdapter);

        signalRService = new SignalRService(this);
        signalRService.setOnNewMessageReceivedListener(contact -> {
            runOnUiThread(() -> {
                Log.d(TAG, "New message received: " + contact);
                contactAdapter.addOrUpdateContact(contact);
                recyclerView.scrollToPosition(contactAdapter.getItemCount() - 1);
            });
        });

        signalRService.startConnection();

        // Load existing messages
        loadExistingMessages();
    }

    private void loadExistingMessages() {
        List<Contact> existingMessages = new MessageRepository(this).getAllMessages();
        Log.d(TAG, "Data contacted: " + existingMessages);
        contactAdapter.setData(existingMessages);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (signalRService != null) {
            signalRService.startConnection();
        }
    }

    private void openMessageActivity(Contact contact) {
        Intent intent = new Intent(ChatActivity.this, MessageActivity.class);
        intent.putExtra("CURRENT_USER_ID", 1);
        intent.putExtra("CURRENT_USERNAME", contact.getUsername());
        intent.putExtra("CURRENT_CONTACT", "Saller");
        startActivity(intent);
    }
}
