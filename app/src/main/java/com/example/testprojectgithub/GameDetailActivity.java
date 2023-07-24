package com.example.testprojectgithub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.SharedPrefManager;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.GameService;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailActivity extends AppCompatActivity {

    GameService gameService;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        context = this;
        // get game id sent by GameListActivity, -1 if not found
        Intent intent = getIntent();
        int id = intent.getIntExtra("game_id", -1);

        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // get game service instance
        gameService = ApiUtils.getGameService();

        // execute the API query. send the token and game id
        gameService.getGame(user.getToken(), id).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // get game object from response
                Game game = response.body();

                // get references to the view elements
                TextView tvName = findViewById(R.id.tvName);
                TextView tvDescription = findViewById(R.id.tvDescription);
                TextView tvRating = findViewById(R.id.tvRating);
                TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);

                RecyclerView recyclerViewReviews = findViewById(R.id.recyclerViewReviews);


                // set values
                tvName.setText(game.getGameName());
                tvDescription.setText(game.getGameDescription());
                tvRating.setText(game.getGameRating());
                tvReleaseDate.setText(game.getReleaseDate());


                // assign action to Game List button
                Button btnCreateReview = findViewById(R.id.btnCreateReview);
                btnCreateReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // forward user to CreateReview
                Intent intent = new Intent(context, CreateReviewActivity.class);
                startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(null, "Error connecting", Toast.LENGTH_LONG).show();
            }
        });

    }
}
