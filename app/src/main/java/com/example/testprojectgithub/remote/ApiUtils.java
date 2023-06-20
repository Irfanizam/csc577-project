package com.example.testprojectgithub.remote;


public class ApiUtils
{
    public static final String BASE_URL = "https://https://csc577-nik.000webhostapp.com";

    public static UserService getUserService()
    {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    //return GameService instance
    public static GameService getGameService()
    {
        return RetrofitClient.getClient(BASE_URL).create(GameService.class);
    }
}
