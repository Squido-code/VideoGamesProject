package com.guillermo.videoGamesProject.ui.principal;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.List;

public interface PrincipalInterface {
    interface view{
        void principalUi();
    }
    interface Controller{
        void initialize();
        void consoleSelected();
        void gameSelected();
        void SearchConsoleByname();
        void SearchGameByname();
    }
    interface Model{
        void start();
        void setVideogames(String id);
        void showDetails(Object object, String uri);
        List<String> ObjectToString(Object object);
        void listToObservableList(List<String> list, ObservableList<String> obList);
        void setImage(String uri);
    }
}
