package com.example.testprojectgithub.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.testprojectgithub.LoginActivity;

public class SharedPrefManager
{
    private static final String SHARED_PREF_NAME = "gamelibarysharedpref";
    private static final String KEY_ID = "keyid";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_TOKEN = "keytoken";
    private static final String KEY_ROLE = "keyrole";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_ROLE, user.getRole());
        editor.apply();
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        User user = new User();
//        user.setId(sharedPreferences.getInt(KEY_ID, -1));
//        user.setUsername(sharedPreferences.getString(KEY_USERNAME, null));
//        user.setEmail(sharedPreferences.getString(KEY_EMAIL, null));
//        user.setToken(sharedPreferences.getString(KEY_TOKEN, null));
//        user.setRole(sharedPreferences.getString(KEY_ROLE, null));

        int id = sharedPreferences.getInt(KEY_ID, -1);
        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        String role = sharedPreferences.getString(KEY_ROLE, null);

        User user = new User(id, email, username, "", token, "", role, 1,"");

        return user;
    }
    public void logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
