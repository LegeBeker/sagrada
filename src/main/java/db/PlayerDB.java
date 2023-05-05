package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.enums.PlayStatusEnum;

public final class PlayerDB {
    private PlayerDB() {
    }

    public static Map<String, String> get(final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player WHERE idplayer = ? LIMIT 1";
        String[] params = {Integer.toString(idPlayer)};

        return db.exec(sql, params).get(0);
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player";

        return db.exec(sql, null);
    }

    public static List<Map<String, String>> getAll(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM player WHERE username = ?";
        String[] params = {username};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> createPlayer(final String username, final int idGame,
            final String playStatus, final String color) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO player (username, idgame, playstatus, private_objectivecard_color, score)"
                + "VALUE (?, ?, ?, ?, -20);";
        String[] params = {username, Integer.toString(idGame), playStatus, color};

        db.exec(sql, params);

        sql = "SELECT * FROM player WHERE username = ? AND idgame = ? AND playstatus = ? AND private_objectivecard_color = ?;";

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getRecentPlayerFromGame(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player WHERE idplayer = (SELECT MAX(idplayer) FROM player WHERE idgame = ? LIMIT 1);";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static boolean acceptInvite(final int gameId, final String playername) {
        Database db = Database.getInstance();

        String sql = "UPDATE player SET playstatus = '" + PlayStatusEnum.ACCEPTED + "' WHERE idgame = ? AND username = ?;";
        String[] params = {Integer.toString(gameId), playername};

        db.exec(sql, params);

        return true;
    }

    public static boolean refuseInvite(final int gameId, final String playername) {
        Database db = Database.getInstance();

        String sql = "UPDATE player SET playstatus =  '" + PlayStatusEnum.REFUSED + "'  WHERE idgame = ? AND username = ?;";
        String[] params = {Integer.toString(gameId), playername};

        db.exec(sql, params);

        return true;
    }

    public static boolean updatePatternCard(final int patternCardId, final int gameId, final String playername) {
        Database db = Database.getInstance();

        String sql = "UPDATE player SET idpatterncard = ? WHERE idgame = ? AND username = ?;";
        String[] params = {Integer.toString(patternCardId), Integer.toString(gameId), playername};

        db.exec(sql, params);

        return true;
    }

    public static int setSeqnr(final int playerId, final int seqNr) {
        Database db = Database.getInstance();

        String sql = "UPDATE player SET seqnr = ? WHERE idplayer = ?;";
        String[] params = {Integer.toString(seqNr), Integer.toString(playerId)};

        db.exec(sql, params);

        sql = "SELECT seqnr FROM player WHERE idplayer = " + Integer.toString(playerId) + ";";

        return Integer.parseInt(db.exec(sql, null).get(0).get("seqnr"));
    }

}
