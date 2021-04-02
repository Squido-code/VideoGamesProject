package com.guillermo.videoGamesProject.ui.principal.view;

import com.guillermo.videoGamesProject.ui.principal.PrincipalInterface;
import com.guillermo.videoGamesProject.ui.principal.controller.PrincipalController;
import com.guillermo.videoGamesProject.util.Resources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

import java.io.IOException;
@AllArgsConstructor
public class PrincipalView implements PrincipalInterface.view {
    public Stage stage;

    public void principalUi() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Resources.getUI("principalUi.fxml"));
            PrincipalController controller = new PrincipalController();
            loader.setController(controller);
            VBox vBox = null;
            vBox = loader.load();
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.show();
            controller.getPrincipalModel().start();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
