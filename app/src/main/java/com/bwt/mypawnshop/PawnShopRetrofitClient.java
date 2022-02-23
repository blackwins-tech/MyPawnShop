package com.bwt.mypawnshop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PawnShopRetrofitClient {

    public static Retrofit pawnShopRetrofitClient;
    private static String PAWN_SHOP_URL = "https://lendingappbackend.herokuapp.com/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    public static Retrofit getRetrofitInstance() {
        if(pawnShopRetrofitClient == null){
            pawnShopRetrofitClient = new Retrofit.Builder()
                    .baseUrl(PAWN_SHOP_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return pawnShopRetrofitClient;
    }

}
