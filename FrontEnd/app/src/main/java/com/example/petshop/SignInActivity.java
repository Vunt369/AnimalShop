package com.example.petshop;

import static com.example.petshop.R.id.*;
import static com.example.petshop.R.id.activity_sign_in;
import static com.example.petshop.R.id.btn_create;
import static com.example.petshop.R.id.btn_signin;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.petshop.User.LoginRequest;
import com.example.petshop.User.User;
import com.example.petshop.User.UserRepository;
import com.example.petshop.User.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edUsername;
    private EditText edPass;
    private Button btnNotAccount;
    private Button btnSignIn;

    UserService userService;
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

        return true;
    }

    private void signIn(){
        if(!checkInput()){
            return;
        }

        try{

            String usename = edUsername.getText().toString();
            String password = edPass.getText().toString();


            LoginRequest loginRequest = new LoginRequest(usename, password);


            Call<User> call =  userService.login(loginRequest);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignInActivity.this, "Sign In successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignInActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.d("SignUp", "Error body: " + errorBody);
                            Toast.makeText(SignInActivity.this, "Username or password is invalid " + errorBody, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(SignInActivity.this, "Username or password is invalid " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("SignIn", "Failure: " + t.getMessage());
                    Toast.makeText(SignInActivity.this, "Sign In failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Loi", e.getMessage());
        }

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
