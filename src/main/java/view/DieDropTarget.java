package main.java.view;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import main.java.controller.ViewController;
import main.java.model.PatternCard;

public class DieDropTarget extends StackPane {

    private final ViewController view;
    private final PatternCard patternCard;

    public DieDropTarget(final ViewController view, final PatternCard patternCard) {
        this.view = view;
        this.patternCard = patternCard;

        this.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof DieView) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getGestureSource() instanceof DieView) {
                this.setStyle("-fx-border-color: #00FFBF;");
            }
        });

        this.setOnDragExited(event -> {
            this.setStyle("-fx-border-color: black;");
        });

        this.setOnDragDropped(event -> {
            DieView dieView = (DieView) event.getGestureSource();

            Boolean placeDie = this.view.getPatternCardController().doMove(this.patternCard, dieView.getValue(),
                    dieView.getColor(), dieView.getNumber(),
                    GridPane.getColumnIndex(this), GridPane.getRowIndex(this));

            if (!placeDie) {
                this.view.displayError("Deze zet is niet geldig.");
                return;
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
