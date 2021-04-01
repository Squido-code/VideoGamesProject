package com.guillermo.videoGamesProject.api.service;

import com.guillermo.videoGamesProject.api.ApiHelper.GetAllPlatformsHelper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetPlatformGamesHelper;
import com.guillermo.videoGamesProject.domain.Videogame;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static com.guillermo.videoGamesProject.util.Constants.*;

public class VideogamesApiServiceImpl {
    private final VideogamesApiService apiService;

    public VideogamesApiServiceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(VideogamesApiService.class);
    }

    public Observable<GetAllPlatformsHelper> getAllPlatforms() {

        return apiService.getAllPlatforms(APIKEY, PAGESIZE);
    }

    public Observable<GetPlatformGamesHelper> getPlatformGames(String platformId) {
        return apiService.getPlatformGames(APIKEY, PAGESIZE, platformId);

    }

    public Observable<Videogame> getVideogameById(int id) {
        return apiService.getVideogame(id, APIKEY);
    }
}
