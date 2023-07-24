package com.example.testprojectgithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.example.testprojectgithub.model.SharedPrefManager;
import com.example.testprojectgithub.model.User;

public class MainActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        context = this;

        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // assign action to logout button
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // clear the shared preferences
                SharedPrefManager.getInstance(getApplicationContext()).logout();

                // display message
                Toast.makeText(getApplicationContext(),
                        "You have successfully logged out.",
                        Toast.LENGTH_LONG).show();

                // forward to LoginActivity
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

        // assign action to Game List button
        Button btnGameList = findViewById(R.id.btnGameList);
        btnGameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forward user to GameListActivity
//                Intent intent = new Intent(context, GameListActivity.class);
//                startActivity(intent);
                if(user.getRole().equalsIgnoreCase("admin")){
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    finish();
                    startActivity(new Intent(getApplicationContext(), GameListActivity.class));
                }
                else if (user.getRole().equalsIgnoreCase("user")){
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    finish();
                    startActivity(new Intent(getApplicationContext(), ViewGameListActivity.class));
                }
            }
        });
    }

    public void doLogout(View view) {
    // clear the shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).logout();
    // display message
        Toast.makeText(getApplicationContext(),
                "You have successfully logged out.",
                Toast.LENGTH_LONG).show();
    // forward to LoginActivity
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}