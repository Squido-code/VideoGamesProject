package com.guillermo.videoGamesProject.ui.principal.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetAllPlatformsHelper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetPlatformGamesHelper;
import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Console;
import com.guillermo.videoGamesProject.domain.Videogame;
import com.guillermo.videoGamesProject.ui.principal.PrincipalInterface;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Builder;
import lombok.Data;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    /**
     * Populate the ConsoleListview when the program starts with the list of platforms from the API.
     */
    public void start() {
        piConsole.setOpacity(1);
        videogamesApiService.getAllPlatforms()
                .flatMapIterable(GetAllPlatformsHelper::getResults)
                .doOnCompleted(() -> Platform.runLater(() -> piConsole.setOpacity(0)))
                .subscribe(consoleObservableList::add);
    }

    /**
     * populate the Gamelistview with the games asgined to an especific platform.
     *
     * @param id API console ID
     */
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

    /**
     * Show the attributes of an specific object at the ui.
     *
     * @param object Object which expect a Console or Videogame
     * @param uri    String with the adress of the picture
     */
    public void showDetails(Object object, String uri) {
        List<String> list = ObjectToString(object);
        if (!olDetails.isEmpty()) {
            olDetails.clear();
        }
        listToObservableList(list, olDetails);
        setImage(uri);
    }

    /**
     * The method takes the attributes of an object minus the image URL and give them back as a List.
     *
     * @param object Object which expect a Console or Videogame
     * @return List of Strings with the attrubutes of an espeficic object
     */
    public List<String> ObjectToString(Object object) {
        List<String> list = new ArrayList<>();
        Map<String, String> map = new ObjectMapper().convertValue(object, Map.class);
        if (!map.isEmpty()) {
            map.remove("image_background");
            map.remove("background_image");
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(entry.getKey() + ": " + entry.getValue());
        }
        return list;
    }

    /**
     * convert a list into an observableList.
     *
     * @param list   List<>
     * @param obList ObservableList
     */
    public void listToObservableList(List<String> list, ObservableList<String> obList) {
        obList.addAll(list);
    }

    /**
     * show at the UI the a picture
     *
     * @param uri String of the URI
     */
    public void setImage(String uri) {
        Image image = new Image(uri);
        ivDetails.setImage(image);
    }

    /**
     * Method that filters the content by name or partial name and display it. This creates a backupList which can be invoke to undo the changes.
     *
     * @param listOf String key word: console for platform and game for videogames
     * @param name   String to search
     */
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

    /**
     * delete de filters applied to the list of platforms.
     * This use the backuplist created.
     */
    public void resetGames() {
        videogameObservableList.clear();
        videogameObservableList.addAll(backupGameList);
    }

    /**
     * delete de filters applied to the list of videogames.
     * This use the backuplist created.
     */
    public void resetConsole() {
        consoleObservableList.clear();
        consoleObservableList.addAll(backupConsoleList);
    }

    /**
     * Method that export the content shown by the listviews, to choose which list you need to send the keyword as parameter.
     *
     * @param stage  Stage
     * @param listOf String key word: console for platform and game for videogames
     */
    public void exportToCSV(Stage stage, String listOf) {
        File file = fileChooser(stage);
        listToCSV(listOf, file);
    }

    /**
     * Create a file to storage the CSV, only create csv files.
     *
     * @param stage Stage
     * @return File
     */
    private File fileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        return fileChooser.showSaveDialog(stage);
    }

    /**
     * Method that fill a csv file with the content of one of the listviews, this is chose by the keywork as parameter.
     *
     * @param listOf String key word: console for platform and game for videogames
     * @param file   File were to write the csv content
     */
    private void listToCSV(String listOf, File file) {
        switch (listOf) {
            case "console":
                try {
                    Writer outputFile = new FileWriter(file);
                    CSVWriter writer = new CSVWriter(outputFile);
                    StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
                    for (Console console : consoleObservableList) {
                        beanToCsv.write(console);
                    }
                    writer.close();
                } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
                    e.printStackTrace();
                }
                break;
            case "game":
                try {
                    Writer outputFile = new FileWriter(file);
                    CSVWriter writer = new CSVWriter(outputFile);
                    StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
                    for (Videogame videogame : videogameObservableList) {
                        beanToCsv.write(videogame);
                    }
                    writer.close();
                } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

}
