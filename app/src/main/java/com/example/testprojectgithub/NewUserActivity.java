package com.example.testprojectgithub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.SharedPrefManager;
import com.example.testprojectgithub.model.User;
import com.example.testprojectgithub.remote.ApiUtils;
import com.example.testprojectgithub.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Context context;
    private Spinner spRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        context = this;

        //get view objects references
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        spRole = findViewById(R.id.spRole);
    }

    public void addNewUser(View view){
        //get values in form
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String role = spRole.getSelectedItem().toString();

        //create a user
        User u = new User(0,username, username, password, "", "", role, 1, "");

        // get user info from SharedPreferences
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        //send request to add new user to REST API
        UserService userService = ApiUtils.getUserService();
        Call<User> call = userService.addNewUser(user.getToken(),u);  //Recheck here
        System.out.println("Username" + u.getUsername());
        System.out.println("Password" +u.getPassword());
        //execute
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                // invalid session?
                if (response.code() == 401)
                    displayAlert("Invalid session");

                // user added successfully?
                Log.d("MyApp:", "Response: " + response.raw().toString());
                User addedUser = response.body();
                Log.d("MyApp:", "User: " + addedUser);

                if (addedUser != null) {
                    // display message
                    Toast.makeText(context,
                            addedUser.getUsername() + " added successfully.",
                            Toast.LENGTH_LONG).show();

                    // end this activity and forward user to GameListActivity
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    displayAlert("Add New User failed.");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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
