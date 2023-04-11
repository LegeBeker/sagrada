package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.java.controller.GameController;
import main.java.controller.ViewController;
import main.java.model.Player;

public class NewGameView extends HBox {

    private ViewController view;
    private ToggleGroup cards;
    private ArrayList<Player> players;

    public NewGameView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
        Font titleFont = new Font("Arial", 24); //TODO make default font conquer the world
        Font buttonFont = new Font("Arial", 16);
        
        // label
        Label title = new Label("Nieuw spel aanmaken");
        title.setFont(titleFont); 
        title.setTextFill(Color.ALICEBLUE);
        
        // 2 radio buttons for default cards and random cards
        cards = new ToggleGroup();
        
        RadioButton standardRb = new RadioButton("Standaard");
        standardRb.setText("Standaard");
        standardRb.setToggleGroup(cards);
        standardRb.setSelected(true);
        standardRb.setTextFill(Color.ALICEBLUE);
        
        RadioButton randomRb = new RadioButton("Willekeurig");
        randomRb.setText("Willekeurig");
        randomRb.setToggleGroup(cards);
        randomRb.setTextFill(Color.ALICEBLUE);
        
        // create game button
        Button create = new Button("Maak nieuw spel aan");
        create.setFont(buttonFont);
        create.setOnAction(e -> createGame());
        
        // return button
        Button back = new Button("Terug");
        back.setFont(buttonFont);
        back.setOnAction(e -> goBack());
        
        VBox buttonPane = new VBox();
        buttonPane.getChildren().setAll(title, standardRb, randomRb, create, back);
        buttonPane.setPadding(new Insets(10));
        
        // TODO choose players here
        ScrollPane playerList = new ScrollPane();
        
        // TODO order things
        
        this.getChildren().addAll(buttonPane, playerList);
    }
    
    private void createGame() {
        RadioButton setting = (RadioButton) this.cards.getSelectedToggle();
        String settingText = setting.getText();
        
        boolean useDefaultCards = true;
        if(settingText.equals("Willekeurig")) {
            useDefaultCards = false;
        }
        
        new GameController().createGame(this.players, useDefaultCards);   
    }
    
    private void goBack() {
        this.view.openGamesView();
    }
}
