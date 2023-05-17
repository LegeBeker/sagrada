package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.java.controller.ViewController;
import main.java.model.FavorToken;
import main.java.model.ToolCard;

public class GameToolCardView extends StackPane {
    private final ToolCard toolCard;
    private final Image imageToolCard;

    private static final int WIDTH = 150;
    private static final int CARDHEIGHT = 200;

    private static final int prohibitedX = 40;
    private static final int prohibitedY = 40;
    private static final int circleRadius = 15;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    private boolean isSelected = false;
    private static GameToolCardView selectedToolCardView = null;

    public GameToolCardView(final ViewController view, final ToolCard toolCard) {
        ImageView imageView = new ImageView();
        this.toolCard = toolCard;
        this.imageToolCard = new Image(
                "file:resources/img/toolcards/" + toolCard.getName().toLowerCase().replace(" ", "-") + ".png");
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(CARDHEIGHT);
        imageView.setImage(imageToolCard);
        view.effects().add3DHoverEffect(this, WIDTH, CARDHEIGHT, SCALEINCREASE, OFFSET, 0);

        Pane pane = new Pane();
        pane.setPrefHeight(CARDHEIGHT);
        pane.setPrefWidth(WIDTH);

        //-- Start loop for each favortoken
        for(FavorToken ft : view.getFavorTokenController().getFavorTokensForToolCard(toolCard.getIdToolCard(), view.getGameController().getGame().getId())){
            int randX = 1 + (int)(Math.random()*WIDTH);
            int randY = 1 + (int)(Math.random()*CARDHEIGHT);

            if(randX <= prohibitedX){
                randX += prohibitedX;
            }
            else if(randX > WIDTH - circleRadius){
                randX -= circleRadius;
            }

            if(randY <= prohibitedY){
                randY += prohibitedY;
            }
            else if(randY > WIDTH - circleRadius){
                randY -= circleRadius;
            }

            System.out.println("Random X: " + randX);
            System.out.println("Random Y: " + randY);

            System.out.println();
            System.out.println("------------------");
            System.out.println();


            Circle c = new Circle(randX,randY,circleRadius);
            // ft.getIdPlayer();

            c.setFill(Color.rgb(255, 0, 0, 0.6));
            c.setStroke(Color.rgb(255, 0, 0).deriveColor(0, 1, 0.2, 1));
            c.setStrokeWidth(2);
            pane.getChildren().add(c);
        }

        this.getChildren().addAll(imageView, pane);

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
