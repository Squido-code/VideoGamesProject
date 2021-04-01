package com.guillermo.videoGamesProject.ui.principal.controller;

import com.guillermo.videoGamesProject.domain.Console;
import com.guillermo.videoGamesProject.domain.Videogame;
import com.guillermo.videoGamesProject.ui.principal.model.PrincipalModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class PrincipalController {
    public ListView<Console> lvConsole;
    public ListView<String> lvDetails;
    public ImageView ivDetails;
    public ListView<Videogame> lvGames;

    private PrincipalModel principalModel;
    private ObservableList<String> olDetails;


    @FXML
    public void initialize() {
        olDetails = FXCollections.observableArrayList();
        principalModel = PrincipalModel.builder()
                .ivDetails(ivDetails)
                .lvDetails(lvDetails)
                .olDetails(olDetails)
                .lvConsole(lvConsole)
                .lvGames(lvGames)
                .build();
        principalModel.start();
        lvDetails.setItems(olDetails);
    }

    @FXML
    public void consoleSelected() {
        Console console = lvConsole.getSelectionModel().getSelectedItem();
        principalModel.showDetails(console, console.getImage_background());
        principalModel.setVideogames(console.getId());
    }

    @FXML
    public void gameSelected() {
        Videogame videogame = lvGames.getSelectionModel().getSelectedItem();
        principalModel.showDetails(videogame, videogame.getBackground_image());
    }
}
