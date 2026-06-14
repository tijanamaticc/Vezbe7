package com.example.vezbe7.api;

import com.example.vezbe7.model.Comment;
import com.example.vezbe7.model.Post;
import com.example.vezbe7.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("comments")
    Call<List<Comment>> getComments();

    @GET("users")
    Call<List<User>> getUsers();
}

