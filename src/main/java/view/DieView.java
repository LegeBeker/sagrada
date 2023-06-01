package main.java.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.java.controller.ViewController;

public class DieView extends Group {

    private static final int DOTRADIUS = 3;
    private static final int RECTANGLE = 50;

    private static final int POSITIONLOW = 15;
    private static final int POSITIONMEDIUM = 25;
    private static final int POSITIONHIGH = 35;

    private static final int RADIUS = 10;
    private static final double SCALE = 0.8;

    private GameOfferView gameOfferView;
    private ViewController view;

    private int eyes;
    private Color color;
    private int number;

    public DieView(final ViewController view, final int eyes, final Color color, final int number,
            final Boolean isDraggable) {
        this.eyes = eyes;
        this.color = color;
        this.number = number;
        this.view = view;
        Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
        rectangle.setFill(Color.rgb(0, 0, 0, 0));
        this.setOnMouseClicked(e -> checkSelectionModeToolCard());
        Rectangle die = new Rectangle(RECTANGLE, RECTANGLE);
        die.setFill(color);
        die.setStroke(Color.BLACK);

        die.setArcHeight(RADIUS);
        die.setArcWidth(RADIUS);

        die.setScaleX(SCALE);
        die.setScaleY(SCALE);
        die.setTranslateX((rectangle.getWidth() - die.getWidth()) / 2);

        if (isDraggable) {
            this.setOnDragDetected(event -> {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                this.gameOfferView = (GameOfferView) this.getParent();
                if (view.getHelpFunction()) {
                    gameOfferView.showPossibleMoves(this.eyes, this.color);
                }
                SnapshotParameters sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);
                Image image = this.snapshot(sp, null);
                content.putImage(image);
                db.setContent(content);
                event.consume();
            });

            this.setOnDragDone(event -> {
                if (view.getHelpFunction()) {
                    gameOfferView.cleanTargets();
                }
            });
        }

        die.setTranslateX((rectangle.getWidth() - die.getWidth()) / 2);

        this.getChildren().addAll(rectangle, die, this.addDotsToDie());
    }

    public DieView(final int eyes) {
        this.eyes = eyes;

        Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
        rectangle.setFill(Color.WHITE);

        this.getChildren().addAll(rectangle, this.addDotsToDie());
    }

    private Group addDotsToDie() {
        ArrayList<Circle> dots = new ArrayList<Circle>();

        switch (Integer.toString(this.eyes)) {
            case "1":
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                break;
            case "2":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "3":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "4":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "5":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "6":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONLOW, POSITIONMEDIUM));
                dots.add(createDot(POSITIONHIGH, POSITIONMEDIUM));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            default:
                break;
        }

        Group dotsGroup = new Group();
        dotsGroup.getChildren().addAll(dots);

        return dotsGroup;
    }

    private Circle createDot(final double x, final double y) {
        return new Circle(x, y, DOTRADIUS, Color.BLACK);
    }

    public int getEyes() {
        return this.eyes;
    }

    public Color getColor() {
        return this.color;
    }

    public int getNumber() {
        return this.number;
    }

    private void checkSelectionModeToolCard() {
        // -- @TimBogersGitHub, Check if it his can result in any issues since this will
        // be used in roudtrack and/or in the board itself. Check source of dieView??

        System.out.println("Parent class: " + this.getParent().getTypeSelector());
        String methodName = view.getSelectedToolcardName();
        if (methodName != null) {
            System.out.println("Kaart " + view.getSelectedToolcardName() + " is geselecteerd");

            if (!this.getParent().getClass().getSimpleName().equals("GameOfferView")) {
                view.displayError("Kies een dobbelsteen uit het aanbod.");
            } else {
                Map<String, String> selectedDieMap = new HashMap<>();
                ;
                selectedDieMap.put("eyes", Integer.toString(this.eyes));
                selectedDieMap.put("dieNumber", Integer.toString(this.number));
                selectedDieMap.put("dieColor", this.color.toString());

                // -- trigger toolcard logic
                switch (methodName) {
                    case "grozingPliers":
                        String actionChoice = askGrozingPliersAction(Integer.parseInt(selectedDieMap.get("eyes")));
                        if (!actionChoice.equals("?")) {
                            view.grozingPliers(Integer.parseInt(selectedDieMap.get("dieNumber")),
                                    selectedDieMap.get("dieColor"), actionChoice);
                        }
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
                        view.lensCutter(1, 2); // -- This is going to be a tricky one since we swap values from offer
                                               // and roundtrack
                        break;
                    case "fluxBrush":
                        view.fluxBrush(Integer.parseInt(selectedDieMap.get("dieNumber")),
                                selectedDieMap.get("dieColor"));
                        break;
                    case "cork-backedStraightedge":
                        System.out.println("Switch case for backedStraightedge triggert");
                        break;
                    case "grindingStone":
                        System.out.println("Switch case for grindingStone triggert");
                        view.grindingStone(Integer.parseInt(selectedDieMap.get("dieNumber")),
                                selectedDieMap.get("dieColor"));
                        break;
                    case "fluxRemover":
                        System.out.println("Switch case for fluxRemover triggert");
                        view.fluxRemover(Integer.parseInt(selectedDieMap.get("dieNumber")),
                                selectedDieMap.get("dieColor"));
                        break;
                    case "tapWheel":
                        System.out.println("Switch case for tapWheel triggert");
                        break;
                    default:
                        break;
                }
                view.setToolCardSelection(null);
            }
        } else {
            System.out.println("Kaart niet geselecteerd");
        }
    }

    private String askGrozingPliersAction(final int currentDieValue) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Gebruik gereedschapskaart");
        alert.setHeaderText("Vermeld je keuze m.b.t. de actie die deze doelkaart moet uitvoeren.");
        alert.setContentText(
                "Let op: Als de dobbelsteen de waarde 6 heeft, en je verhoogt de steen, wordt het geen 1. "
                + "De huidge waarde van de geselecteerde dobbelsteen is " + currentDieValue + ". ");

        ButtonType incrementButton = new ButtonType("Toevoegen");
        ButtonType decrementButton = new ButtonType("Aftrekken");
        ButtonType closeButton = new ButtonType("Sluiten", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(incrementButton, decrementButton, closeButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) {
            return "?";
        }

        if (result.get() == incrementButton) {
            return "increment";
        } else if (result.get() == decrementButton) {
            return "decrement";
        } else {
            return "?"; // -- failsafe for if we forget to handle a new button
        }

    }
}
