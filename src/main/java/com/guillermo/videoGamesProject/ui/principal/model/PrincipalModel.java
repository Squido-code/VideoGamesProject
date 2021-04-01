package com.guillermo.videoGamesProject.ui.principal.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public void showDetails(Object object,ListView<String> listView,ObservableList<String> details){
        List<String> list = ObjectToString(object);
        if(!details.isEmpty()){
            details.clear();
        }
        listToObservableList(list,details);
    }
    public List<String> ObjectToString(Object object){
        List<String> list = new ArrayList<>();
        Map<String, String> map = new ObjectMapper().convertValue(object,Map.class);
        if(!map.isEmpty()){
            map.remove("image_background");
        }

        for(Map.Entry<String,String> entry : map.entrySet()){
            list.add(entry.getKey()+": "+entry.getValue());
        }
        return list;
    }
    public void listToObservableList(List<String> list,ObservableList<String> obList){
        for(String string: list){
            obList.add(string);
        }
    }
}
