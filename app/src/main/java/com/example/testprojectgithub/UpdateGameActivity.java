package com.example.testprojectgithub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.SharedPrefManager;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.GameService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateGameActivity extends AppCompatActivity {

    private GameService gameService;
    private Game game;      // store book info

    // form fields
//    private EditText txtTitle;
//    private EditText txtDesc;
//    private EditText txtAuthor;
//    private EditText txtISBN;
//    private EditText txtYear;
//    private static TextView tvCreated;
//
//    private static Date createdAt;

    private EditText txtName;
    private EditText txtDescription;
    private EditText txtRating;

    private static TextView tvReleaseDate; // static because need to be accessed by DatePickerFragment

    private static Date releaseDate; // static because need to be accessed by DatePickerFragment

    private Context context;


    /**
     * Date picker fragment class
     * Reference: https://developer.android.com/guide/topics/ui/controls/pickers
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            // create a date object from selected year, month and day
            releaseDate = new GregorianCalendar(year, month, day).getTime();

            // display in the label beside the button with specific date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            tvReleaseDate.setText( sdf.format(releaseDate) );
        }
    }

    /**
     * Called when pick date button is clicked. Display a date picker dialog
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_game);

        // retrieve book id from intent
        // get book id sent by BookListActivity, -1 if not found
        Intent intent = getIntent();
        int id = intent.getIntExtra("game_id", -1);

        // initialize createdAt to today's date
        releaseDate = new Date();

        // get references to the form fields in layout
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        txtRating = findViewById(R.id.txtRating);


        // retrieve book info from database using the book id
        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // get book service instance
        gameService = ApiUtils.getGameService();

        // execute the API query. send the token and book id
        gameService.getGame(user.getToken(), id).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // get book object from response
                game = response.body();

                // set values into forms
                txtName.setText(game.getGameName());
                txtDescription.setText(game.getGameDescription());
                txtRating.setText(game.getGameRating());


                // parse created_at date to date object
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    // parse created date string to date object
                    releaseDate = sdf.parse(game.getReleaseDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(null, "Error connecting", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Update book info in database when the user click Update Book button
     * @param view
     */
    public void updateGame(View view) {
        // get values in form
        String title = txtName.getText().toString();
        String description = txtDescription.getText().toString();
        String rating = txtRating.getText().toString();


        // convert createdAt date to format in DB
        // reference: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String created_at = sdf.format(releaseDate);

        // set updated_at to current date and time
        String updated_at = sdf.format(new Date());

        // update the book object retrieved in onCreate with the new data. the book object
        // already contains the id
        game.setGameName(title);
        game.setGameDescription(description);
        game.setGameRating(rating);


        Log.d("MyApp:", "Game info: " + game.toString());

        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        // send request to update the book record to the REST API
        GameService gameService = ApiUtils.getGameService();
        Call<Game> call = gameService.updateGame(user.getToken(), game);

        Context context = this;
        // execute
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {

                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // invalid session?
                if (response.code() == 401)
                    displayAlert("Invalid session. Please re-login");

                // book updated successfully?
                Game updatedGame = response.body();
                if (updatedGame != null) {
                    // display message
                    Toast.makeText(context,
                            updatedGame.getGameName() + " updated successfully.",
                            Toast.LENGTH_LONG).show();

                    // end this activity and forward user to BookListActivity
                    Intent intent = new Intent(context, GameListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    displayAlert("Update Game failed.");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                displayAlert("Error [" + t.getMessage() + "]");
                // for debug purpose
                Log.d("MyApp:", "Error: " + t.getCause().getMessage());
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