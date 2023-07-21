package com.example.testprojectgithub.remote;

import com.example.testprojectgithub.model.DeleteResponse;
import com.example.testprojectgithub.model.Game;
import com.example.testprojectgithub.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GameService {

    @GET("api/game")
    Call<List<Game>> getAllGames(@Header("api-key") String api_key);


    @GET("api/game/{id}")
    Call<Game> getGame(@Header("api-key") String api_key, @Path("id") int id);

    /**
     * Add game by sending a single Game JSON
     * @return game object
     */
    @POST("api/game")
    Call<Game> addGame(@Header ("api-key") String apiKey, @Body Game game);

    @POST("api/game/update")
    Call<Game> updateGame(@Header ("api-key") String apiKey, @Body Game game);

    /**
     * Delete game based on the id
     * @return DeleteResponse object
     */
    @POST("api/game/delete/{id}")
    Call<DeleteResponse> deleteGame(@Header ("api-key") String apiKey, @Path("id") int id);
}
