# 🚀 Vežba 7 — Retrofit HTTP Zahtevi (Cheat-sheet za kolokvijum)

## ❓ ZADATAK
> 1. Podesiti retrofit i omogućiti slanje HTTP zahteva ka sajtu `https://app.beeceptor.com/mock-server/dummy-json`.  
> 2. Kreirati potrebne klase u modelu potrebne za postove, korisnike i komentare.  
> 3. Odraditi GET zahteve za prvi post i drugi komentar i ispisati ih u posebnim TextView poljima.  
> 4. U Toast poruci prikazati koliko ima korisnika.

---

# 📋 DEO 1: PODESITI RETROFIT I OMOGUĆITI HTTP ZAHTEVE

## Šta trebam da dodam?

**Gde:** `app/build.gradle` sekcija `dependencies`

**KOPIRAJ I LEPIM:**
```groovy
// Retrofit & Gson
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

Posle: **Sync Now** (desni klik ili Ctrl+Enter).

---

## Dodaj INTERNET permisiju

**Gde:** `app/src/main/AndroidManifest.xml` (pre `</manifest>`)

**KOPIRAJ I LEPIM:**
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## Kreiraj `RetrofitClient.java`

**Gde:** `app/src/main/java/com/example/vezbe7/api/RetrofitClient.java`

**KOPIRAJ I LEPIM (sve):**
```java
package com.example.vezbe7.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Za Beeceptor dummy-json (preporuka)
    private static final String BASE_URL = "https://dummy-json.mock.beeceptor.com/";

    // Ako profesor traži drugu adresu, menjaš SAMO ovaj red:
    // private static final String BASE_URL = "https://app.beeceptor.com/mock-server/dummy-json/";

    // Ako je drugi mock server:
    // private static final String BASE_URL = "https://neki-drugi-mock.mock.beeceptor.com/";

    // Važno: BASE_URL mora da se završava sa "/".
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
```

**Šta se MENJA ako je drugačiji server:**
- `BASE_URL` → zameni samo adresu, ostatak ostaje isti

---

# 📦 DEO 2: KREIRAJ MODEL KLASE (Post, Comment, User)

## `Post.java`

**Gde:** `app/src/main/java/com/example/vezbe7/model/Post.java`

**KOPIRAJ I LEPIM (sve):**
```java
package com.example.vezbe7.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    // Ako server vraća drugačija imena polja, menjaš samo @SerializedName.
    // Primer: ako umesto title dođe subject -> @SerializedName("subject") private String title;

    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public Post() {}
    public Post(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "Post{id=" + id + ", userId=" + userId + ", title='" + title + "'}";
    }
}
```

---

## `Comment.java`

**Gde:** `app/src/main/java/com/example/vezbe7/model/Comment.java`

**KOPIRAJ I LEPIM (sve):**
```java
package com.example.vezbe7.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    // Ako se JSON polja razlikuju, ovde menjaš samo @SerializedName.
    // Primer: ako email nije email nego userEmail -> @SerializedName("userEmail") private String email;

    @SerializedName("id")
    private int id;
    @SerializedName("postId")
    private int postId;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("body")
    private String body;

    public Comment() {}
    public Comment(int id, int postId, String name, String email, String body) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "Comment{id=" + id + ", postId=" + postId + ", name='" + name + "'}";
    }
}
```

---

## `User.java`

**Gde:** `app/src/main/java/com/example/vezbe7/model/User.java` (već treba da postoji — POTVRĐENO!)

**Proveri da ima:**
```java
@SerializedName("name")
private String name;

@SerializedName("email")
private String email;

@SerializedName("phone")
private String phone;
```

**Ako zadatak traži druga imena, menjaš samo ove anotacije:**
```java
// Primer:
// @SerializedName("firstName") private String name;
// @SerializedName("lastName") private String surname;
// @SerializedName("email") private String email;
// @SerializedName("phone") private String phone;
```

---

## `ApiService.java`

**Gde:** `app/src/main/java/com/example/vezbe7/api/ApiService.java`

**KOPIRAJ I LEPIM (sve):**
```java
package com.example.vezbe7.api;

import com.example.vezbe7.model.Comment;
import com.example.vezbe7.model.Post;
import com.example.vezbe7.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // GET = čitanje podataka
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("comments")
    Call<List<Comment>> getComments();

    @GET("users")
    Call<List<User>> getUsers();

    // Ako zadatak traži POST / PUT / DELETE, samo dodaj/odkomentariši potrebnu metodu:

    // POST = dodavanje novog podatka
    // @POST("posts")
    // Call<Post> addPost(@Body Post post);

    // PUT = izmena celog podatka
    // @PUT("posts/{id}")
    // Call<Post> updatePost(@Path("id") int id, @Body Post post);

    // DELETE = brisanje podatka
    // @DELETE("posts/{id}")
    // Call<Void> deletePost(@Path("id") int id);
}
```

---

# 🎯 DEO 3: KREIRAJ GET ZAHTEVE I ISPIS U TextView

## U `MainActivity.java`

### 3a. Dodaj TV reference (top dela klase, pre `onCreate`)

**KOPIRAJ I LEPIM:**
```java
private TextView tvFirstPost;
private TextView tvSecondComment;
private TextView tvUsersCount;
private ApiService apiService;
```

### 3b. U `onCreate()` inicijalizuj

**KOPIRAJ I LEPIM u onCreate():**
```java
tvFirstPost = findViewById(R.id.tvFirstPost);
tvSecondComment = findViewById(R.id.tvSecondComment);
tvUsersCount = findViewById(R.id.tvUsersCount);

