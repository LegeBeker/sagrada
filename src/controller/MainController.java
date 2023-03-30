package controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController extends Application {
    public void startup(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        ViewController view = new ViewController();

        stage.setTitle("Sagrada Groep J");
        stage.getIcons().add(new Image("file:resources/img/app-icon.png"));

        stage.setScene(view);

        stage.setMinHeight(500);
        stage.setMinWidth(800);

        stage.centerOnScreen();
        stage.isAlwaysOnTop();

        stage.show();
    }
}
