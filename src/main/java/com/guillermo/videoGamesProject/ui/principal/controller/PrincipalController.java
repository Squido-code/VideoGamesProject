package com.guillermo.videoGamesProject.ui.principal.controller;

import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Console;
import com.guillermo.videoGamesProject.domain.Videogame;
import com.guillermo.videoGamesProject.ui.principal.PrincipalInterface;
import com.guillermo.videoGamesProject.ui.principal.model.PrincipalModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Data;


@Data
public class PrincipalController implements PrincipalInterface.Controller {
    public ListView<Console> lvConsole;
    public ListView<String> lvDetails;
    public ImageView ivDetails;
    public ListView<Videogame> lvGames;
    public ProgressIndicator piConsole;
    public ProgressIndicator piGames;
    public static ObservableList<Console> consoleObservableList = FXCollections.observableArrayList();
    public static ObservableList<Videogame> videogameObservableList = FXCollections.observableArrayList();

    private PrincipalModel principalModel;
    private ObservableList<String> olDetails;
    public TextField tfConsoleName;
    public TextField tfGameName;

    @FXML
    public void initialize() {
        olDetails = FXCollections.observableArrayList();
        principalModel = PrincipalModel.builder()
                .ivDetails(ivDetails)
                .lvDetails(lvDetails)
                .olDetails(olDetails)
                .lvConsole(lvConsole)
                .piConsole(piConsole)
                .piGames(piGames)
                .videogamesApiService(new VideogamesApiServiceImpl())
                .build();
        lvDetails.setItems(olDetails);
        lvConsole.setItems(consoleObservableList);
        lvGames.setItems(videogameObservableList);
    }

    @FXML
    public void consoleSelected() {
        Console console = lvConsole.getSelectionModel().getSelectedItem();
        lvGames.getItems().clear();
        principalModel.showDetails(console, console.getImage_background());
        principalModel.setVideogames(console.getId());
    }

    @FXML
    public void gameSelected() {
        Videogame videogame = lvGames.getSelectionModel().getSelectedItem();
        principalModel.showDetails(videogame, videogame.getBackground_image());
    }

    @Override
    public void SearchConsoleByname() {
        String name = tfConsoleName.getText();
        principalModel.searchByName("console", name);
    }

    @Override
    public void SearchGameByname() {
        String name = tfGameName.getText();
        principalModel.searchByName("game", name);
    }

    @Override
    public void resetGame() {
        tfGameName.clear();
        principalModel.resetGames();
    }

    @Override
    public void resetConsole() {
        tfConsoleName.clear();
        principalModel.resetConsole();
    }
}
