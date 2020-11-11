package com.eic.sera.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String base_url = "https://fuzzysera.herokuapp.com";



    private static Retrofit getRetrofitInstance(){   // Build our retrofit object

        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()) // class for json coversion
                .build();
    }

    public static ApiService getApiService(){
        return  getRetrofitInstance().create(ApiService.class);
    }
}
