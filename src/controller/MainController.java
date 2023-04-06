package controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController extends Application {

    private final int minHeight = 500;
    private final int minWidth = 800;

    public void startup(final String[] args) {
        launch(args);
    }

    public void start(final Stage stage) {

        stage.show();
    }
}
