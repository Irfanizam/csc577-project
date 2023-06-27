package com.example.testprojectgithub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectgithub.adapter.GameAdapter;
import com.example.testprojectgithub.model.DeleteResponse;
import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.GameService;
import com.example.testprojectgithub.model.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameListActivity extends AppCompatActivity {

    GameService gameService;
    Context context;
    RecyclerView gameList;
    GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        context = this; // get current activity context

        // get reference to the RecyclerView gameList
        gameList = findViewById(R.id.gameList);
        // register for context menu
        registerForContextMenu(gameList);

        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // get game service instance
        gameService = ApiUtils.getGameService();

        // execute the call. send the user token when sending the query
        gameService.getAllGames(user.getToken()).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // Get list of game object from response
                List<Game> games = response.body();

                // initialize adapter
                adapter = new GameAdapter(context, games);

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

        // action handler for Add Game floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // forward user to NewGameActivity
                Intent intent = new Intent(context, NewGameActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Fetch data for ListView
     */
    private void updateListView() {
        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // get game service instance
        gameService = ApiUtils.getGameService();

        // execute the call. send the user token when sending the query
        gameService.getAllGames(user.getToken()).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // token is not valid/expired
                if (response.code() == 401) {
                    displayAlert("Session Invalid");
                }

                // Get list of game object from response
                List<Game> games = response.body();

                // initialize adapter
                adapter = new GameAdapter(context, games);

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
                displayAlert("Error [" + t.getMessage() + "]");
                Log.e("MyApp:", t.getMessage());
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Game selectedGame = adapter.getSelectedItem();
        Log.d("MyApp", "selected "+selectedGame.toString());
        switch (item.getItemId()) {
            case R.id.menu_details://should match the id in the context menu file
                doViewDetails(selectedGame);
                break;
            case R.id.menu_delete:
                doDeleteGame(selectedGame);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void doViewDetails(Game selectedGame) {
        Log.d("MyApp:", "viewing details "+ selectedGame.toString());
        Intent intent = new Intent(context, GameDetailActivity.class);
        intent.putExtra("game_id", selectedGame.getIdGame());
        startActivity(intent);
    }


    /**
     * Delete game record. Called by contextual menu "Delete"
     * @param selectedGame - games selected by user
     */
    private void doDeleteGame(Game selectedGame) {
        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // prepare REST API call
        GameService gameService = ApiUtils.getGameService();
        Call<DeleteResponse> call = gameService.deleteGame(user.getToken(), selectedGame.getIdGame());

        // execute the call
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.code() == 200) {
                    // 200 means OK
                    displayAlert("Game successfully deleted");
                    // update data in list view
                    updateListView();
                } else {
                    displayAlert("Game failed to delete");
                    Log.e("MyApp:", response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                displayAlert("Error [" + t.getMessage() + "]");
                Log.e("MyApp:", t.getMessage());
            }
        });
    }



    /**
     * Displaying an alert dialog with a single button
     * @param message - message to be displayed
     */
    public void displayAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

