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
import main.java.enums.ToolcardEnum;

public class GameToolCardView extends StackPane {
    private final Image imageToolCard;
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

    private int amountFavorTokensDisplayed = 0;
    private List<Map<String, String>> players;
    private Pane stoneDisplayPane;

    private ToolcardEnum toolCard;

    private boolean isSelected = false;
    private static GameToolCardView selectedToolCardView = null;

    public GameToolCardView(final ViewController view, final ToolcardEnum toolCard) {
        ImageView imageView = new ImageView();
        this.view = view;

        this.toolCard = toolCard;

        this.imageToolCard = new Image(
                getClass().getResource("/img/toolcards/" + toolCard.getImageName().toLowerCase().replace(" ", "-")
                        + ".png").toExternalForm());
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        imageView.setImage(imageToolCard);
        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, 0);

        stoneDisplayPane = new Pane();
        stoneDisplayPane.setPrefHeight(CARDHEIGHT);
        stoneDisplayPane.setPrefWidth(WIDTH);

        players = view.getPlayers();

        for (Map<String, String> ft : view.getFavorTokensForToolCard(toolCard.getName())) {
            calculateNewStonePosition(ft);
        }

        this.getChildren().addAll(imageView, stoneDisplayPane);

        this.setOnMouseClicked(event -> {
            if (view.isTurnPlayer()) {
                if (!isSelected) {
                    if (selectedToolCardView != null) {
                        selectedToolCardView.removeSelection();
                    }

                    String methodName = getSelectedMethodName(toolCard.getName());
                    if (!methodName.equals("")) {

                        System.out.println("Huidige toolkaart: " + methodName);

                        if (methodName.equals("runningPliers") && !view.getGameClockwise()) {
                            view.displayError("Je kan deze gereedschapskaart alleen activeren in je eerste beurt");
                            return;
                        }

                        if (askConfirmationUsageCard(toolCard.getDutchName())) {
                            this.addSelection();
                            selectedToolCardView = this;

                            // -- These methods can be called without any input variables
                            switch (methodName) {
                                case "glazingHammer":
                                    if (!view.glazingHammer()) { // Returns a boolean for the extra check
                                        this.view.displayError(
                                                "Je kan alleen het aanbod opnieuw rollen bij de 2e beurt in de ronde.");
                                    }
                                    this.removeSelection();
                                    this.isSelected = false;
                                    break;
                                case "runningPliers":
                                    break;
                                case "eglomiseBrush":
                                    view.eglomiseBrush();
                                    break;
                                case "copperFoilBurnisher":
                                    view.copperFoilBurnisher();
                                    break;
                                case "lathekin":
                                    break;
                            }

                        }
                    }

                } else {
                    removeSelection();
                    selectedToolCardView = null;
                    String deselectedMethodName = getDeselectedMethodName(toolCard.getName());
                    System.out.println(deselectedMethodName + "() has been deselected.");
                }
            }

        });
    }

    public void addSelectionOutline() {
        this.setStyle("-fx-border-color: #00FFBF; -fx-border-width: 3px; -fx-border-radius: 10px;");
    }

    public void removeSelectionOutline() {
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");
    }

    public void addSelection() {
        this.setStyle("-fx-border-color: #00FFBF; -fx-border-width: 3px; -fx-border-radius: 10px;");
        isSelected = true;
        view.setToolCardSelection(this.getSelectedMethodName(toolCard.getName()));
    }

    public void removeSelection() {
        this.setStyle("-fx-border-color: transparent; -fx-border-width: 3px;");
        isSelected = false;
        view.setToolCardSelection(null);
    }

    public String getToolCardName() {
        return toolCard.getName();
    }

    public String getSelectedMethodName(final String toolCardName) {
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
                "Je staat op het punt om de gereedschapskaart " + toolCardname
                        + " te gebruiken, je koopt dan direct de gereedschapskaart. Weet je het zeker?");

        ButtonType acceptButton = new ButtonType("Accepteren");
        ButtonType refuseButton = new ButtonType("Weigeren");
        ButtonType closeButton = new ButtonType("Sluiten", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(acceptButton, refuseButton, closeButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == acceptButton) {
            if (!view.buyToolCard(toolCardname)) {
                view.displayError("Je hebt niet genoeg betaalstenen om deze gereedschapskaart te kopen.");
                return false;
            }
        }

        return result.get() == acceptButton;
    }

    public Boolean getSelectionStatus() {
        return this.isSelected;
    }

    public void reCalcStonePositions() {

        List<Map<String, String>> favorTokenList = view.getFavorTokensForToolCard(getToolCardName());
        if (amountFavorTokensDisplayed != favorTokenList.size()) {
            int diff = favorTokenList.size() - amountFavorTokensDisplayed;
            for (int i = 0; i < diff; i++) {
                int index = favorTokenList.size() - (i + 1);
                calculateNewStonePosition(favorTokenList.get(index));
                amountFavorTokensDisplayed++;
            }
        }
    }

    private void calculateNewStonePosition(final Map<String, String> ft) {
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
                    stoneDisplayPane.getChildren().addAll(c, innerCircle);
                    this.amountFavorTokensDisplayed++;
                }
            }
        }

    }
}
