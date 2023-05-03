package main.java.view;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class DieDropTarget extends StackPane {

    public DieDropTarget() {
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
            GridPane gridPane = (GridPane) this.getParent();
            PatternCardView patternCardView = (PatternCardView) gridPane.getParent();
            DieView dieView = (DieView) event.getGestureSource();

            Boolean placeDie = patternCardView.validateMove(dieView.getValue(), dieView.getColor(),
                    gridPane.getColumnIndex(this), gridPane.getRowIndex(this));

            if (placeDie) {
                this.getChildren().add(dieView);
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
