package main.java.view;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
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

    private int eyes;
    private Color color;
    private int number;

    public DieView(final ViewController view, final int eyes, final Color color, final int number,
            final Boolean isDraggable) {
        this.eyes = eyes;
        this.color = color;
        this.number = number;
        Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
        rectangle.setFill(Color.rgb(0, 0, 0, 0));

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

    public void hide() {
        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }
}
