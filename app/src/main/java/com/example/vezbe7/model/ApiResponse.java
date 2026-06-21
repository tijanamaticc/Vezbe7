package com.example.vezbe7.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {
    // ApiResponse je "omotač" (wrapper) koji koristimo kada server vraća
    // jedan JSON objekat koji sadrži više nizova (npr. posts, comments, users).
    // Ako server vraća direktno niz (npr. [ {...}, {...} ]), onda nije potreban ApiResponse
    // i možemo koristiti direktno Call<List<Post>> ili slično.
    @SerializedName("posts")
    private List<Post> posts;

    @SerializedName("comments")
    private List<Comment> comments;

    @SerializedName("users")
    private List<User> users;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

