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
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class RoundTrackView extends StackPane implements Observer {
    private static final int PADDING = 5;
    private static final int SIZE = 32;
    private static final int TEXTPADDING = 2;
    private static final int ROUNDS = 10;
    private static final int ROWAMOUNT = 5;
    private static final int ROUNDING = 20;

    private static final int WIDTH = 190;
    private static final int HEIGHT = 120;

    private static final double SCALEINCREASE = 1.75;

    private static final int LEFTANIMATEOFFSET = 200;
    private static final int BOTTOMANIMATEOFFSET = 100;

    private Game game;

    private final ArrayList<Group> roundGroups;

    public RoundTrackView(final ViewController view, final Game game) {
        this.game = game;
        this.roundGroups = new ArrayList<>();

        this.setMaxSize(WIDTH, HEIGHT);
        this.setPadding(new Insets(PADDING));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(PADDING));
        gridPane.setVgap(PADDING);
        gridPane.setHgap(PADDING);

        this.setAlignment(Pos.CENTER_RIGHT);
        gridPane.setAlignment(Pos.CENTER_RIGHT);

        for (int i = 0; i < ROUNDS; i++) {
            VBox vbox = new VBox();
            Group group = new Group();
            Rectangle rectangle = new Rectangle(SIZE, SIZE);
            rectangle.setFill(Color.BEIGE);
            group.getChildren().add(rectangle);
            roundGroups.add(group);
            Text text = new Text(Integer.toString(i + 1));
            text.setFill(Color.WHITE);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(TEXTPADDING));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(group, textFlow);
            if (i >= ROWAMOUNT) {
                gridPane.add(vbox, i - ROWAMOUNT, 2);
            } else {
                gridPane.add(vbox, i, 1);
            }
        }

        Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);
        rectangle.setFill(Color.RED);
        rectangle.setOpacity(0.5);
        rectangle.setArcHeight(ROUNDING);
        rectangle.setArcWidth(ROUNDING);
        this.getChildren().add(rectangle);
        this.getChildren().add(gridPane);

        Observable.addObserver(Game.class, this);

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
