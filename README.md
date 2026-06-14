# 7. Vežba - Retrofit HTTP Zahtevi

## 📋 Pregled Projekta

Ovaj projekat implementira **HTTP zahteve korišćenjem Retrofit biblioteke** ka mock serveru na adresi https://app.beeceptor.com/mock-server/.

## ✅ Šta je Urađeno

### 1. **Konfiguracija Retrofit-a**
- Dodate zavisnosti u `build.gradle`:
  - `com.squareup.retrofit2:retrofit:2.9.0`
  - `com.squareup.retrofit2:converter-gson:2.9.0`

### 2. **Model Klase** (u paketu `com.example.vezbe7.model`)
- **User.java** - Predstavlja korisnika sa poljima:
  - `id` - ID korisnika
  - `firstName` - Ime
  - `lastName` - Prezime
  - `email` - Email adresa
  - `phone` - Telefonski broj

- **Post.java** - Predstavlja post sa poljima:
  - `id` - ID posta
  - `userId` - ID korisnika koji je kreirao post
  - `title` - Naslov posta
  - `body` - Sadržaj posta

- **Comment.java** - Predstavlja komentar sa poljima:
  - `id` - ID komentara
  - `postId` - ID posta na kojem je komentar
  - `name` - Ime autora komentara
  - `email` - Email autora
  - `body` - Tekst komentara

### 3. **API Servis** (u paketu `com.example.vezbe7.api`)
- **ApiService.java** - Interface sa GET zahtevima:
  ```java
  @GET("posts")
  Call<List<Post>> getPosts();
  
  @GET("comments")
  Call<List<Comment>> getComments();
  
  @GET("users")
  Call<List<User>> getUsers();
  ```

- **RetrofitClient.java** - Singleton klasa za Retrofit inicijalizaciju:
  - `BASE_URL = "https://app.beeceptor.com/mock-server/"`
  - Koristi Gson converter za serijalizaciju/deserijalizaciju JSON-a

### 4. **MainActivity Logika**
Implementirani su sledeći zahtevi:

#### a) **GET zahtev za prvi post**
```java
loadFirstPost() {
  - Učitava sve postove sa API-ja
  - Prikazuje PRVI post u TextView (tvFirstPost)
  - Prikazuje: ID, Korisnik ID, Naslov i Tekst
}
```

#### b) **GET zahtev za drugi komentar**
```java
loadSecondComment() {
  - Učitava sve komentare sa API-ja
  - Prikazuje DRUGI komentar u TextView (tvSecondComment)
  - Prikazuje: ID, Post ID, Ime autora, Email i Tekst
}
```

#### c) **GET zahtev za broj korisnika**
```java
loadUsersCount() {
  - Učitava sve korisnike sa API-ja
  - Prikazuje broj korisnika u TextView (tvUsersCount)
  - Prikazuje Toast poruku sa brojem korisnika
}
```

### 5. **Layout** (`activity_main.xml`)
Tri TextView polja za prikaz podataka:
- `tvFirstPost` - Prvi post
- `tvSecondComment` - Drugi komentar
- `tvUsersCount` - Broj korisnika

### 6. **Permisije**
Dodata INTERNET permisija u `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## 🔧 Kako Funkcioniše?

1. **App se pokreće** → MainActivity se učitava
2. **onCreate()** → Pozivaju se tri metode za učitavanje podataka
3. **API zahtevi** se izvršavaju asinkreno (Callback)
4. **Odgovori se obrađuju** i prikazuju u TextView poljima
5. **Toast** automatski prikazuje broj korisnika kad je učitan

## 📱 Testiranje

Za testiranje aplikacije:
1. Preuzmite projekat u Android Studio
2. Pokrenite na emulator ili fizičkom uređaju
3. Aplikacija će automatski učitati i prikazati:
   - Prvi post sa detaljima
   - Drugi komentar sa detaljima
   - Toast sa brojem korisnika

## 📂 Struktura Fajlova

```
Vezbe7/
├── app/
│   ├── build.gradle (sa Retrofit zavisnostima)
│   ├── src/main/
│   │   ├── AndroidManifest.xml (sa INTERNET permisijom)
│   │   ├── java/com/example/vezbe7/
│   │   │   ├── MainActivity.java (glavna aktivnost sa logikom)
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   ├── Post.java
│   │   │   │   └── Comment.java
│   │   │   └── api/
│   │   │       ├── ApiService.java (interface sa GET zahtevima)
│   │   │       └── RetrofitClient.java (Retrofit konfiguracija)
│   │   └── res/layout/
│   │       └── activity_main.xml (sa 3 TextView polja)
```

## 🌐 API Endpoint

- **Base URL**: `https://app.beeceptor.com/mock-server/`
- **Dostupne rute**:
  - `/posts` - Svi postovi
  - `/comments` - Svi komentari
  - `/users` - Svi korisnici

## 💡 Ključne Tehnologije

- **Retrofit** - HTTP klijent sa type-safe zahtevima
- **Gson** - JSON serijalizacija/deserijalizacija
- **Callback** - Asinkreni obrada odgovora
- **AsyncTask** - Pozadinski niti za mrežne zahteve

## 🎯 Zahtevi Zadatka - Implementirano ✅

- ✅ 1. Retrofit konfiguriran i HTTP zahtevi omogućeni
- ✅ 2. Model klase kreirane (Post, User, Comment)
- ✅ 3. GET zahtevi za prvi post i drugi komentar sa prikazom u TextView
- ✅ 4. Toast poruka sa brojem korisnika
- ✅ 5. INTERNET permisija dodata

## 📝 Napomene

- Svi zahtevi su asinkreni - aplikacija neće "zamrzavati" tokom učitavanja
- Greške se hvataju i prikazuju u TextView poljima
- Podaci se prikazuju samo prvi put - nema refresh mehanizma (može se dodati kasnije)

---

**Status**: ✅ Kompletna implementacija 7. vežbe - Retrofit HTTP zahtevi

