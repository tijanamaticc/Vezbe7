package com.example.vezbe7.api;

import com.example.vezbe7.model.Comment;
import com.example.vezbe7.model.Post;
import com.example.vezbe7.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ApiService - glavne GET metode koje koristi aplikacija plus komentarisani primeri
 * ostalih uobičajenih varijanti (trailing slash, id u putanji, query parametri,
 * POST/PUT/DELETE). Kopiraj metodu koja ti treba, otkomentariši i prilagodi rutu.
 */
public interface ApiService {

    // ---------------------- AKTIVNO: Osnovni GET za Vežbu 7 ----------------------
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("comments")
    Call<List<Comment>> getComments();

    @GET("users")
    Call<List<User>> getUsers();

    // ---------------------- Varijante sa trailing slash (ako server očekuje "/") ----------------------
    // @GET("posts/")
    // Call<List<Post>> getPostsWithSlash();

    // ---------------------- GET JEDAN PO ID (putanja) ----------------------
    // Primer: GET /posts/5
    // @GET("posts/{id}")
    // Call<Post> getPostById(@Path("id") int id);

    // Ako server traži posebne heder-e, dodaj ih ovako:
    // @Headers({"User-Agent: Mobile-Android", "Content-Type:application/json"})
    // @GET("posts/{id}")
    // Call<Post> getPostByIdWithHeaders(@Path("id") int id);

    // ---------------------- GET sa query parametrima ----------------------
    // Primer: GET /posts?userId=2
    // @GET("posts")
    // Call<List<Post>> getPostsByUser(@Query("userId") int userId);

    // ---------------------- POST (kreiranje) ----------------------
    // @POST("posts")
    // Call<Post> addPost(@Body Post post);

    // Ako server očekuje trailing slash pri POST:
    // @POST("posts/")
    // Call<Post> addPostWithSlash(@Body Post post);

    // ---------------------- PUT (izmena celog resursa) ----------------------
    // Tipično: PUT /posts/{id} sa telom Post objekta
    // @PUT("posts/{id}")
    // Call<Post> updatePost(@Path("id") int id, @Body Post post);

    // ---------------------- DELETE ----------------------
    // Brisanje po id: DELETE /posts/{id}
    // @DELETE("posts/{id}")
    // Call<ResponseBody> deletePost(@Path("id") int id);

    // ---------------------- Primer: product-style CRUD (komentarisano) ----------------------
    /*
    @Headers({"User-Agent: Mobile-Android", "Content-Type:application/json"})
    @GET("product/")
    Call<ArrayList<Product>> getAllProducts();

    @Headers({"User-Agent: Mobile-Android", "Content-Type:application/json"})
    @GET("product/{id}")
    Call<Product> getProductById(@Path("id") Long id);

    @Headers({"User-Agent: Mobile-Android", "Content-Type:application/json"})
    @POST("product/")
    Call<Product> addProduct(@Body Product product);

    @Headers({"User-Agent: Mobile-Android", "Content-Type:application/json"})
    @DELETE("product/{id}")
    Call<ResponseBody> deleteProductById(@Path("id") Long id);

    @Headers({"User-Agent: Mobile-Android", "Content-Type:application/json"})
    @PUT("product/")
    Call<Product> editProduct(@Body Product product);
    */

}



