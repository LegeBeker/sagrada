package main.java.view;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;

public class GameView extends BorderPane {

    public GameView(final ViewController view) {
        BorderPane left = new BorderPane();
        left.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");

        left.setTop(new GameInfoView(view));
        left.setBottom(new GameButtonsView(view));

        this.setLeft(left);
        this.setCenter(new GameCenterView(view));
        this.setRight(new GameChatView(view));

        this.setBackground(view.getBackground());
    }
}
