package com.guillermo.videoGamesProject.ui.principal.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetAllPlatformsHelper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetPlatformGamesHelper;
import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Console;
import com.guillermo.videoGamesProject.domain.Videogame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Builder;
import lombok.Data;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

@Data
@Builder
public class PrincipalModel {
    private ListView<Console> lvConsole;
    private ListView<String> lvDetails;
    private ImageView ivDetails;
    private ObservableList<String> olDetails;
    private VideogamesApiServiceImpl videogamesApiService;
    private ListView<Videogame> lvGames;

    public void start() {
        Task<Void> task = new Task() {
            @Override
            protected Object call() throws Exception {
                ObservableList<Console> consoleObservableList = FXCollections.observableArrayList();
                videogamesApiService.getAllPlatforms()
                        .flatMapIterable(GetAllPlatformsHelper::getResults)
                        .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                        .subscribe(consoleObservableList::add);
                lvConsole.setItems(consoleObservableList);
                return null;
            }
        };
        new Thread(task).start();
    }

    public void setVideogames(String id) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Videogame> videogameObservableList = FXCollections.observableArrayList();
                videogamesApiService.getPlatformGames(id)
                        .flatMapIterable(GetPlatformGamesHelper::getResults)
                        .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                        .subscribe(videogameObservableList::add);

                lvGames.setItems(videogameObservableList);
                return null;
            }
        };
        new Thread(task).start();
    }

    public void showDetails(Object object, String uri) {
        List<String> list = ObjectToString(object);
        if (!olDetails.isEmpty()) {
            olDetails.clear();
        }
        listToObservableList(list, olDetails);
        setImage(uri);
    }

    public List<String> ObjectToString(Object object) {
        List<String> list = new ArrayList<>();
        Map<String, String> map = new ObjectMapper().convertValue(object, Map.class);
        if(!map.isEmpty()){
            map.remove("image_background");
            map.remove("background_image");
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(entry.getKey() + ": " + entry.getValue());
        }
        return list;
    }

    public void listToObservableList(List<String> list, ObservableList<String> obList) {
        for (String string : list) {
            obList.add(string);
        }
    }

    public void setImage(String uri) {
        Image image = new Image(uri);
        ivDetails.setImage(image);
    }
}
