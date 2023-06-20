package com.example.testprojectgithub.remote;

import com.example.testprojectgithub.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GameService {

    @GET("api/game")
    Call<List<Game>> getAllGames(@Header("api-key") String api_key);
}
