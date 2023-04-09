package main.java.model;

import javafx.scene.paint.Color;

public class PatternCardField {

    private Color color;
    private int value;

    public PatternCardField(final Color color, final int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return this.color;
    }

    public int getValue() {
        return this.value;
    }
}
