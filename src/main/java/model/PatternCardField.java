package main.java.model;

import java.util.Map;

import javafx.scene.paint.Color;
import main.java.enums.ColorEnum;

public class PatternCardField {

    private Color color;
    private Integer value;

    public Color getColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setColor(final String color) {
        ColorEnum colorEnum = ColorEnum.fromString(color);
        this.color = Color.web(colorEnum.getHexCode());
    }

    public void setValue(final String value) {
        this.value = value != null ? Integer.parseInt(value) : null;
    }

    public static PatternCardField mapToPatternCardField(final Map<String, String> map) {
        PatternCardField field = new PatternCardField();

        if (map.get("color") != null) {
            field.setColor(map.get("color"));
        }
        if (map.get("value") != null) {
            field.setValue(map.get("value"));
        }

        return field;
    }
}
