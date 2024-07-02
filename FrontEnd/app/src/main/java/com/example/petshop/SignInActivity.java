package com.example.petshop;

import static com.example.petshop.R.id.*;
import static com.example.petshop.R.id.activity_sign_in;
import static com.example.petshop.R.id.btn_create;
import static com.example.petshop.R.id.btn_signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edUsername;
    private EditText edPass;
    private Button btnNotAccount;
    private Button btnSignIn;

    private final String REQUIRE = "Require";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitivity_sign_in);

        edUsername = (EditText) findViewById(R.id.input_Name);
        edPass = (EditText) findViewById(R.id.input_pass);
        btnNotAccount = (Button) findViewById(R.id.btn_create);
        btnSignIn = (Button) findViewById(R.id.btn_signin) ;

        btnNotAccount.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(activity_sign_in), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean checkInput(){
        if(TextUtils.isEmpty(edUsername.getText().toString())){
            edUsername.setError(REQUIRE);
            return false;
        }

        if(TextUtils.isEmpty(edPass.getText().toString())){
            edPass.setError(REQUIRE);
            return false;
        }

        return true;
    }

    private void signIn(){
        if(!checkInput()){
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signUpForm(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btn_signin) {
            signIn();
        } else if (id == btn_create) {
            signUpForm();
        }
    }
}
