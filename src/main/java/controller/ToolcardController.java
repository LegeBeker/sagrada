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
}
