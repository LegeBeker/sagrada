package main.java.view;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class RoundTrackView extends StackPane implements Observer {
    private static final int PADDING = 5;
    private static final int SIZE = 50;
    private static final int TEXTPADDING = 2;
    private static final int ROUNDS = 10;
    private static final int ROWAMOUNT = 5;
    private static final int ROUNDING = 20;
    private static final Double OPACITY = 0.5;

    private static final int WIDTH = 280;
    private static final int HEIGHT = 150;

    private final ArrayList<Group> roundGroups;
    private final ViewController view;

    public RoundTrackView(final ViewController view) {
        this.roundGroups = new ArrayList<>();
        this.view = view;

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
            FlowPane diceDisplay = new FlowPane(Orientation.VERTICAL, 0, 1);
            diceDisplay.setMaxWidth(SIZE + 5);
            diceDisplay.setPrefHeight(SIZE + PADDING);
            Rectangle diceShower = new Rectangle(SIZE, SIZE);
            diceShower.setFill(Color.BEIGE);
            group.getChildren().add(0, diceShower);
            group.getChildren().add(1, diceDisplay);
            group.setOnMouseEntered(e -> showAllDice(group));
            group.setOnMouseExited(e -> showOneDice(group));
            roundGroups.add(group);

            Text roundNumber = new Text(Integer.toString(i + 1));
            roundNumber.setFill(Color.WHITE);
            TextFlow roundNumberFlow = new TextFlow(roundNumber);
            roundNumberFlow.setPadding(new Insets(TEXTPADDING));
            roundNumberFlow.setTextAlignment(TextAlignment.CENTER);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(group, roundNumberFlow);
            if (i >= ROWAMOUNT) {
                gridPane.add(vbox, i - ROWAMOUNT, 2);
            } else {
                gridPane.add(vbox, i, 1);
            }
        }

        Rectangle background = new Rectangle(WIDTH, HEIGHT);
        background.setFill(Color.RED);
        background.setOpacity(OPACITY);
        background.setArcHeight(ROUNDING);
        background.setArcWidth(ROUNDING);
        this.getChildren().add(0, background);
        this.getChildren().add(1, gridPane);

        Observable.addObserver(Game.class, this);

        update();
    }

    private void showAllDice(final Group diceGroup) {
        FlowPane diceDisplay = (FlowPane) diceGroup.getChildren().get(1);
        System.out.println("entered, amount of dice: " + diceDisplay.getChildren().size());
        diceGroup.getChildren().get(0).setScaleY(Math.max(diceDisplay.getChildren().size(), 1));
        diceDisplay.setTranslateY(-SIZE * (Math.max(diceDisplay.getChildren().size(), 1) - 1) / 2);
        for(Node diceNode : diceDisplay.getChildren()) {
            DieView diceView = (DieView) diceNode;
            diceView.show();
        }
    }

    private void showOneDice(final Group diceGroup) {
        FlowPane diceDisplay = (FlowPane) diceGroup.getChildren().get(1);
        diceGroup.getChildren().get(0).setScaleY(1);
        diceDisplay.setTranslateY(0);
        for(Node diceNode : diceDisplay.getChildren()) {
            DieView diceView = (DieView) diceNode;
            diceView.hide();
        }
        
        if (!diceDisplay.getChildren().isEmpty()) {
            DieView diceView = (DieView) diceDisplay.getChildren().get(0);
            diceView.show();
        }
    }

    @Override
    public void update() {
        int previousRoundtrack = -1;
        for (Map<String, String> die : view.getRoundTrack()) {
            int currentRoundTrack = Integer.parseInt(die.get("roundtrack")) - 1;
            Group currentDiceGroup = roundGroups.get((currentRoundTrack + 1) / 2);
            FlowPane diceDisplay = (FlowPane) currentDiceGroup.getChildren().get(1);
            if (previousRoundtrack != currentRoundTrack) {
                diceDisplay.getChildren().clear();
            }
            DieView newDice = new DieView(this.view, Integer.parseInt(die.get("eyes")), Color.web(die.get("color")),
                Integer.parseInt(die.get("number")), false);
            diceDisplay.getChildren().add(newDice);
            previousRoundtrack = currentRoundTrack;
            diceDisplay.setPrefHeight((SIZE + PADDING) * Math.max(diceDisplay.getChildren().size(), 1));
        }

        for (Group diceGroup : roundGroups) {
            if(diceGroup.isHover()) {
                showAllDice(diceGroup);
            } else {
                showOneDice(diceGroup);
            }
        }
    }
}
