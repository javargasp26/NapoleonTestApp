package com.example.napoleontestapp.placeHolder;

import com.example.napoleontestapp.info.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserHolder {
    @GET("users")
    Call<List<User>> getUsers();
}
