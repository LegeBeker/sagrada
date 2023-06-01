package main.java.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.enums.ColorEnum;
import main.java.model.PatternCard;
import main.java.model.PatternCardField;

public final class PatternCardFieldsFactory {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private static final int MAX_DIFFICULTY = 6;

    private static ArrayList<String> options = new ArrayList<String>(
            Arrays.asList("1", "2", "3", "4", "5", "6", "Red", "Green", "Blue", "Yellow", "Purple"));

    private PatternCardFieldsFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static PatternCard generatePatternCard(final int id) {
        PatternCardField[][] fields = new PatternCardField[ROWS][COLUMNS];

        int difficulty = (int) (Math.random() * MAX_DIFFICULTY + 1);

        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                fields[row - 1][col - 1] = PatternCardField
                        .mapToPatternCardField(setField(row, col, fields, difficulty));
            }
        }

        return PatternCard.mapCustomPatternCard(id, difficulty, fields);
    }

    private static Map<String, String> setField(final int row, final int col,
            final PatternCardField[][] fields, final int difficulty) {
        Map<String, String> field = new HashMap<String, String>();
        ArrayList<int[]> neighbors = PatternCard.getNeighbors(row, col, false);

        field.put("position_x", Integer.toString(col));
        field.put("position_y", Integer.toString(row));

        String option = null;
        while (option == null) {
            option = options.get((int) (Math.random() * options.size()));
            for (int[] neighbor : neighbors) {
                PatternCardField neighborField = fields[neighbor[0] - 1][neighbor[1] - 1];
                if (neighborField != null
                        && ((option.matches("[1-6]") && neighborField.getValue() != null
                                && neighborField.getValue().equals(Integer.parseInt(option)))
                                || ((option.matches("(Red|Green|Blue|Yellow|Purple)")
                                        && neighborField.getColor() != null
                                        && neighborField.getColor()
                                                .equals(Color.web(ColorEnum.fromString(option).getHexCode())))))) {
                    option = null;
                    break;
                }
            }

            if (Math.random() <= ((-0.06 * difficulty) + 0.66)) {
                return field;
            }

            if (option != null && option.matches("[1-6]")) {
                field.put("value", option);
            } else if (option != null && option.matches("(Red|Green|Blue|Yellow|Purple)")) {
                field.put("color", option);
            }
        }

        return field;
    }
}
