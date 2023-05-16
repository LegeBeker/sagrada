package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.java.controller.ViewController;
import main.java.model.ToolCard;

public class GameToolCardView extends StackPane {
    private final ToolCard toolCard;
    private final Image imageToolCard;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    private boolean isSelected = false;
    private static GameToolCardView selectedToolCardView = null;

    public GameToolCardView(final ViewController view) {
        ImageView imageView = new ImageView();
        this.toolCard = toolCard;
        this.imageToolCard = new Image(
                "file:resources/img/toolcards/" + toolCard.getName().toLowerCase().replace(" ", "-") + ".png");
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        imageView.setImage(imageToolCard);
        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, 0);

        this.getChildren().add(imageView);

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
        this.setStyle("-fx-border-color: #00FFBF; -fx-border-width: 3px; -fx-border-radius: 10px;");
        isSelected = true;
    }

    private void removeSelection() {
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");
        isSelected = false;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    private String getSelectedMethodName(final ToolCard toolCard) {
        String methodName = toolCard.getName().replaceAll("[^a-zA-Z0-9]", "");
        return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
    }

    private String getDeselectedMethodName(final ToolCard toolCard) {
        String deselectedMethodName = toolCard.getName().replaceAll("[^a-zA-Z0-9]", "");
        return Character.toLowerCase(deselectedMethodName.charAt(0)) + deselectedMethodName.substring(1);
    }
}
