package controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainController extends Application {
	public void startup(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		ViewController view = new ViewController();

		stage.setTitle("Sagrada Groep J");
		
		stage.setScene(view);
		
		stage.centerOnScreen();
		stage.setResizable(false);
		
		stage.show();
	}
}
