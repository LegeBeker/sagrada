package main.java.model;

import javafx.scene.paint.Color;

public class PatternCardField {

    private Color color;
    private int value;

    public Color getColor() {
        return this.color;
    }

    public int getValue() {
        return this.value;
    }

    public void setColor(final String color) {
        if (color == null) {
            this.color = Color.web("#F8F8F8");
            return;
        }
        switch (color) {
            case "red":
                this.color = Color.web("#F44336");
                return;
            case "blue":
                this.color = Color.web("#2196F3");
                return;
            case "green":
                this.color = Color.web("#4CAF50");
                return;
            case "yellow":
                this.color = Color.web("#FFEB3B");
                return;
            case "purple":
                this.color = Color.web("#9C27B0");
                return;
            default:
                this.color = Color.web("#F8F8F8");
        }
    }

    public void setValue(final String value) {
        this.value = 0;
        if (value != null) {
            this.value = Integer.parseInt(value);
        }
    }
}
