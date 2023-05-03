package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;
import main.java.model.ToolCard;

public class GameToolCardView extends ImageView {
    private ToolCard toolCard;
    private Image imageToolCard;

    private final int width = 150;
    private final int height = 200;

    private final double scaleIncrease = 1.75;
    private final int offset = 100;

    private boolean isSelected = false;
    private static GameToolCardView selectedToolCardView = null;

    public GameToolCardView(final ViewController view, final ToolCard toolCard) {
        this.imageToolCard = new Image(
                "file:resources/img/toolcards/" + toolCard.getName().toLowerCase().replace(" ", "-") + ".png");

        this.setFitWidth(this.width);
        this.setFitHeight(this.height);

        this.setImage(this.imageToolCard);

//        view.effects().add3DHoverEffect(this, width, height, scaleIncrease, offset);

        this.setOnMouseClicked(event -> {
            if (!isSelected) {
                if (selectedToolCardView != null) {
                    selectedToolCardView.removeSelection();
                    System.out.println(selectedToolCardView.getToolCard().getName() + " has been deselected.");
                }
                this.addSelection();
                selectedToolCardView = this;
                System.out.println(toolCard.getName() + " has been selected.");
            } else {
                removeSelection();
                selectedToolCardView = null;
                System.out.println(toolCard.getName() + " has been deselected.");
            }
        });
    }

    private void addSelection() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        isSelected = true;
    }

    private void removeSelection() {
        this.setStyle("");
        isSelected = false;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }


}
