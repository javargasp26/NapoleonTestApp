package com.example.napoleontestapp.placeHolder;

import com.example.napoleontestapp.info.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostHolder {
    @GET("posts")
    Call<List<Post>> getPost();
}
