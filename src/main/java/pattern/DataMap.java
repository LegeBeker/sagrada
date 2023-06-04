package main.java.pattern;

import java.util.HashMap;

import javafx.scene.paint.Color;

/*  
 * Dit is de DataMap class het is een HashMap met extra functionaliteit.
 * Zo kan je bijvoorbeeld een String, Integer, Color of Boolean ophalen uit de DataMap.
 * Deze class was bedoeld om te zorgen dat er geen we op een makkelijk manier konder voorkomen dat we models naar de view stuurden.
 * In 1 datamap kan je 1 instantie van model zetten. Bijv een Player of een Game. 
 */
public class DataMap extends HashMap<String, Object> {
    public String getString(final String key) {
        Object value = getNestedProperty(key);
        if (value instanceof String) {
            return (String) value;
        } else {
            throw new IllegalStateException("Value is not a string for key: " + key);
        }
    }

    public int getInt(final String key) {
        Object value = getNestedProperty(key);
        if (value instanceof Integer) {
            return (int) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Value is not a valid integer for key: " + key);
            }
        } else {
            throw new IllegalStateException("Value is not an integer or string for key: " + key);
        }
    }

    public Color getColor(final String key) {
        Object value = getNestedProperty(key);
        if (value instanceof Color) {
            return (Color) value;
        } else {
            throw new IllegalStateException("Value is not a color for key: " + key);
        }
    }

    public Boolean getBoolean(final String key) {
        Object value = getNestedProperty(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            throw new IllegalStateException("Value is not a Boolean for key: " + key);
        }
    }

    private Object getNestedProperty(final String key) {
        String[] propertyParts = key.split("\\.");
        Object value = this;
        for (String part : propertyParts) {
            if (value instanceof DataMap) {
                value = ((DataMap) value).get(part);
            } else {
                throw new IllegalStateException("Nested property does not exist for key: " + key);
            }
        }
        return value;
    }

    public <T> T getValue(final String key, final Class<T> valueType) {
        Object value = get(key);
        if (valueType.isInstance(value)) {
            return valueType.cast(value);
        } else {
            throw new IllegalStateException("Value is not of the specified type for key: " + key);
        }
    }
}
