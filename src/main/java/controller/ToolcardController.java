package main.java.controller;

import java.util.Scanner;

import main.java.db.ToolCardDB;

public class ToolcardController {

    private static final int MAX_VALUE = 6;

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
        int dieValue = 6;
        System.out.println("Starting value: " + dieValue);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a command (flip to flip the die): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("flip")) {
                switch (dieValue) {
                case 1:
                    dieValue = 6;
                    break;
                case 2:
                    dieValue = 5;
                    break;
                case 3:
                    dieValue = 4;
                    break;
                case 4:
                    dieValue = 3;
                    break;
                case 5:
                    dieValue = 2;
                    break;
                case 6:
                    dieValue = 1;
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
}
