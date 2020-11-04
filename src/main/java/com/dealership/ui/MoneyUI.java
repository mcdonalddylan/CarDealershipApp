package com.dealership.ui;

import java.util.Scanner;

import com.dealership.model.User;

public class MoneyUI {

	public int showMenu(User user, Scanner scan)
	{
		boolean exit = false;
		int input = 0;
		
			if(!user.isEmployee() && !user.isSystem())
			{
				System.out.println("");
				System.out.println("  Customer Payment Menu ");
				System.out.println("--------------------------");
				System.out.println("1. View my payments");
				System.out.println("2. Make a payment");
				System.out.println("3. BACK");
				
				System.out.println("");
				System.out.println("Enter a number: ");
				
				if(scan.hasNextInt())
				{
					input = scan.nextInt();
					return input;
				}
				else
				{
					//System.out.println("ERROR: Please enter a number between 1 and 3.");
					scan.nextLine();
					input = -1;
				}
			}
			else if(user.isEmployee())
			{
				System.out.println("");
				System.out.println(" Employee Payment Menu ");
				System.out.println("------------------------");
				System.out.println("1. View all payments");
				System.out.println("2. View my payments");
				System.out.println("3. Make a payment");
				System.out.println("4. BACK");
				
				System.out.println("");
				System.out.println("Enter a number: ");
				
				if(scan.hasNextInt())
				{
					input = scan.nextInt();
					return input;
				}
				else
				{
					//System.out.println("ERROR: Please enter a number between 1 and 4.");
					scan.nextLine();
					input = -1;
				}
			}
			else if(user.isSystem())
			{
				System.out.println("");
				System.out.println("  System Payment Menu ");
				System.out.println("------------------------");
				System.out.println("1. View all payments");
				System.out.println("2. View my payments");
				System.out.println("3. Make a payment");
				System.out.println("4. BACK");
				
				System.out.println("");
				System.out.println("Enter a number: ");
				
				if(scan.hasNextInt())
				{
					input = scan.nextInt();
					return input;
				}
				else
				{
					//System.out.println("ERROR: Please enter a number between 1 and 4.");
					scan.nextLine();
					input = -1;
				}
			}
		
		return input;
	}
}
