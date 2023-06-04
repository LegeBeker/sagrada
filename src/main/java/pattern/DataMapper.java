package main.java.pattern;

import java.lang.reflect.Method;
import java.util.ArrayList;

import main.java.model.PatternCardField;


/*
 * DataMapper is een class die een lijst van Players of Games omzet naar een lijst van DataMaps.
 * 
 * Dit is een voorbeeld van hoe je de DataMapper kan gebruiken in dit voorbeeld maken we gebruik van de Message class:
 * In de MessageController doen we het volgende:
 * public ArrayList<DataMap> getChatMessages(final Game game) {
 *    return DataMapper.createMultiple(Message.getAll(game.getId()), "message", "username", "time");
 * }
 * 
 * De eerste parameter is een ArrayList van Messages, de tweede parameter zijn de velden die je wilt ophalen uit de Message class. 
 * De velden die je wilt ophalen moeten een getter hebben in de Message class. Volgens de JavaBeans conventie (getUsername()).
 * 
 * In de MessageView doen we het volgende:
 * 
 * for (DataMap message : view.getChatMessages()) {
 *  addMessage(message.getString("message"), message.getString("username"),       
 *     message.getString("time"));
 * }
 * 
 * Ook is er ondersteuning voor het ophalen van child fields zoals voor de turnPlayer:
 * public ArrayList<DataMap> getGames() {
 *      return DataMapper.createMultiple(Game.getAll(),"id", "turnPlayer{username,score}", "currentRound", "creationDate");
 * }
 * 
 * In dit geval wordt er eerst een getter gemaakt voor getTurnPlayer daarna worden de getters voor username en score uitgevoerd in Player class.
 * 
 */

public final class DataMapper {
    private DataMapper() {
    }

    public static DataMap create(final Object object, final String... fields) {
        DataMap map = new DataMap();
        for (String field : fields) {
            System.out.println(field);
            String[] fieldParts = splitField(field);
            String getterMethodName = createGetterName(fieldParts[0]);

            try {
                Method getterMethod = object.getClass().getMethod(getterMethodName);
                Object value = getterMethod.invoke(object);

                if (isPrimitiveOrString(value) || fieldParts.length == 1) {
                    map.put(fieldParts[0], value);
                } else if (value instanceof ArrayList) {
                    ArrayList<?> objects = (ArrayList<?>) value;
                    ArrayList<DataMap> maps = new ArrayList<>();
                    for (Object obj : objects) {
                        System.out.println(obj);
                        maps.add(create(obj, fieldParts[1].split(",")));
                    }
                    map.put(fieldParts[0], maps);
                } else if (value instanceof PatternCardField) {
                    System.out.println("PatternCardField");
                } else {
                    map.put(fieldParts[0], create(value, fieldParts[1].split(",")));
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public static ArrayList<DataMap> createMultiple(final ArrayList<?> objects,
            final String... fields) {
        ArrayList<DataMap> maps = new ArrayList<>();
        for (Object object : objects) {
            maps.add(create(object, fields));
        }

        return maps;
    }

    private static boolean isPrimitiveOrString(final Object value) {
        return value == null || value.getClass().isPrimitive() || value instanceof String;
    }

    private static String createGetterName(final String field) {
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    private static String[] splitField(final String field) {
        if (field.contains("{") && field.contains("}")) {
            String[] parts = field.split("\\{");
            parts[1] = parts[1].substring(0, parts[1].length() - 1);
            return parts;
        } else {
            return new String[] {field};
        }
    }
}
