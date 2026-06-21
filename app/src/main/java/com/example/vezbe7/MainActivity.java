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
        // Ako želiš da odmah prikažeš n-ti post iz liste, možeš pozvati metodu sa indeksom (0-based):
        // loadPostByIndex(4); // 4 znači peti post
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
//    private void loadNextPost(){
//        apiService.getPosts().enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Post> posts = response.body();
//                    if (posts.size() > 1) {
//                        Post nextPost = posts.get(1); // Uzimamo drugi post (indeks 1)
//                        String postText = "ID: " + nextPost.getId() + "\n" +
//                                "Korisnik ID: " + nextPost.getUserId() + "\n" +
//                                "Naslov: " + nextPost.getTitle() + "\n" +
//                                "Tekst: " + nextPost.getBody();
//                        tvFirstPost.setText(postText);
//                    } else {
//                        tvFirstPost.setText("Nema sledećeg posta");
//                    }
//                } else {
//                    tvFirstPost.setText("Neuspešan odgovor: " + response.code());
//                    Toast.makeText(MainActivity.this, "Greška: odgovor " + response.code(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
//                Toast.makeText(MainActivity.this, "Greška pri učitavanju postova: " + t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }

//    private void loadFirstNPosts(int n) {
//        apiService.getPosts().enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Post> posts = response.body();
//                    StringBuilder postsText = new StringBuilder();
//                    for (int i = 0; i < Math.min(n, posts.size()); i++) {
//                        Post post = posts.get(i);
//                        postsText.append("ID: ").append(post.getId()).append("\n")
//                                .append("Korisnik ID: ").append(post.getUserId()).append("\n")
//                                .append("Naslov: ").append(post.getTitle()).append("\n")
//                                .append("Tekst: ").append(post.getBody()).append("\n\n");
//                    }
//                    tvFirstPost.setText(postsText.toString());
//                } else {
//                    tvFirstPost.setText("Neuspešan odgovor: " + response.code());
//                    Toast.makeText(MainActivity.this, "Greška: odgovor " + response.code(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
//                Toast.makeText(MainActivity.this, "Greška pri učitavanju postova: " + t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//    private void loadFirstPostTitle(){
//        apiService.getPosts().enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Post> posts = response.body();
//                    if (!posts.isEmpty()) {
//                        Post firstPost = posts.get(0);
//                        String postTitle = "Naslov: " + firstPost.getTitle();
//                        tvFirstPost.setText(postTitle);
//                    } else {
//                        tvFirstPost.setText("Nema dostupnih postova");
//                    }
//                } else {
//                    tvFirstPost.setText("Neuspešan odgovor: " + response.code());
//                    Toast.makeText(MainActivity.this, "Greška: odgovor " + response.code(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
//                Toast.makeText(MainActivity.this, "Greška pri učitavanju postova: " + t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });

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

    // ----- PRIMER: kako pozvati GET po ID-u (ako otkomentarišeš odgovarajuću metodu u ApiService) -----
    /*
    // Ako u ApiService postoji: @GET("posts/{id}") Call<Post> getPostById(@Path("id") int id);
    private void loadPostById(int id) {
        apiService.getPostById(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Post p = response.body();
                    // prikaz rezultata
                    tvFirstPost.setText("ID: " + p.getId() + "\nNaslov: " + p.getTitle());
                } else {
                    tvFirstPost.setText("Neuspešan odgovor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
            }
        });
    }
    */

    // ----- PRIMER: kako poslati POST zahtev (ako otkomentarišeš addPost u ApiService) -----
    /*
    private void createPostExample() {
        Post newPost = new Post(0, 1, "Novi naslov", "Sadržaj posta");
        apiService.addPost(newPost).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Post created = response.body();
                    Toast.makeText(MainActivity.this, "Kreiran post id=" + created.getId(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Greška: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška pri kreiranju: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    */
}

    // ----- PRIMER: kako da prikupiš proizvoljan (n-ti) post iz liste -----
    /*
    private void loadPostByIndex(int index) {
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> posts = response.body();
                    if (posts.size() > index) {
                        Post p = posts.get(index);
                        tvFirstPost.setText("ID: " + p.getId() + "\nNaslov: " + p.getTitle());
                    } else {
                        tvFirstPost.setText("Nema toliko postova");
                    }
                } else {
                    tvFirstPost.setText("Neuspešan odgovor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
            }
        });
    }
    */

    // ----- PRIMER: kako UPDATE (PUT) - izmena postojećeg posta (komentarisano) -----
    /*
    private void updatePostExample(int idToUpdate) {
        // Napravi objekat sa novim podacima (server očekuje ceo objekat za PUT)
        Post updated = new Post(idToUpdate, 1, "Izmenjen naslov", "Izmenjen tekst");
        apiService.updatePost(idToUpdate, updated).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Post izmenjen id=" + response.body().getId(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Greška: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška pri izmeni: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    */

    // ----- PRIMER: kako DELETE - obriši post po ID-u (komentarisano) -----
    /*
    private void deletePostExample(int idToDelete) {
        apiService.deletePost(idToDelete).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Post obrisan id=" + idToDelete, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Greška pri brisanju: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška pri brisanju: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    */
