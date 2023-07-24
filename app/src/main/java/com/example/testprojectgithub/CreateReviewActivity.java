package com.example.testprojectgithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testprojectgithub.model.Review;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.ReviewService;
import com.example.testprojectgithub.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReviewActivity extends AppCompatActivity {

    private ReviewService reviewService;
    private UserService userService;
    private String apiKey, userid, idGame, userId, reviewId;
    private EditText comment ;

    private TextView tvHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        Intent intent = getIntent();
        String gameName = intent.getStringExtra("gameName");
        String idGame = intent.getStringExtra("idGame");


        reviewService = ApiUtils.getReviewService();
        userService = ApiUtils.getUserService();

        tvHeader = findViewById(R.id.tvHeader);
        comment = findViewById(R.id.comment);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnCreate = findViewById(R.id.btnCreate);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment.setText("");
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReview();
            }
        });

        userService.getUser(apiKey, userid).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        // Store the username obtained from the user object
                        String username = user.getUsername();
                        System.out.println("Username : " + user.getUsername());
                    }
                } else {
                    // Handle the case when fetching user details fails
                    Toast.makeText(CreateReviewActivity.this, "Failed to fetch user details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    private void createReview() {


}

}