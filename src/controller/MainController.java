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


		//Set the stage icon
		stage.getIcons().add(new Image("file:resources/images/icon.jpeg"));

		

		stage.setTitle("Sagrada Groep J");
		
		stage.setScene(view);
		
		stage.centerOnScreen();		
		
		stage.show();
	}
}
