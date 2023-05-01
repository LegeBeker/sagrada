package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.DieDB;
import main.java.enums.ColorEnum;

public class Die {
    private int idGame;
    private int number;
    private Color color;
    private String colorString;
    private int eyes;
    private int roundtrack;
    private int roundID;

    public void setColor(final String color) {
        ColorEnum colorEnum = ColorEnum.fromString(color);
        this.color = Color.web(colorEnum.getHexCode());
    }

    public int getEyes() {
        return eyes;
    }

    public Color getColor() {
        return color;
    }

    public int getRoundTrack() {
        return roundtrack;
    }

    public Game getGame() {
        return Game.get(this.idGame);
    }

    public static Boolean putOffer(final int idGame, final int playerAmount) {
        int dieAmount = (playerAmount * 2) + 1;
        return DieDB.putOffer(idGame, dieAmount);
    }

    public Boolean putRoundTrack(final int roundID) {
        DieDB.putRoundTrack(this.idGame, roundID, this.number, this.colorString);
        this.getGame().notifyObservers();
        return true;
    }

    public static ArrayList<Die> getOffer(final int idGame) {
        List<Map<String, String>> offer = DieDB.getOffer(idGame);
        ArrayList<Die> dice = new ArrayList<Die>();

        for (Map<String, String> dieMap : offer) {
            Die die = new Die();

            die.idGame = Integer.parseInt(dieMap.get("idgame"));
            die.number = Integer.parseInt(dieMap.get("dienumber"));
            die.setColor(dieMap.get("diecolor"));
            die.colorString = dieMap.get("diecolor");
            die.eyes = Integer.parseInt(dieMap.get("eyes"));

            dice.add(die);

        }
        return dice;
    }

    public static ArrayList<Die> getRoundTrack(final int idGame) {
        List<Map<String, String>> offer = DieDB.getRoundTrack(idGame);
        ArrayList<Die> dice = new ArrayList<Die>();

        for (Map<String, String> dieMap : offer) {
            Die die = new Die();

            die.idGame = Integer.parseInt(dieMap.get("idgame"));
            die.number = Integer.parseInt(dieMap.get("dienumber"));
            die.setColor(dieMap.get("diecolor"));
            die.colorString = dieMap.get("diecolor");
            die.eyes = Integer.parseInt(dieMap.get("eyes"));
            die.roundtrack = Integer.parseInt(dieMap.get("roundtrack"));

            dice.add(die);
        }
        return dice;
    }

}
