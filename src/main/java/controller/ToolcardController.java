package main.java.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ToolcardController {
	

	public void GrozingPliers() {
		int DieValue = 5;
		Connection conn;
		try {
			conn = DriverManager.getConnection(null);
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Starting value: " + DieValue);
		System.out.print("Enter 1 to add or 2 to subtract: ");
		int choice = input.nextInt();
		
		if (choice == 1) {
			if(DieValue == 6) {
				System.out.println("Value is 6 and cant become 1");
			}else {	
					DieValue++;
					System.out.println("Added 1, value is now " + DieValue);
					PreparedStatement stmt = conn.prepareStatement("UPDATE dice SET value = ? WHERE id = 1");
                    stmt.setInt(1, DieValue);
                    stmt.executeUpdate();
				}
			
			} else if (choice == 2) {
			if(DieValue == 1 ) {
				System.out.println("Value is 1 and cant become 6");
			}
			DieValue--;
			PreparedStatement stmt = conn.prepareStatement("UPDATE dice SET value = ? WHERE id = 1");
            stmt.setInt(1, DieValue);
            stmt.executeUpdate();
		} else {
			System.out.println("Invalid choice.");
		}
		conn.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
