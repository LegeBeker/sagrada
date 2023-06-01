package main.java.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import main.java.controller.ViewController;

public class GameTopView extends HBox{

    private static final int COUNTERTOPVIEWWIDTH = 450;
    
    public GameTopView(final ViewController view) {
        super();
        this.getChildren().addAll(new GameOfferView(view), new RoundTrackView(view), new Rectangle(COUNTERTOPVIEWWIDTH, 0));
        this.getChildren().forEach(child -> {
            HBox.setHgrow(child, Priority.ALWAYS);
        });
    }
}
