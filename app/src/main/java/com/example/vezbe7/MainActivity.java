package com.example.vezbe7;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vezbe7.api.ApiService;
import com.example.vezbe7.api.RetrofitClient;
import com.example.vezbe7.model.Comment;
import com.example.vezbe7.model.Post;
import com.example.vezbe7.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvFirstPost;
    private TextView tvSecondComment;
    private TextView tvUsersCount;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        apiService = RetrofitClient.getApiService();

        loadFirstPost();
        loadSecondComment();
        loadUsersCount();
    }

    private void initializeViews() {
        tvFirstPost = findViewById(R.id.tvFirstPost);
        tvSecondComment = findViewById(R.id.tvSecondComment);
        tvUsersCount = findViewById(R.id.tvUsersCount);
    }

    private void loadFirstPost() {
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> posts = response.body();
                    if (!posts.isEmpty()) {
                        Post firstPost = posts.get(0);
                        String postText = "ID: " + firstPost.getId() + "\n" +
                                "Korisnik ID: " + firstPost.getUserId() + "\n" +
                                "Naslov: " + firstPost.getTitle() + "\n" +
                                "Tekst: " + firstPost.getBody();
                        tvFirstPost.setText(postText);
                    } else {
                        tvFirstPost.setText("Nema dostupnih postova");
                    }
                } else {
                    tvFirstPost.setText("Neuspešan odgovor: " + response.code());
                    Toast.makeText(MainActivity.this, "Greška: odgovor " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Greška pri učitavanju postova: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadSecondComment() {
        apiService.getComments().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comment> comments = response.body();
                    if (comments.size() > 1) {
                        Comment secondComment = comments.get(1);
                        String commentText = "ID: " + secondComment.getId() + "\n" +
                                "Post ID: " + secondComment.getPostId() + "\n" +
                                "Ime: " + secondComment.getName() + "\n" +
                                "Email: " + secondComment.getEmail() + "\n" +
                                "Tekst: " + secondComment.getBody();
                        tvSecondComment.setText(commentText);
                    } else if (comments.isEmpty()) {
                        tvSecondComment.setText("Nema komentara");
                    } else {
                        tvSecondComment.setText("Nema drugog komentara");
                    }
                } else {
                    tvSecondComment.setText("Neuspešan odgovor: " + response.code());
                    Toast.makeText(MainActivity.this, "Greška: odgovor " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                tvSecondComment.setText("Greška pri učitavanju: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Greška pri učitavanju komentara: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUsersCount() {
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    int usersCount = users.size();
                    tvUsersCount.setText("Ukupno korisnika: " + usersCount);
                    Toast.makeText(MainActivity.this,
                            "Broj korisnika: " + usersCount,
                            Toast.LENGTH_SHORT).show();
                } else {
                    tvUsersCount.setText("Neuspešan odgovor: " + response.code());
                    Toast.makeText(MainActivity.this, "Greška: odgovor " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                tvUsersCount.setText("Greška pri učitavanju: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Greška pri učitavanju korisnika: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}