package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.DieDB;

public class Die {
    private int idGame;
    private int number;
    private Color color;
    private int eyes;
    private int roundtrack;
    private int roundID;

    public void setColor(final String color) {
        if (color == null) {
            this.color = Color.web("#F8F8F8");
            return;
        }
        switch (color) {
            case "red":
                this.color = Color.web("#F44336");
                return;
            case "blue":
                this.color = Color.web("#2196F3");
                return;
            case "green":
                this.color = Color.web("#4CAF50");
                return;
            case "yellow":
                this.color = Color.web("#FFEB3B");
                return;
            case "purple":
                this.color = Color.web("#9C27B0");
                return;
            default:
                this.color = Color.web("#F8F8F8");
        }
    }

    public int getEyes() {
        return eyes;
    }

    public Color getColor() {
        return color;
    }

    public static Boolean putOffer(final int idGame, final int playerAmount) {
        int dieAmount = (playerAmount * 2) + 1;
        return DieDB.putOffer(idGame, dieAmount);
    }

    public static ArrayList<Die> getOffer(final int idGame) {
        List<Map<String, String>> offer = DieDB.getOffer(idGame);
        ArrayList<Die> dice = new ArrayList<Die>();

        for (Map<String, String> dieMap : offer) {
            Die die = new Die();

            die.idGame = Integer.parseInt(dieMap.get("idgame"));
            die.number = Integer.parseInt(dieMap.get("dienumber"));
            die.setColor(dieMap.get("diecolor"));
            die.eyes = Integer.parseInt(dieMap.get("eyes"));

            dice.add(die);

        }
        return dice;
    }
}
