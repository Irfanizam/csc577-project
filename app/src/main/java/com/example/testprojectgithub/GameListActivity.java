package com.example.testprojectgithub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectgithub.adapter.GameAdapter;
import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.GameService;
import com.example.testprojectgithub.model.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameListActivity extends AppCompatActivity {

    GameService gameService;
    Context context;
    RecyclerView gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        context = this; // get current activity context

        // get reference to the RecyclerView bookList
        gameList = findViewById(R.id.gameList);

        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // get book service instance
        gameService = ApiUtils.getGameService();

        // execute the call. send the user token when sending the query
        gameService.getAllGames(user.getToken()).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // Get list of book object from response
                List<Game> games = response.body();

                // initialize adapter
                GameAdapter adapter = new GameAdapter(context, games);

                // set adapter to the RecyclerView
                gameList.setAdapter(adapter);

                // set layout to recycler view
                gameList.setLayoutManager(new LinearLayoutManager(context));

                // add separator between item in the list
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(gameList.getContext(),
                        DividerItemDecoration.VERTICAL);
                gameList.addItemDecoration(dividerItemDecoration);
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Toast.makeText(context, "Error connecting to the server", Toast.LENGTH_LONG).show();
                Log.e("MyApp:", t.getMessage());
            }
        });
    }
}

