package main.java.view.GameViews;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameChatView extends HBox {

    private final Background background = new Background(new BackgroundFill(Color.web("#B00322"), null, null));
    private final Text text = new Text("Chat");

    
    public GameChatView(ViewController view, Game game) {
        this.setBackground(background);

        this.getChildren().add(text);

    }

}
