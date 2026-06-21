package com.example.vezbe7.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Osnovna adresa servera (BASE_URL). Ako profesor zahteva drugu adresu,
    // promeni samo ovu konstantu. OBAVEZNO zadrži "/" na kraju.
    private static final String BASE_URL = "https://dummy-json.mock.beeceptor.com/";
    // Primer alternativnih adresa (komentarisano):
    // private static final String BASE_URL = "https://app.beeceptor.com/mock-server/dummy-json/";
    // private static final String BASE_URL = "https://neki-drugi-mock.mock.beeceptor.com/";

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

