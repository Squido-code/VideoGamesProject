package com.guillermo.videoGamesProject.ui.principal.model;

import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;

public class PrincipalModel {
    public void start(ListView listView){
        VideogamesApiServiceImpl videogamesApiService = new VideogamesApiServiceImpl();
        ObservableList<Platform> platformObservableList = FXCollections.observableArrayList();

        videogamesApiService.getAllPlatforms()
                .flatMapIterable(getAllPlatformsHelper ->getAllPlatformsHelper.getResults())
                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .subscribe(platform -> platformObservableList.add(platform));
        listView.setItems(platformObservableList);
            }
}
