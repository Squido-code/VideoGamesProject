package com.guillermo.videoGamesProject;

import com.guillermo.videoGamesProject.ui.principal.view.PrincipalView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
//        VideogamesApiServiceImpl videogamesApiService = new VideogamesApiServiceImpl();
//        videogamesApiService.getAllPlatforms().flatMapIterable(getAllPlatformsHelper ->getAllPlatformsHelper.getResults())
//                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
//                .subscribe(System.out::println);
//        VideogamesApiServiceImpl videogamesApiService = new VideogamesApiServiceImpl();
//        videogamesApiService.getPlatformGames(5).flatMapIterable(getPlatformGamesHelper ->getPlatformGamesHelper.getResults())
//                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
//                .subscribe(System.out::println);
//        VideogamesApiServiceImpl videogamesApiService = new VideogamesApiServiceImpl();
//        videogamesApiService.getVideogameById(4200)
//                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
//                .subscribe(System.out::println);
//        Observable<GetAllPlatformsHelper> platformList = videogamesApiService.getAllPlatforms();
//        List<Videogame> videogames = videogamesApiService.getPlatformGames(7);
//        Videogame videogame = videogamesApiService.getVideogameById(4200);
//        System.out.println(videogame.toString());

//        String imageSource = "http://yourImageURL";
//
//        ImageView imageView = ImageViewBuilder.create()
//                .image(new Image(imageSource))
//                .build();

    }

    @Override
    public void start(Stage stage) throws Exception {
        new PrincipalView(stage).principalUi();
    }
}
