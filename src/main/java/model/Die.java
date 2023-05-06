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

    public int getNumber() {
        return number;
    }

    public Game getGame() {
        return Game.get(this.idGame);
    }

    public static boolean createGameOffer(final int idGame) {
        return DieDB.createGameOffer(idGame);
    }

    public Boolean putRoundTrack(final int roundID) {
        DieDB.putRoundTrack(this.idGame, roundID, this.number, this.colorString);
        this.getGame().notifyObservers();
        return true;
    }

    public static ArrayList<Die> getOffer(final int idGame, final int roundID) {
        List<Map<String, String>> offer = DieDB.getOffer(idGame, roundID);
        ArrayList<Die> dice = new ArrayList<Die>();

        for (Map<String, String> dieMap : offer) {
            dice.add(mapToDie(dieMap));
        }

        return dice;
    }

    public static ArrayList<Die> getNewOffer(final int idGame, final int roundID, final int playerAmount) {
        int dieAmount = (playerAmount * 2) + 1;
        List<Map<String, String>> offer = DieDB.getNewOffer(idGame, roundID, dieAmount);
        ArrayList<Die> dice = new ArrayList<Die>();

        for (Map<String, String> dieMap : offer) {
            dice.add(mapToDie(dieMap));
        }

        return dice;
    }

    public static ArrayList<Die> getRoundTrack(final int idGame) {
        List<Map<String, String>> offer = DieDB.getRoundTrack(idGame);
        ArrayList<Die> dice = new ArrayList<Die>();

        for (Map<String, String> dieMap : offer) {
            dice.add(mapToDie(dieMap));
        }
        return dice;
    }

    public static Die mapToDie(final Map<String, String> dieMap) {
        Die die = new Die();

        die.idGame = Integer.parseInt(dieMap.get("idgame"));
        die.number = Integer.parseInt(dieMap.get("dienumber"));
        die.setColor(dieMap.get("diecolor"));
        die.colorString = dieMap.get("diecolor");
        die.eyes = Integer.parseInt(dieMap.get("eyes"));
        if (dieMap.get("roundtrack") != null) {
            die.roundtrack = Integer.parseInt(dieMap.get("roundtrack"));
        }
        if (dieMap.get("roundid") != null) {
            die.roundID = Integer.parseInt(dieMap.get("roundid"));
        }

        return die;
    }
}
