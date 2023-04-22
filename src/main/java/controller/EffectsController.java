package main.java.controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.PerspectiveTransform;
import javafx.util.Duration;

public class EffectsController {

    private static final int TRANSITION_DURATION = 300;
    private static final double PERSPECTIVE_RATIO = 0.2;

    public void add3DHoverEffect(final Node node, final int width, final int height, final double scaleIncrease,
            final int pixelOffset) {
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();

        resetPerspectiveTransform(perspectiveTransform, width, height);

        node.setEffect(perspectiveTransform);

        node.setOnMouseMoved(event -> {
            Point2D mousePosition = new Point2D(event.getX(), event.getY());

            double currentMousePosX = mousePosition.getX();
            double currentMousePosY = mousePosition.getY();

            double around2 = (currentMousePosY * 100 / height * PERSPECTIVE_RATIO - 10);

            perspectiveTransform.setUrx(width + around2);
            perspectiveTransform.setUlx(around2 * -1);

            perspectiveTransform.setLrx(width - around2);
            perspectiveTransform.setLlx(around2);

            double around1 = 1 * (currentMousePosX * 100 / width * PERSPECTIVE_RATIO - 10);

            perspectiveTransform.setUly(around1 * -1);
            perspectiveTransform.setLly(height - (-1 * around1));

            perspectiveTransform.setUry(around1);
            perspectiveTransform.setLry(height - around1);
        });

        node.setOnMouseEntered(event -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(TRANSITION_DURATION), node);
            tt.setToY(-pixelOffset);

            ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_DURATION), node);
            st.setToX(scaleIncrease);
            st.setToY(scaleIncrease);

            st.play();
            tt.play();
        });

        node.setOnMouseExited(event -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(TRANSITION_DURATION), node);
            tt.setToY(0);

            ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_DURATION), node);
            st.setToX(1);
            st.setToY(1);

            st.play();
            tt.play();

            resetPerspectiveTransform(perspectiveTransform, width, height);
        });
    }

    private void resetPerspectiveTransform(final PerspectiveTransform perspectiveTransform, final int width,
            final int height) {
        perspectiveTransform.setUlx(0);
        perspectiveTransform.setUly(0);
        perspectiveTransform.setUrx(width);
        perspectiveTransform.setUry(0);
        perspectiveTransform.setLrx(width);
        perspectiveTransform.setLry(height);
        perspectiveTransform.setLlx(0);
        perspectiveTransform.setLly(height);
    }
}
