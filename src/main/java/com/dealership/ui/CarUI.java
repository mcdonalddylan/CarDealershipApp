package com.dealership.ui;

import java.util.Scanner;

import com.dealership.model.User;

public class CarUI {

	public int showMenu(User user, Scanner scan)
	{
		boolean exit = false;
		int input = 0;
		
		//System.out.println("is employee?:" + user.isEmployee());
		//System.out.println("is employee?:" + user.isSystem());
			if(user.isEmployee())
			{
				System.out.println("");
				System.out.println("  Employee Car Menu ");
				System.out.println("----------------------");
				System.out.println("1. View ALL cars");
				System.out.println("2. Add new car");
				System.out.println("3. Remove car");
				System.out.println("4. View cars that I own");
				System.out.println("5. BACK");
				
				System.out.println("");
				System.out.println("Enter a number: ");
				
				if(scan.hasNextInt())
				{
					input = scan.nextInt();
					return input;
				}
				else
				{
					//System.out.println("ERROR: Please enter a number between 1 and 5.");
					scan.nextLine();
					input = -1;
				}
			}
			else if(user.isSystem())
			{
				System.out.println("");
				System.out.println("   System Car Menu ");
				System.out.println("----------------------");
				System.out.println("1. View ALL cars");
				System.out.println("2. Add new car");
				System.out.println("3. Remove car");
				System.out.println("4. View cars that I own");
				System.out.println("5. BACK");
				
				System.out.println("");
				System.out.println("Enter a number: ");
				
				if(scan.hasNextInt())
				{
					input = scan.nextInt();
					return input;
				}
				else
				{
					//System.out.println("ERROR: Please enter a number between 1 and 5.");
					scan.nextLine();
					input = -1;
				}
			}
		
		return input;
	}
}
