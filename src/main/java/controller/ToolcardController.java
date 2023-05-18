package main.java.controller;

import java.util.Random;
import java.util.Scanner;
import main.java.db.ToolCardDB;

public class ToolcardController {

    private static final int MAX_VALUE = 6;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
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

    public void fluxBrush(int dieValue) {
        int currentValue = dieValue;
        int newValue;

        do {
            newValue = random.nextInt(SIX) + 1;
        } while (newValue == currentValue);

        System.out.println("Starting value: " + currentValue);
        System.out.println("New value: " + newValue);

        ToolCardDB.updateGameDieValue(newValue, 0);
    }
}
