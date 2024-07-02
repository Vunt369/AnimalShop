package com.example.petshop;

import static com.example.petshop.R.id.*;
import static com.example.petshop.R.id.activity_sign_up;
import static com.example.petshop.R.id.btn_signin;
import static com.example.petshop.R.id.btn_signup;
import static com.example.petshop.R.id.input_confirm_pass;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edUsername;
    private EditText edPass;

    private EditText edConfirm;
    private Button btnAlreadyAccount;
    private Button btnSignUp;

    private final String REQUIRE = "Require";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        edUsername = (EditText) findViewById(R.id.input_Name);
        edPass = (EditText) findViewById(R.id.input_pass);
        btnAlreadyAccount = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup) ;
        edConfirm = (EditText) findViewById(input_confirm_pass);

        btnAlreadyAccount.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(activity_sign_up), (v, insets) -> {
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

        if(TextUtils.isEmpty(edConfirm.getText().toString())){
            edConfirm.setError(REQUIRE);
            return false;
        }

        if(!TextUtils.equals(edPass.getText().toString(), edConfirm.getText().toString())){
            Toast.makeText(this, "Password are not match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void signUp(){
        if(!checkInput()){
            return;
        }


    }

    private void signInForm(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btn_signup) {
            signUp();
        } else if (id == btn_signin) {
            signInForm();
        }
    }
}
