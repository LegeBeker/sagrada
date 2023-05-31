package main.java.controller;

import java.security.KeyStoreSpi;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javafx.scene.paint.Color;
import main.java.db.DieDB;
import main.java.db.GameDB;
import main.java.db.ToolCardDB;
import main.java.enums.ColorEnum;
import main.java.model.Die;
import main.java.model.ToolCard;

public class ToolcardController {
    private static final int MAX_VALUE = 6;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int TURNCOUNT = 2;
    private Random random = new Random();

    public static Map<String, String> getToolCard(final int gameId, final String toolCardName) {
        return ToolCard.getToolCard(gameId, toolCardName);
    }

    public void grozingPliers(final int gameId, final int dieNumber, final String dieColor, final String choiceAction) {
        //-- choiceaction is a string which can contain "increment" or "decrement". Based on this you can trigger the corresponding values 
        Scanner input = new Scanner(System.in);
        System.out.println("Starting value: " + dieValue);
        System.out.print("Enter 1 to add or 2 to subtract: ");
        int choice = input.nextInt();
        if (choice == 1) {
            if (dieValue == MAX_VALUE) {
                System.out.println("Value is 6 and cant become 7");
            } else {
                System.out.println("Added 1, value is now " + (dieValue + 1));
                ToolCardDB.updateGameDieValue(dieValue + 1, choice);
            }
        } else if (choice == 2) {
            if (dieValue == 1) {
                System.out.println("Value is 1 and cant become 0");
            } else {
                System.out.println("Subtracted 1, value is now " + (dieValue - 1));
                ToolCardDB.updateGameDieValue(dieValue - 1, choice);
            }
        } else {
            System.out.println("Invalid choice.");
        }

        input.close();
    }

    public void grindingStone(final int gameId, final int dieNumber, final String dieColor) {
        int dieValue = DieDB.getGameDieEyes(gameId, dieNumber, dieColor);
        int newValue = 7 - dieValue;
        ToolCardDB.updateGameDieValue(gameId, dieNumber, dieColor, newValue);    
    }

    public void fluxBrush(final int gameId, final int dieNumber, final String dieColor) {
        int currentValue = DieDB.getGameDieEyes(gameId, dieNumber, dieColor);
        int newValue;
        do {
            newValue = random.nextInt(SIX) + 1;
        } while (newValue == currentValue);

        ToolCardDB.updateGameDieValue(gameId, dieNumber, dieColor, newValue);
    }

    public void glazingHammer(final int turnCount, final int gameId, final int roundId) {
        if (turnCount == TURNCOUNT) {
            List<Die> gameOffer = Die.getOffer(gameId, roundId);
            for (Die die : gameOffer) {
                int newValue = random.nextInt(SIX) + 1;
                ToolCardDB.updateGameDieValue(gameId, die.getNumber(), die.getColor().toString(), newValue);
            }
        }
    }

    public void lensCutter(final int gameId, final int roundId) {
        Scanner input = new Scanner(System.in);
        List<Die> gameOffer = Die.getOffer(gameId, roundId);
        List<Die> roundTrack = Die.getRoundTrack(gameId);

        System.out.print("Enter which die you want to swap from the game offer: ");
        int choice = input.nextInt();
        Die selectedDie = gameOffer.get(choice - 1);

        System.out.print("Enter which die you want to swap from the round track: ");
        int roundTrackChoice = input.nextInt();
        Die selectedRoundtrack = roundTrack.get(roundTrackChoice - 1);

        Die temp = selectedDie;
        selectedDie = selectedRoundtrack;
        selectedRoundtrack = temp;

        Color color = selectedDie.getColor();
        String colorString = color.toString();

        ToolCardDB.updateGameDieColor(gameId, colorString);
        ToolCardDB.updateGameDieValue(gameId, selectedDie.getEyes());

        input.close();
    }

    public void fluxRemover(final int gameId, final int dieNumber, final String dieColor, final int roundId) {
        ToolCardDB.addDieToBag(gameId, dieColor, dieNumber, roundId);
    }
}
