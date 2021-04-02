package com.guillermo.videoGamesProject.ui.principal.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetAllPlatformsHelper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetPlatformGamesHelper;
import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Console;
import com.guillermo.videoGamesProject.domain.Videogame;
import com.guillermo.videoGamesProject.ui.principal.PrincipalInterface;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Builder;
import lombok.Data;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.guillermo.videoGamesProject.ui.principal.controller.PrincipalController.consoleObservableList;
import static com.guillermo.videoGamesProject.ui.principal.controller.PrincipalController.videogameObservableList;

@Data
@Builder
public class PrincipalModel implements PrincipalInterface.Model {
    private ListView<Console> lvConsole;
    private ListView<String> lvDetails;
    private ImageView ivDetails;
    private ObservableList<String> olDetails;
    private VideogamesApiServiceImpl videogamesApiService;
    private ProgressIndicator piConsole;
    private ProgressIndicator piGames;
    private ObservableList<Videogame> backupGameList;
    private ObservableList<Console> backupConsoleList;

    public void start() {
        piConsole.setOpacity(1);
        videogamesApiService.getAllPlatforms()
                .flatMapIterable(GetAllPlatformsHelper::getResults)
                .doOnCompleted(() -> Platform.runLater(() -> piConsole.setOpacity(0)))
                .subscribe(consoleObservableList::add);
    }

    public void setVideogames(String id) {
        /**
         * No he conseguido evitar el error Not on FX application thread;
         * He intentado usar Platform.runLater para meter el cambio del observableList en el mismo thread sin exito.
         * He probado varias combinaciones usando las listviews sin exito tampoco.
         * Mi objetivo es que se acutalice el observableList y automaticamente ya el Listview lo muestre.
         * Apesar de esta excepcion la Listview muestra la lista actualizada.
         */
        piGames.setOpacity(1);
        videogamesApiService.getPlatformGames(id)
                .flatMapIterable(GetPlatformGamesHelper::getResults)
                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .doOnCompleted(() -> Platform.runLater(() -> piGames.setOpacity(0)))
                .subscribe(videogameObservableList::add);
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
        obList.addAll(list);
    }

    public void setImage(String uri) {
        Image image = new Image(uri);
        ivDetails.setImage(image);
    }

    public void searchByName(String listOf, String name) {
        /**
         * La API no acepta busquedas parciales
         */
        switch (listOf) {
            case "console":
                Stream<Console> consoleStream = consoleObservableList.stream();
                List<Console> consoleList = consoleStream
                        .filter(console -> console.getName().matches(".*" + name + ".*"))
                        .collect(Collectors.toList());
                backupConsoleList = FXCollections.observableArrayList(consoleObservableList);
                consoleObservableList.clear();
                consoleObservableList.addAll(consoleList);
                break;
            case "game":
                Stream<Videogame> videogameStream = videogameObservableList.stream();
                List<Videogame> videogameList = videogameStream
                        .filter(videogame -> videogame.getName().matches(".*" + name + ".*"))
                        .collect(Collectors.toList());
                backupGameList = FXCollections.observableArrayList(videogameObservableList);
                videogameObservableList.clear();
                videogameObservableList.addAll(videogameList);
                break;
        }

    }

    public void resetGames() {
        videogameObservableList.clear();
        videogameObservableList.addAll(backupGameList);
    }

    public void resetConsole() {
        consoleObservableList.clear();
        consoleObservableList.addAll(backupConsoleList);
    }
}
