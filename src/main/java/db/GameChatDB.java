import java.sql.Date;
import java.util.List;
import java.util.Map;

public final class GameChatDB {
    private GameChatDB() {
    }

    public static void addMessage(final int idGame, final int idUser, final String message) {
        Database db = Database.getInstance();

        Date date = new Date(System.currentTimeMillis());

        String sql = "INSERT INTO chatline (idplayer, message) VALUES (?, ?, ?)";
        String[] params = { Integer.toString(idGame), message };

        db.exec(sql, params);
    }

    public static List<Map<String, String>> getMessages(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT idplayer, time, message, player.idgame FROM chatline INNER JOIN player ON chatline.idplayer = player.idplayer WHERE player.idgame = ? ORDER BY idplayer ASC";
        String[] params = { Integer.toString(idGame) };

        return db.exec(sql, params);
    }
}