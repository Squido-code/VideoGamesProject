package com.guillermo.videoGamesProject.ui.principal.controller;

import com.guillermo.videoGamesProject.domain.Platform;
import com.guillermo.videoGamesProject.ui.principal.model.PrincipalModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;




public class PrincipalController {
    public ListView<Platform> lvPlatforms;
    public ListView<String> lvDetails;
    private PrincipalModel principalModel;
    public ImageView ivDetails;
    private ObservableList<String> olDetails;


//    public PrincipalController() {
//        this.principalModel = new PrincipalModel();
//
//    }

    @FXML
    public void initialize() {
        olDetails = FXCollections.observableArrayList();
        principalModel = PrincipalModel.builder()
                .ivDetails(ivDetails)
                .lvDetails(lvDetails)
                .olDetails(olDetails)
                .lvPlatforms(lvPlatforms)
                .build();
        principalModel.start();
        lvDetails.setItems(olDetails);
    }

    @FXML
    public void platformSelected() {
        Platform platform = lvPlatforms.getSelectionModel().getSelectedItem();
        principalModel.showDetails(platform, platform.getImage_background());
    }
}
