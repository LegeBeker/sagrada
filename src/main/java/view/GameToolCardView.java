package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;
import main.java.model.ToolCard;

public class GameToolCardView extends ImageView {
    private final ToolCard toolCard;
    private final Image imageToolCard;
    private final int width = 150;
    private final int height = 200;
    private final double scaleIncrease = 1.75;
    private final int offset = 100;

    private boolean isSelected = false;
    private static GameToolCardView selectedToolCardView = null;

    public GameToolCardView(final ViewController view, final ToolCard toolCard) {
        this.toolCard = toolCard;
        this.imageToolCard = new Image(
                "file:resources/img/toolcards/" + toolCard.getName().toLowerCase().replace(" ", "-") + ".png");

        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setImage(imageToolCard);
        view.effects().add3DHoverEffect(this, width, height, scaleIncrease, offset);
        this.setOnMouseClicked(event -> {
            if (!isSelected) {
                if (selectedToolCardView != null) {
                    selectedToolCardView.removeSelection();
                    String deselectedMethodName = getDeselectedMethodName(selectedToolCardView.getToolCard());
                    System.out.println(deselectedMethodName + "() has been deselected.");
                }
                this.addSelection();
                selectedToolCardView = this;
                String methodName = getSelectedMethodName(toolCard);
                System.out.println(methodName + "() has been selected.");
            } else {
                removeSelection();
                selectedToolCardView = null;
                String deselectedMethodName = getDeselectedMethodName(toolCard);
                System.out.println(deselectedMethodName + "() has been deselected.");
            }
        });
    }

    private void addSelection() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 100px;");
        isSelected = true;
    }

    private void removeSelection() {
        this.setStyle("");
        isSelected = false;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    private String getSelectedMethodName(ToolCard toolCard) {
        String methodName = toolCard.getName().replaceAll("[^a-zA-Z0-9]", "");
        return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
    }

    private String getDeselectedMethodName(ToolCard toolCard) {
        String deselectedMethodName = toolCard.getName().replaceAll("[^a-zA-Z0-9]", "");
        return Character.toLowerCase(deselectedMethodName.charAt(0)) + deselectedMethodName.substring(1);
    }
}
