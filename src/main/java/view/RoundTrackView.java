package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.Die;
import main.java.model.Game;
import main.java.pattern.Observer;

public class RoundTrackView extends StackPane implements Observer {
    private final int padding = 5;
    private final int size = 32;
    private final int textPadding = 2;
    private final int rounds = 10;
    private final int rowAmount = 5;
    private final int rounding = 20;

    private final int width = 190;
    private final int height = 120;

    private final double scaleIncrease = 1.75;

    private final int leftAnimateOffset = 200;
    private final int bottomAnimateOffset = 100;

    private Game game;

    private final ArrayList<Group> roundGroups;

    public RoundTrackView(final ViewController view, final Game game) {
        this.game = game;
        this.roundGroups = new ArrayList<>();

        this.setMaxSize(width, height);
        this.setPadding(new Insets(padding));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(padding));
        gridPane.setVgap(padding);
        gridPane.setHgap(padding);

        this.setAlignment(Pos.CENTER_RIGHT);
        gridPane.setAlignment(Pos.CENTER_RIGHT);

        for (int i = 0; i < rounds; i++) {
            VBox vbox = new VBox();
            Group group = new Group();
            Rectangle rectangle = new Rectangle(size, size);
            rectangle.setFill(Color.BEIGE);
            group.getChildren().add(rectangle);
            roundGroups.add(group);
            Text text = new Text(Integer.toString(i + 1));
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(textPadding));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(group, textFlow);
            if (i >= rowAmount) {
                gridPane.add(vbox, i - rowAmount, 2);
            } else {
                gridPane.add(vbox, i, 1);
            }
        }

        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.RED);
        rectangle.setOpacity(0.5);
        rectangle.setArcHeight(rounding);
        rectangle.setArcWidth(rounding);
        this.getChildren().add(rectangle);
        this.getChildren().add(gridPane);

        view.effects().add3DHoverEffect(this, width, height, scaleIncrease, bottomAnimateOffset, leftAnimateOffset);

        game.addObserver(this);

        update();
    }

    @Override
    public void update() {
        for (Die die : game.getRoundTrack()) {
            roundGroups.get(die.getRoundTrack() - 1).getChildren()
                    .add(new DieView(die.getEyes(), die.getColor(), die.getNumber(), false));
        }
    }
}
