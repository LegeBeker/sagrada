package main.java.db;

import java.util.List;
import java.util.Map;

public class DieDB {

	public static boolean putOffer(final int idGame, final int dieAmount) {
		Database db = Database.getInstance();

		String sql = "INSERT INTO gamedie (idgame, diecolor, dienumber, eyes) "
				+ "SELECT ?, d.color, d.number, FLOOR(RAND()* 6) + 1 " + "FROM die d " + "LEFT JOIN gamedie g "
				+ "ON d.color = g.diecolor AND d.number = g.dienumber AND g.idgame = ? " + "WHERE g.idgame IS NULL "
				+ "ORDER BY RAND() " + "LIMIT " + Integer.toString(dieAmount);
		
		String[] params = { Integer.toString(idGame), Integer.toString(idGame)};

		db.exec(sql, params);

		return true;
	}

	public static List<Map<String, String>> getOffer(final int idGame) {
		Database db = Database.getInstance();

		String sql = "SELECT * FROM gamedie WHERE idgame = ? AND eyes IS NOT NULL AND roundtrack IS NULL AND roundID IS NULL";

		String[] params = { Integer.toString(idGame) };

		return db.exec(sql, params);
	}
}
