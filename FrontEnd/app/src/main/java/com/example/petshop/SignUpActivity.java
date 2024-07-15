package com.example.petshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.User.User;
import com.example.petshop.User.UserRepository;
import com.example.petshop.User.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity  {
    private EditText edUsername;
    private EditText edPass;

    private EditText edConfirm;
    private EditText edEmail;
    private Button btnAlreadyAccount;
    private Button btnSignUp;

    private final String REQUIRE = "Require";

    UserService userService;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);


        edUsername = (EditText) findViewById(R.id.input_Name);
        edPass = (EditText) findViewById(R.id.input_pass);
        btnAlreadyAccount = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup) ;
        edConfirm = (EditText) findViewById(R.id.input_confirm_pass);
        edEmail = (EditText) findViewById(R.id.input_email);

      //  btnAlreadyAccount.setOnClickListener();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        btnAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInForm();
            }
        });
        userService = UserRepository.getUserService();

        
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

        if(TextUtils.isEmpty(edEmail.getText().toString())){
            edEmail.setError(REQUIRE);
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

        try{

        String usename = edUsername.getText().toString();
        String password = edPass.getText().toString();
        String email = edEmail.getText().toString();

        User user = new User(usename, password, null, email, null, null);


            Call<User> call =  userService.signUp(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.d("SignUp", "Error body: " + errorBody);
                            Toast.makeText(SignUpActivity.this, "Sign up failed: " + errorBody, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Sign up failed: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("SignUp", "Failure: " + t.getMessage());
                    Toast.makeText(SignUpActivity.this, "Sign up failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Loi", e.getMessage());
        }
    }

    private void signInForm(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }


}