apiService = RetrofitClient.getApiService();

loadFirstPost();
loadSecondComment();
loadUsersCount();

// Ako treba drugi element iz niza, promeni indeks u metodi:
// prvi post -> posts.get(0)
// drugi post -> posts.get(1)
// treći post -> posts.get(2)

// prvi komentar -> comments.get(0)
// drugi komentar -> comments.get(1)
// treći komentar -> comments.get(2)
```

### 3c. Dodaj tri metode (na kraju MainActivity klase, pre zatvorene zagrade)

**KOPIRAJ I LEPIM sve tri:**

```java
private void loadFirstPost() {
    apiService.getPosts().enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Post> posts = response.body();
                if (!posts.isEmpty()) {
                    Post firstPost = posts.get(0); // promeni na get(1), get(2)... ako treba drugi/treći post
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
            }
        }
        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            tvFirstPost.setText("Greška pri učitavanju: " + t.getMessage());
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
                    Comment secondComment = comments.get(1); // promeni na get(0) za prvi komentar, get(2) za treći
                    String commentText = "ID: " + secondComment.getId() + "\n" +
                            "Post ID: " + secondComment.getPostId() + "\n" +
                            "Ime: " + secondComment.getName() + "\n" +
                            "Email: " + secondComment.getEmail() + "\n" +
                            "Tekst: " + secondComment.getBody();
                    tvSecondComment.setText(commentText);
                } else {
                    tvSecondComment.setText("Nema drugog komentara");
                }
            } else {
                tvSecondComment.setText("Neuspešan odgovor: " + response.code());
            }
        }
        @Override
        public void onFailure(Call<List<Comment>> call, Throwable t) {
            tvSecondComment.setText("Greška pri učitavanju: " + t.getMessage());
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
                Toast.makeText(MainActivity.this, "Broj korisnika: " + usersCount, Toast.LENGTH_SHORT).show();
            } else {
                tvUsersCount.setText("Neuspešan odgovor: " + response.code());
            }
        }
        @Override
        public void onFailure(Call<List<User>> call, Throwable t) {
            tvUsersCount.setText("Greška pri učitavanju: " + t.getMessage());
        }
    });
}
```

---

### 3d. Proveri XML Layout

**Gde:** `app/src/main/res/layout/activity_main.xml` 

**Obavezno treba da imaš ova tri TextView sa ID-evima:**
```xml
<TextView android:id="@+id/tvFirstPost" ... />
<TextView android:id="@+id/tvSecondComment" ... />
<TextView android:id="@+id/tvUsersCount" ... />
```

Ako ne imaš, dodaj ih.

---

### 3e. Import-i

**U vrhu MainActivity.java dodaj (ako Android Studio nije automatski):**
```java
import com.example.vezbe7.api.ApiService;
import com.example.vezbe7.api.RetrofitClient;
import com.example.vezbe7.model.Comment;
import com.example.vezbe7.model.Post;
import com.example.vezbe7.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
```

---

# 🔴 TROUBLESHOOTING — Česti problemi

| Problem | Uzrok | Rešenje |
|---------|-------|--------|
| **404 greška** | Ruta pogrešna | Testiraj u browser-u: `BASE_URL + /posts` |
| **HTML umesto JSON** | Dashboard umesto API-ja | Koristi `https://dummy-json.mock.beeceptor.com/` |
| **"Failed to connect"** | Nema interneta | Proveri emulator ima internet; testiraj `ping` iz PowerShell |
| **JSON parse error** | Polja ne odgovaraju | Proveri `@SerializedName` — mora biti istu kao JSON ključ |
| **NullPointerException** | TextView ID-evi ne postoje u XML | Dodaj `@+id/tvFirstPost` itd. u XML |

---

# 🧪 TEST KOMANDE (PowerShell)

```powershell
# Provera da li server ima JSON
Invoke-RestMethod -Uri 'https://dummy-json.mock.beeceptor.com/posts'
Invoke-RestMethod -Uri 'https://dummy-json.mock.beeceptor.com/comments'
Invoke-RestMethod -Uri 'https://dummy-json.mock.beeceptor.com/users'
```

Ako vidiš JSON → OK!  
Ako vidiš HTML → pogrešna adresa!

---

# 💾 BUILD & RUN (PowerShell)

```powershell
cd 'C:\Users\tijan\AndroidStudioProjects\Vezbe7'
.\gradlew.bat clean assembleDebug
.\gradlew.bat installDebug
```

---

# 🎓 QUICK MEMORY (za usmeni — 5 sekundi)

1. **BASE_URL** = `https://dummy-json.mock.beeceptor.com/`
2. **ApiService** = interfejce sa `@GET(...)` — Retrofit spaja BASE_URL + ruta
3. **Model klase** koriste `@SerializedName("key")` da mapiraju JSON na Java polja
4. **MainActivity** poziva API `apiService.getPosts().enqueue(...)` i upišeš u TextView
5. **Ako vidiš HTML** → pogodila si dashboard; koristi pravo mock hostname

---

✅ **GOTOVO! Spreman za kolokvijum i predaju!**

