package main.java.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import main.java.db.ToolCardDB;

public class ToolcardController {
	

	public void GrozingPliers() {
	    int DieValue = 5;
	    Scanner input = new Scanner(System.in);

	    System.out.println("Starting value: " + DieValue);
	    System.out.print("Enter 1 to add or 2 to subtract: ");
	    int choice = input.nextInt();

	    if (choice == 1) {
	        if(DieValue == 6) {
	            System.out.println("Value is 6 and cant become 7");
	        } else {
	            DieValue++;
	            System.out.println("Added 1, value is now " + DieValue);
	            ToolCardDB.updateGameDieValue(DieValue, choice);
	        }
	    } else if (choice == 2) {
	        if(DieValue == 1) {
	            System.out.println("Value is 1 and cant become 0");
	        } else {
	            DieValue--;
	            System.out.println("Subtracted 1, value is now " + DieValue);
	            ToolCardDB.updateGameDieValue(DieValue, choice);
	        }
	    } else {
	        System.out.println("Invalid choice.");
	    }
	}
}

