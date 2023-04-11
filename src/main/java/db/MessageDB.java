package main.java.db;

import javafx.scene.chart.PieChart.Data;

public class MessageDB {
    // Create methods to add messages to the database
    public void addMessage(final String message, final int playerId) {
        // Add message to database
    }

    public List<Map<String, String>> getMessages(final int playerId) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM chatline WHERE idplayer = ?";
        String[] params = { Integer.toString(playerId) };

        return db.exec(sql, params);
    }

}
