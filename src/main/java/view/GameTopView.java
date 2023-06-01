package main.java.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import main.java.controller.ViewController;

public class GameTopView extends HBox{

    private final static double PARTOFTHESCREEN = 4.75;
    
    public GameTopView(final ViewController view) {
        super();
        final double screenWidth = Screen.getPrimary().getBounds().getMaxX();
        this.getChildren().addAll(new GameOfferView(view), new RoundTrackView(view), new Rectangle(screenWidth / PARTOFTHESCREEN, 0));
        this.getChildren().forEach(child -> {
            HBox.setHgrow(child, Priority.ALWAYS);
        });
    }
}
