package com.example.testprojectgithub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.testprojectgithub.model.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvHelloWorld = findViewById(R.id.tvHelloWorld);
        tvHelloWorld.setText("Testingttt");
    }
    public void doLogut(View view)
    {
        SharedPrefManager.getInstance(getApplicationContext().logout());
        Toast.k
    }
}