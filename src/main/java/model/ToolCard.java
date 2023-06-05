package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import main.java.db.ToolCardDB;

public class ToolCard {
    private int idToolCard;
    private String name;
    private String description;

    public int getIdToolCard() {
        return idToolCard;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<ToolCard> getToolCards(final int idGame) {
        ArrayList<ToolCard> toolCards = new ArrayList<>();

        for (Map<String, String> row : ToolCardDB.getToolCards(idGame)) {
            ToolCard toolCard = new ToolCard();

            toolCard.idToolCard = Integer.parseInt(row.get("idtoolcard"));
            toolCard.name = row.get("name");
            toolCard.description = row.get("description");

            toolCards.add(toolCard);
        }

        return toolCards;
    }

    public static Map<String, String> getToolCard(final int idGame, final String toolCardname) {
        return ToolCardDB.getToolCard(idGame, toolCardname);
    }

    public static boolean boughtRunningPliers(final int idGame, final int idPlayer, final int previousRoundId) {
        return ToolCardDB.boughtRunningPliers(idGame, idPlayer, previousRoundId);
    }
}
