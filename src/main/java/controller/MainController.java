package main.java.controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.db.Database;

public class MainController extends Application {

    private final int minHeight = 800;
    private final int minWidth = 1280;

    public void startup(final String[] args) {
        launch(args);
    }

    public void start(final Stage stage) {
        ViewController view = new ViewController();

        stage.setTitle("Sagrada Groep J");
        stage.getIcons().add(new Image("file:resources/img/app-icon.png"));

        stage.setScene(view);

        stage.setMinHeight(this.minHeight);
        stage.setMinWidth(this.minWidth);

        stage.centerOnScreen();
        stage.isAlwaysOnTop();

        stage.show();
    }

    public void stop() {
        Database.getInstance().close();
        System.exit(0);
    }
}
