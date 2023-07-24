package com.example.testprojectgithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testprojectgithub.model.ErrorResponse;
import com.example.testprojectgithub.model.SharedPrefManager;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.UserService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin, btnRegister;
    Context context;

    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if(validateLogin(username, password))
                {
                    doLogin(username, password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // forward user to NewUserActivity
                startActivity(new Intent(getApplicationContext(), NewUserActivity.class));
            }
        });



    }
    private boolean validateLogin(String username, String password)
    {
        if(username == null || username.trim().length() == 0)
        {
            displayToast("Username is required");
            return false;
        }
        if (password == null || password.trim().length() == 0)
        {
            displayToast("Password is required");
            return false;
        }
        return true;
    }

    private void doLogin(String username, String password)
    {
        Call call = userService.login(username, password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful())
                {
                    User user = (User) response.body();
                    if(user.getToken() !=null)
                    {
                        if(user.getRole().equalsIgnoreCase("admin")){
                            displayToast("Login Successful");
                            displayToast("Token: " + user.getToken());
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else if (user.getRole().equalsIgnoreCase("user")){
                            displayToast("Login Successful");
                            displayToast("Token: " + user.getToken());
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                    }
                    else if (response.errorBody() != null)
                    {
                        String errorResp = null;
                        try
                        {
                            errorResp = response.errorBody().string();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        ErrorResponse e = new Gson().fromJson(errorResp, ErrorResponse.class);
                        displayToast(e.getError().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                 displayToast("Error connecting to server.");
                 displayToast(t.getMessage());
            }
        });
    }
    public void displayToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}