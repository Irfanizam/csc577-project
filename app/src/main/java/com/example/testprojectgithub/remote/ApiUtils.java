package com.example.testprojectgithub.remote;


public class ApiUtils
{
    public static final String BASE_URL = ;

    public static UserService.getUserService()
    {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
