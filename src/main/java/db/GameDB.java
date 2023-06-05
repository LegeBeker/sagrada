package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.enums.PlayStatusEnum;

public final class GameDB {
    private GameDB() {
    }

    public static Map<String, String> get(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM game"
                + " JOIN round ON game.current_roundID = round.roundID"
                + " WHERE idgame = ? LIMIT 1;";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params).get(0);
    }

    public static List<Map<String, String>> getGamesList(final String username) {
        Database db = Database.getInstance();

        String sql = "SELECT game.*, DATE_FORMAT(game.creationdate, '%d-%m-%Y %H:%i:%s') AS formatted_creationdate, player.username, round.roundnr, "
                + "CASE WHEN pl2.username IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END AS isPlayerInGame, "
                + "CASE WHEN EXISTS (SELECT 1 FROM player pl3 WHERE game.idgame = pl3.idgame AND pl3.playstatus = 'refused') "
                + "THEN 'TRUE' ELSE 'FALSE' END AS hasDeclinedInvites "
                + "FROM game "
                + "LEFT JOIN player ON game.turn_idplayer = player.idplayer "
                + "LEFT JOIN player pl2 ON game.idgame = pl2.idgame AND pl2.username = ? "
                + "LEFT JOIN round ON round.roundID = game.current_roundID "
                + "WHERE game.idgame = 983 "
                + "GROUP BY game.idgame;";

        String[] params = {username};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getPlayers(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player WHERE idgame = ?";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> createGame(final String time) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO game (creationDate, current_roundID) VALUE(?, 1);";
        String[] params = {time};

        db.exec(sql, params);

        sql = "SELECT * FROM game WHERE creationdate = ?;";

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getColors(final int playerCount) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM color ORDER BY RAND() LIMIT " + Integer.toString(playerCount) + ";";

        return db.exec(sql, null);
    }

    public static List<Map<String, String>> setTurnPlayer(final int gameID, final int playerID) {
        Database db = Database.getInstance();

        String sql = "UPDATE game SET turn_idplayer = ? WHERE idgame = " + Integer.toString(gameID) + ";";
        String[] params = {Integer.toString(playerID)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> setRound(final int gameID, final int round) {
        Database db = Database.getInstance();

        String sql = "UPDATE game SET current_roundID = ? WHERE idgame = " + Integer.toString(gameID) + ";";
        String[] params = {Integer.toString(round)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> assignToolcards(final int gameID) {
        Database db = Database.getInstance();

        String sql = "SELECT idtoolcard FROM toolcard ORDER BY RAND() LIMIT 3;";
        List<Map<String, String>> toolcardIDs = db.exec(sql, null);

        String[] params = new String[1];
        sql = "INSERT INTO gametoolcard (idtoolcard, idgame) VALUE (?, " + Integer.toString(gameID) + ");";
        for (Map<String, String> toolcardMap : toolcardIDs) {
            params[0] = toolcardMap.get("idtoolcard");
            db.exec(sql, params);
        }

        sql = "SELECT * FROM gametoolcard WHERE idgame = " + Integer.toString(gameID) + ";";
        List<Map<String, String>> gametoolcardIDs = db.exec(sql, null);

        return gametoolcardIDs;
    }

    public static List<Map<String, String>> assignPublicObjectivecards(final int gameID) {
        Database db = Database.getInstance();

        String sql = "SELECT idpublic_objectivecard FROM public_objectivecard ORDER BY RAND() LIMIT 3;";
        List<Map<String, String>> publicObjectivecardIDs = db.exec(sql, null);

        String[] params = new String[1];
        sql = "INSERT INTO gameobjectivecard_public VALUE (" + Integer.toString(gameID) + ", ?);";
        for (Map<String, String> publicObjectivecardMap : publicObjectivecardIDs) {
            params[0] = publicObjectivecardMap.get("idpublic_objectivecard");
            db.exec(sql, params);
        }
        sql = "SELECT * FROM gameobjectivecard_public WHERE idgame = " + Integer.toString(gameID) + ";";
        return db.exec(sql, null);
    }

    public static Map<Integer, Boolean> getGamesWithOpenInvites(final String username) {
        Database db = Database.getInstance();

        String sql = "SELECT game.idgame, "
                + "CASE WHEN player.username = ? THEN 'TRUE' ELSE 'FALSE' END AS hasOpenInvite "
                + "FROM game "
                + "JOIN player ON game.idgame = player.idgame "
                + "WHERE player.playstatus = ? "
                + "GROUP BY game.idgame, hasOpenInvite;";

        String[] params = {username, PlayStatusEnum.CHALLENGEE.toString()};
        List<Map<String, String>> games = db.exec(sql, params);

        Map<Integer, Boolean> gamesWithOpenInvites = new java.util.HashMap<Integer, Boolean>();

        for (Map<String, String> game : games) {
            gamesWithOpenInvites.put(Integer.parseInt(game.get("idgame")),
                    Boolean.parseBoolean(game.get("hasOpenInvite")));
        }

        return gamesWithOpenInvites;
    }

    public static void finishGame(final int idgame) {
        Database db = Database.getInstance();

        String sql = "UPDATE player SET playstatus = '" + PlayStatusEnum.FINISHED.toString()
            + "' WHERE idgame = ?;";

        String[] params = {Integer.toString(idgame)};
        db.exec(sql, params);
    }
}
