package main.java.controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.paint.Color;
import main.java.db.ToolCardDB;
import main.java.model.Die;

public class ToolcardController {

    private static final int MAX_VALUE = 6;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int TURNCOUNT = 2;
    private Random random;

    public void grozingPliers(final int dieValue) {
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

    public void grindingStone() {
        int dieValue = SIX;
        System.out.println("Starting value: " + dieValue);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a command (flip to flip the die): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("flip")) {
                switch (Integer.toString(dieValue)) {
                case "1":
                    dieValue = SIX;
                    break;
                case "2":
                    dieValue = FIVE;
                    break;
                case "3":
                    dieValue = FOUR;
                    break;
                case "4":
                    dieValue = THREE;
                    break;
                case "5":
                    dieValue = TWO;
                    break;
                case "6":
                    dieValue = ONE;
                    break;
                default:
                    break;
                }
                ToolCardDB.updateGameDieValue(dieValue, 0);
                System.out.println("Die flipped. New value: " + dieValue);
            } else {
                System.out.println("Invalid command.");
            }
        }
    }

    public void fluxBrush(final int dieValue) {
        int currentValue = dieValue;
        int newValue;

        do {
            newValue = random.nextInt(SIX) + 1;
        } while (newValue == currentValue);

        System.out.println("Starting value: " + currentValue);
        System.out.println("New value: " + newValue);

        ToolCardDB.updateGameDieValue(newValue, 0);
    }

    public void glazingHammer(final int turnCount, final int gameId, final int roundId) {
        if (turnCount == TURNCOUNT) {

            List<Die> gameOffer = Die.getOffer(gameId, roundId);

            for (Die die : gameOffer) {
                int newValue = random.nextInt(SIX) + 1;

                ToolCardDB.updateGameDieValue(die.getNumber(), newValue);

            }
            System.out.println("Draft pool dice have been rerolled");
        } else {
            System.out.println("You can only reroll the draft pool when its ur second turn");
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
}
