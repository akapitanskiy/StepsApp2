package com.alexthekap.stepsapp.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a.kapitanskiy
 * on 23.08.2019
 *
 * Singleton
 */
public class RetrofitManager {
    private static Retrofit instance;

    private RetrofitManager() {
    }

    public static Retrofit getInstance() {
        if(instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl("https://intern-f6251.firebaseio.com/intern/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}
