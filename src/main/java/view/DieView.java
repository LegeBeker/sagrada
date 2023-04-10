package main.java.view;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DieView extends Group {

    private static final int DOTRADIUS = 3;
    private static final int RECTANGLE = 50;

    private static final int POSITIONLOW = 15;
    private static final int POSITIONMEDIUM = 25;
    private static final int POSITIONHIGH = 35;

    private int value;

    public DieView(final int value, final Color color) {
        this.value = value;

        Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
        rectangle.setFill(color);

        this.getChildren().addAll(rectangle, this.addDotsToDie());
    }

    private Group addDotsToDie() {
        ArrayList<Circle> dots = new ArrayList<Circle>();

        switch (Integer.toString(this.value)) {
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
}
