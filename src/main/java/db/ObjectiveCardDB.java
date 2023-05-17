package main.java.db;

import java.util.List;
import java.util.Map;

public final class ObjectiveCardDB {

    private ObjectiveCardDB() {

    };

    public static List<Map<String, String>> getObjectiveCards(final int idGame) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM public_objectivecard WHERE idpublic_objectivecard IN ";
        sql += "(SELECT idpublic_objectivecard FROM gameobjectivecard_public WHERE idgame = ?) ;";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static Map<String, String> getObjectiveCard(final int idObjectiveCard) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM public_objectivecard WHERE idpublic_objectivecard = ?;";
        String[] params = {Integer.toString(idObjectiveCard)};

        return db.exec(sql, params).get(0);
    }
}
