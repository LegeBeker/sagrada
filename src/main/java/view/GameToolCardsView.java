package main.java.view;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;

public class GameToolCardsView extends FlowPane {

    private ArrayList<GameToolCardView> toolCardViews = new ArrayList<GameToolCardView>();

    private static final int GAP = 10;
    private static final int PADDING = 5;

    public GameToolCardsView(final ViewController view) {
        for (String toolCardName : view.getToolCardsNames()) {
            GameToolCardView gcv = new GameToolCardView(view, toolCardName);
            this.getChildren().add(gcv);
            toolCardViews.add(gcv);
        }

        this.setPadding(new Insets(0, 0, PADDING, PADDING));
        this.setHgap(GAP);
    }

    public void dieSelectedForToolcard(final Map<String, String> selectedDieMap){
        for(GameToolCardView gcv : this.toolCardViews){
            if(gcv.getSelectionStatus()){
                gcv.dieSelectedForToolcard(selectedDieMap);
            }
        }
    }
}
