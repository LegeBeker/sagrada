package main.java.view;

import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DieDropTarget extends StackPane {
    public DieDropTarget() {
        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.WHITE);

        this.getChildren().addAll(rectangle);

        this.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof DieView) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getGestureSource() instanceof DieView) {
                this.setStyle("-fx-border-color: blue;");
            }
        });

        this.setOnDragExited(event -> {
            this.setStyle("");
        });

        this.setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof DieView) {
                DieView dieView = (DieView) event.getGestureSource();
                this.getChildren().add(dieView);
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }
}