package main.java.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatternCardFieldsFactory {

    
    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    public static ArrayList<Map<String, String>> generatePatternCardFields(){
        ArrayList<Map<String, String>> fields = new ArrayList<Map<String, String>>();

        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                Map<String, String> field = new HashMap<>();
                field.put("position_x", String.valueOf(col));
                field.put("position_y", String.valueOf(row));
                field.put("color", null);
                field.put("eyes", null);
                fields.add(field);
            }
        }

        
        return fields;
    }
}
