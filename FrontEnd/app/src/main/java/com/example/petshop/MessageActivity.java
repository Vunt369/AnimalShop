package com.example.petshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.adapters.MessageAdapter;
import com.example.petshop.helper.BaseUtils;
import com.example.petshop.model.Message;
import com.example.petshop.model.Talker;
import com.example.petshop.model.UserConnection;
import com.example.petshop.utils.KeyHelper;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.Objects;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private Uri imageUri;
    private int type;
    private RecyclerView rcyMessage;
    private ImageButton btnGoBack, btnChoseImage, btnSend;
    private MessageAdapter mAdapter;
    private ImageView imvAvatar, imgPreview;
    private TextView txtName, txtNote;
    private EditText edtInput;
    private int page = 1;
    private int pageSize = 1000;
    private String roomId;
    private HubConnection mConnection;
    public static int CURRENT_USER_ID = 1;
    public static String CURRENT_USERNAME = "User1";
    public static String CURRENT_CONTACT = "TUNGLD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        bindElement();
        triggerAction();
        // Get data from intent
        if (getIntent() != null) {
            CURRENT_USER_ID = getIntent().getIntExtra("CURRENT_USER_ID", 1);
            CURRENT_USERNAME = getIntent().getStringExtra("CURRENT_USERNAME");
            CURRENT_CONTACT = getIntent().getStringExtra("CURRENT_CONTACT");
        }
        // Initialize the adapter and set it to the RecyclerView
        mAdapter = new MessageAdapter(this);
        rcyMessage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcyMessage.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableService();
    }

    @SuppressLint("CheckResult")
    public void enableService() {
        // Generate roomId based on current username and contact name
        roomId = KeyHelper.generatePalindrome(new String[]{CURRENT_USERNAME, CURRENT_CONTACT});
        Log.d(TAG, "enableService: RoomID= " + roomId);

        if (mConnection == null) {
            UserConnection userConnection = new UserConnection();
            userConnection.setUserId(CURRENT_USER_ID);
            userConnection.setChatRoom(roomId);
            userConnection.setUsername(CURRENT_USERNAME);
            userConnection.setFilePath("");

            mConnection = HubConnectionBuilder.create(BaseUtils.API_URI + "chathub").build();

            mConnection.on("ReceiveMessage", (userId, username, mess, chatRoom, filePath) -> {
                Log.d(TAG, "ReceiveMessage: Received message from " + username + ": " + mess);
                if (Objects.equals(roomId, chatRoom)) {
                    Message message1 = new Message();
                    message1.setContent(mess);
                    Talker talker = new Talker();
                    talker.setUsername(username);
                    message1.setSender(talker);
                    if (filePath != null) {
                        message1.setFile(filePath);
                    }
                    runOnUiThread(() -> {
                        Log.d(TAG, "Updating UI with new message");
                        mAdapter.addData(message1);
                        rcyMessage.scrollToPosition(mAdapter.getItemCount() - 1);
                    });
                }
            }, Long.class, String.class, String.class, String.class, String.class);

            mConnection.onClosed(exception -> {
                if (exception != null) {
                    Log.e(TAG, "Connection closed with exception", exception);
                    if (mConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {
                        // Handle disconnected state
                        Log.d(TAG, "Connection state: DISCONNECTED. Attempting to reconnect...");
                        // Implement reconnection logic here if needed
                    }
                }
            });

            mConnection.start().blockingAwait();
            Log.d(TAG, "enableService: Connection started");
        }
    }

    public void triggerAction() {
        btnGoBack.setOnClickListener((e) -> {
            Log.d(TAG, "Go back button clicked, finishing activity");
            finish();
        });

        btnSend.setOnClickListener((e) -> {
            String message = edtInput.getText().toString();
            if (!message.isEmpty()) {
                Log.d(TAG, "Send button clicked, sending message: " + message);
                sendMessage(message, CURRENT_USERNAME);
            }
        });

        imgPreview.setOnClickListener((e) -> {
            Log.d(TAG, "Image preview clicked, hiding image preview");
            imgPreview.setVisibility(View.INVISIBLE);
            imageUri = null;
        });
    }

    public void sendMessage(String message, String username) {
        String SERVER_METHOD_SEND = "SendMessageWithFilePathToRoom";
        Log.d(TAG, "sendMessage: Sending message: " + message);

        if (mConnection != null && mConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            Log.d(TAG, "sendMessage: Sending message to server");
            mConnection.send(SERVER_METHOD_SEND, CURRENT_USER_ID, username, message, roomId, null);
        } else {
            Log.e(TAG, "sendMessage: Connection is not in a connected state");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null) {
            Log.d(TAG, "onDestroy: Stopping connection");
            mConnection.stop();
            mConnection = null;
        }
    }

    public void bindElement() {
        Log.d(TAG, "bindElement: Binding UI elements");
        btnGoBack = findViewById(R.id.btn_go_back);
        btnChoseImage = findViewById(R.id.btn_chose_image);
        btnSend = findViewById(R.id.btn_send);
        txtName = findViewById(R.id.txt_msg_detail_name);
        txtNote = findViewById(R.id.txt_msg_detail_status);
        edtInput = findViewById(R.id.edt_input_msg);
        rcyMessage = findViewById(R.id.rcy_messages);
        imgPreview = findViewById(R.id.previewPickedImage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.d(TAG, "onCreateView: Creating view");
        return super.onCreateView(name, context, attrs);
    }
}
