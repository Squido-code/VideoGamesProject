package com.guillermo.videoGamesProject.ui.principal.controller;

import com.guillermo.videoGamesProject.api.ApiHelper.GetAllPlatformsHelper;
import com.guillermo.videoGamesProject.api.ApiHelper.GetPlatformGamesHelper;
import com.guillermo.videoGamesProject.api.service.VideogamesApiServiceImpl;
import com.guillermo.videoGamesProject.domain.Platform;
import com.guillermo.videoGamesProject.ui.principal.model.PrincipalModel;
import io.reactivex.rxjava3.internal.operators.observable.ObservableLift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;


public class PrincipalController {
    public ListView<Platform> lvPlatforms;
    public ListView lvDetails;
    private PrincipalModel principalModel;

    public PrincipalController() {
        this.principalModel = new PrincipalModel();
    }

    @FXML
    public void initialize(){
        principalModel.start(lvPlatforms);
    }
    @FXML
    public void platformSelected(){
        Platform platform = lvPlatforms.getSelectionModel().getSelectedItem();

    }
}
