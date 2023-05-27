package main.java.view;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.java.controller.ViewController;

public class GameToolCardView extends StackPane {
    private final Image imageToolCard;
    private GameCenterView gameCenterView;
    private ViewController view;

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

    public GameToolCardView(final ViewController view, final String toolCardName, final GameCenterView gameCenterView) {
        ImageView imageView = new ImageView();
        this.gameCenterView = gameCenterView;
        this.view = view;

        this.toolCardName = toolCardName;

        this.imageToolCard = new Image(
                "file:resources/img/toolcards/" + toolCardName.toLowerCase().replace(" ", "-")
                        + ".png");
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        imageView.setImage(imageToolCard);
        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, 0);

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
        this.getChildren().addAll(imageView, pane);

        this.setOnMouseClicked(event -> {
            if (!isSelected) {
                if (selectedToolCardView != null) {
                    selectedToolCardView.removeSelection();
                    String deselectedMethodName = getDeselectedMethodName(selectedToolCardView.getToolCardName());
                }

                String methodName = getSelectedMethodName(toolCardName);
                if (!methodName.equals("")) {
                    if(askConfirmationUsageCard(this.getToolCardName())){
                        this.addSelection();
                        selectedToolCardView = this;
                    }
                }

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
        gameCenterView.updateSelectionStatus(isSelected);
    }

    private void removeSelection() {
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");
        isSelected = false;
        gameCenterView.updateSelectionStatus(isSelected);
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

    private Boolean askConfirmationUsageCard(final String toolCardname) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Gebruik gereedschapskaart");
        alert.setHeaderText("Bevestiging gebruikt " + toolCardname);
        alert.setContentText(
                "Je staat op het punt om de gereedschapskaart " + toolCardname + " te gebruiken. Weet je het zeker?");

        ButtonType acceptButton = new ButtonType("Accepteren");
        ButtonType refuseButton = new ButtonType("Weigeren");
        ButtonType closeButton = new ButtonType("Sluiten", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(acceptButton, refuseButton, closeButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) {
            return false;
        }

        if (result.get() == acceptButton) {
            return true;
        } else if (result.get() == refuseButton) {
            return false;
        }
        else{
            return false; //-- failsafe for if we forget to handle a new button
        }
    }

    public Boolean getSelectionStatus(){
        return this.isSelected;
    }

    public void dieSelectedForToolcard (Map<String, String> selectedDieMap){
        String methodName = getSelectedMethodName(toolCardName);
        switch (methodName) {
            case "grozingPliers": //-- Keep in mind that all values are hardcoded as of now
                System.out.println("Switch case for grozingPliers triggert");
                view.grozingPliers(2);
                break;
            case "eglomiseBrush":
                System.out.println("Switch case for eglomiseBrush triggert");
                break;
            case "copperFoilBurnisher":
                System.out.println("Switch case for copperFoilBurnisher triggert");
                break;
            case "lathekin":
                System.out.println("Switch case for lathekin triggert");
                break;
            case "lensCutter":
                System.out.println("Switch case for lensCutter triggert");
                view.lensCutter(1, 2);
                break;
            case "fluxBrush":
                System.out.println("Switch case for fluxBrush triggert");
                //-- dieNumber & dieColor
                view.fluxBrush(Integer.parseInt(selectedDieMap.get("dieNumber")), selectedDieMap.get("dieColor"));
                break;
            case "glazingHammer":
                System.out.println("Switch case for glazingHammer triggert");
                view.glazingHammer(1, 2, 3);
                break;
            case "runningPliers":
                System.out.println("Switch case for runningPliers triggert");
                break;
            case "cork-backedStraightedge":
                System.out.println("Switch case for backedStraightedge triggert");
                break;
            case "grindingStone":
                System.out.println("Switch case for grindingStone triggert");
                view.grindingStone();
                break;
            case "fluxRemover":
                System.out.println("Switch case for fluxRemover triggert");
                view.fluxRemover(1, 2);
                break;
            case "tapWheel":
                System.out.println("Switch case for tapWheel triggert");
                break;
            default:
                break;
            }
    }
}
