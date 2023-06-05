package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.enums.ColorEnum;

public final class ToolCardDB {
    private ToolCardDB() {
    }

    public static List<Map<String, String>> getToolCards(final int idGame) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM toolcard tc JOIN gametoolcard gtc ON tc.idtoolcard = gtc.idtoolcard WHERE gtc.idgame = ?";
        String[] params = {Integer.toString(idGame)};
        return db.exec(sql, params);
    }

    public static Map<String, String> getToolCard(final int idGame, final String toolCardname) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM toolcard tc JOIN gametoolcard gtc ON tc.idtoolcard = gtc.idtoolcard WHERE gtc.idgame = ? AND tc.name = ?";
        String[] params = {Integer.toString(idGame), toolCardname};
        return db.exec(sql, params).get(0);
    }

    public static boolean updateGameDieValue(final int idgame, final int dieNumber, final String dieColor,
            final int eyes) {
        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET eyes = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        String[] params = {Integer.toString(eyes), Integer.toString(idgame), Integer.toString(dieNumber),
                ColorEnum.fromString(dieColor).getName()};
        db.exec(sql, params);
        return true;
    }

    public static boolean updateGameDieColor(final int idgame, final String color) {
        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET diecolor = ? WHERE idgame = ?";
        String[] params = {color.toString(), Integer.toString(idgame)};
        db.exec(sql, params);
        return true;
    }

    public static void addDieToBag(final int idgame, final String dieColor, final int dieNumber, final int roundID) {
        Database db = Database.getInstance();

        // -- Update old die
        String sql = "UPDATE gamedie SET roundID = NULL WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        String[] params = {Integer.toString(idgame), Integer.toString(dieNumber),
                ColorEnum.fromString(dieColor).getName()};
        db.exec(sql, params);

        // -- Generate new die
        String sql2 = "UPDATE gamedie SET roundID = ? WHERE idgame = ? AND roundID IS NULL ORDER BY RAND() LIMIT 1";
        // -- The roundID is 1 greater than the current round...
        String[] params2 = {Integer.toString(roundID - 1), Integer.toString(idgame)};
        db.exec(sql2, params2);
    }

    public static void lensCutter(final int gameId, final int currentRoundId, final int dieNumberOffer,
            final String dieColorOffer, final int dieNumberRoundTrack, final String dieColorRoundTrack) {
        int roundtrack = DieDB.getRoundTrackFromDie(gameId, dieNumberRoundTrack, dieColorRoundTrack);

        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET roundID = ?, roundtrack = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        String[] params = {Integer.toString(roundtrack), Integer.toString(roundtrack), Integer.toString(gameId),
                Integer.toString(dieNumberOffer), ColorEnum.fromString(dieColorOffer).getName()};
        db.exec(sql, params);

        sql = "UPDATE gamedie SET roundID = ?, roundtrack = NULL WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        params = new String[] {Integer.toString(currentRoundId), Integer.toString(gameId),
                Integer.toString(dieNumberRoundTrack), ColorEnum.fromString(dieColorRoundTrack).getName()};
        db.exec(sql, params);

    }

    public static boolean boughtRunningPliers(final int gameId, final int playerId, final int previousRoundId) {
        Database db = Database.getInstance();
        String sql = "SELECT COUNT(*) AS 'boughtRunningPliers' FROM gamefavortoken "
        + "WHERE idgame = ? AND idplayer = ? AND roundID = ? AND gametoolcard = 8;";
        String[] params = {Integer.toString(gameId), Integer.toString(playerId), Integer.toString(previousRoundId)};
        int amountBought = Integer.parseInt(db.exec(sql, params).get(0).get("boughtRunningPliers"));

        return amountBought > 0;
    }
}
