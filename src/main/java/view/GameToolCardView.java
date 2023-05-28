package main.java.view;

import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.java.controller.ViewController;

public class GameToolCardView extends StackPane {
    private final Image imageToolCard;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;

    private static final int CARDHEIGHT = 200;

    private static final int PROHIBITEDX = 40;
    private static final int PROHIBITEDY = 40;
    private static final int CIRCLERADIUS = 15;
    private static final double OPACITY = 0.6;

    private static final int INNERCIRCLEY = 6;
    private static final int INNERCIRCLEX = 6;
    private static final int INNERCIRCLERADIUS = 5;

    private static final int MAXVALUERGB = 255;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    private String toolCardName;

    private boolean isSelected = false;
    private static GameToolCardView selectedToolCardView = null;

    public GameToolCardView(final ViewController view, final String toolCardName) {
        ImageView imageView = new ImageView();

        this.toolCardName = toolCardName;

        this.imageToolCard = new Image(
                "file:resources/img/toolcards/" + toolCardName.toLowerCase().replace(" ", "-")
                        + ".png");
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");

        imageView.setPreserveRatio(true);

        imageView.setImage(imageToolCard);

        Pane imagePane = new Pane();
        imagePane.setPrefHeight(HEIGHT);
        imagePane.setPrefWidth(WIDTH);
        imagePane.getChildren().add(imageView);

        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET,
                0);

        Pane pane = new Pane();
        pane.setPrefHeight(CARDHEIGHT);
        pane.setPrefWidth(WIDTH);

        List<Map<String, String>> players = view.getPlayers();

        for (Map<String, String> ft : view.getFavorTokensForToolCard(toolCardName)) {

            int randX = 1 + (int) (Math.random() * WIDTH);
            int randY = 1 + (int) (Math.random() * CARDHEIGHT);

            if (randX <= PROHIBITEDX) {
                randX += PROHIBITEDX;
            } else if (randX > WIDTH - CIRCLERADIUS) {
                randX -= CIRCLERADIUS;
            }
            if (randY <= PROHIBITEDY) {
                randY += PROHIBITEDY;
            } else if (randY > WIDTH - CIRCLERADIUS) {
                randY -= CIRCLERADIUS;
            }
            for (Map<String, String> p : players) {
                if (p.get("idPlayer").equals(ft.get("idplayer"))) {
                    Circle c = new Circle(randX, randY, CIRCLERADIUS);
                    Color playerColor = Color.valueOf(p.get("color"));
                    if (playerColor != null) {
                        c.setFill(Color.rgb((int) (playerColor.getRed() * MAXVALUERGB),
                                (int) (playerColor.getGreen() * MAXVALUERGB),
                                (int) (playerColor.getBlue() * MAXVALUERGB), OPACITY));
                        c.setStroke(Color.rgb((int) (playerColor.getRed() * MAXVALUERGB),
                                (int) (playerColor.getGreen() * MAXVALUERGB),
                                (int) (playerColor.getBlue()) * MAXVALUERGB).deriveColor(0, 1, 0.2, 1));
                        c.setStrokeWidth(2);

                        Circle innerCircle = new Circle(randX - INNERCIRCLEX, randY - INNERCIRCLEY, INNERCIRCLERADIUS);
                        innerCircle.setFill(Color.rgb(MAXVALUERGB, MAXVALUERGB, MAXVALUERGB, OPACITY));
                        pane.getChildren().addAll(c, innerCircle);
                    }
                }
            }
        }
        this.getChildren().addAll(imagePane, pane);

        this.setOnMouseClicked(event -> {
            if (!isSelected) {
                if (selectedToolCardView != null) {
                    selectedToolCardView.removeSelection();
                    String deselectedMethodName = getDeselectedMethodName(selectedToolCardView.getToolCardName());
                    System.out.println(deselectedMethodName + "() has been deselected.");
                }
                this.addSelection();
                selectedToolCardView = this;
                String methodName = getSelectedMethodName(toolCardName);
                System.out.println(methodName + "() has been selected.");
            } else {
                removeSelection();
                selectedToolCardView = null;
                String deselectedMethodName = getDeselectedMethodName(toolCardName);
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

    public String getToolCardName() {
        return this.toolCardName;
    }

    private String getSelectedMethodName(final String toolCardName) {
        String methodName = toolCardName.replaceAll("[^a-zA-Z0-9]", "");
        return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
    }

    private String getDeselectedMethodName(final String toolCardName) {
        String deselectedMethodName = toolCardName.replaceAll("[^a-zA-Z0-9]", "");
        return Character.toLowerCase(deselectedMethodName.charAt(0)) + deselectedMethodName.substring(1);
    }
}
