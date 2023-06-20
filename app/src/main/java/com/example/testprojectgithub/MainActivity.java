package com.example.testprojectgithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.example.testprojectgithub.model.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvHelloWorld = findViewById(R.id.tvHelloWorld);
        tvHelloWorld.setText("Hi");

        // assign action to Game List button
        Button btnGameList = findViewById(R.id.btnGameList);
        btnGameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // forward user to GameListActivity
                Intent intent = new Intent(context, GameListActivity.class);
                startActivity(intent);
            }
        });
    }
    public void doLogut(View view)
    {
        SharedPrefManager.getInstance(getApplicationContext().logout());
        Toast.k
    }
}