package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.enums.ToolcardEnum;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameToolCardsView extends FlowPane implements Observer {

    private ArrayList<GameToolCardView> toolCardViews = new ArrayList<GameToolCardView>();

    private static final int GAP = 10;
    private static final int PADDING = 5;
    private ViewController view;

    public GameToolCardsView(final ViewController view) {
        this.view = view;
        Observable.addObserver(Game.class, this);

        for (String toolCardName : this.view.getToolCardsNames()) {
            GameToolCardView gcv = new GameToolCardView(view, ToolcardEnum.fromString(toolCardName));
            this.getChildren().add(gcv);
            toolCardViews.add(gcv);
        }

        this.setPadding(new Insets(0, 0, PADDING, PADDING));
        this.setHgap(GAP);
    }

    @Override
    public void update() {
        for (GameToolCardView toolCard : toolCardViews) {
            toolCard.reCalcStonePositions();
        }

        for (GameToolCardView toolCard : toolCardViews) {
            if (view.getSelectedToolcardName() != null) {
                if (toolCard.getToolCardMethodName().equals(view.getSelectedToolcardName())) {
                    toolCard.addSelectionOutline();
                } else {
                    toolCard.removeSelectionOutline();
                }
            } else {
                toolCard.removeSelection();
            }
        }
    }
}
