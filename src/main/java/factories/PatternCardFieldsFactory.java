package main.java.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PatternCardFieldsFactory {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private static String[][] fieldColor = new String[ROWS][COLUMNS];
    private static String[][] fieldEyes = new String[ROWS][COLUMNS];

    private static ArrayList<String> options = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6","Red", "Green", "Blue", "Yellow", "Purple"));

    public static ArrayList<Map<String, String>> generatePatternCardFields() {
        ArrayList<Map<String, String>> fields = new ArrayList<Map<String, String>>();

        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                Map<String, String> field = new HashMap<>();
                field.put("position_x", String.valueOf(col));
                field.put("position_y", String.valueOf(row));
                String color = setFieldColor(row, col);
                field.put("color", color);
                fieldColor[row - 1][col - 1] = color;
                String eyes = setFieldEyes(row, col);
                field.put("value", eyes);
                fieldEyes[row - 1][col - 1] = eyes;
                fields.add(field);
            }
        }

        return fields;
    }

    private static String setFieldColor(final int row, final int col) {
        ArrayList<int[]> neighbors = getNeighbors(row, col);
        ArrayList<String> possibilities = colors;
        Random random = new Random();
        int randomNumber = random.nextInt(101);

        String[] valueToRemove = {null};

        neighbors.forEach((cell) -> {
            if (fieldColor[cell[0] - 1][cell[1] - 1] != null) {
                possibilities.forEach((value) -> {
                    if (value.equals(fieldColor[cell[0] - 1][cell[1] - 1])) {
                        valueToRemove[0] = value;
                    }
                });
            }
        });    

        possibilities.remove(valueToRemove[0]);

        if (!possibilities.isEmpty() && randomNumber > 50) {
            Random randomColor = new Random();
            int randomIndex = randomColor.nextInt(possibilities.size());
            return possibilities.get(randomIndex);
        }

        return null;
    }

    private static String setFieldEyes(final int row, final int col) {
        ArrayList<int[]> neighbors = getNeighbors(row, col);
        ArrayList<String> possibilities = numbers;
        Random random = new Random();
        int randomNumber = random.nextInt(101);
        String[] valueToRemove = {null};

        neighbors.forEach((cell) -> {
            if (fieldEyes[cell[0] - 1][cell[1] - 1] != null) {
                possibilities.forEach((value) -> {
                    if (value.equals(fieldEyes[cell[0] - 1][cell[1] - 1])) {
                        valueToRemove[0] = value;
                    }
                });
            }
        });    

        possibilities.remove(valueToRemove[0]);

        if (!possibilities.isEmpty() && randomNumber > 50) {
            Random randomEyes = new Random();
            int randomIndex = randomEyes.nextInt(possibilities.size());
            return possibilities.get(randomIndex);
        }

        return null;
    }

    public static ArrayList<int[]> getNeighbors(final int row, final int col) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[][] offsets;

        offsets = new int[][] {{-1, 0 }, { 0, -1 }};

        for (int[] offset : offsets) {
            int neighborRow = row + offset[0];
            int neighborCol = col + offset[1];

            if (neighborRow >= 1 && neighborRow <= ROWS && neighborCol >= 1 && neighborCol <= COLUMNS) {
                neighbors.add(new int[] { neighborRow, neighborCol });
            }
        }

        return neighbors;
    }

}
