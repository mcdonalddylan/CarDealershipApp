package com.dealership.ui;

import java.util.Scanner;

import com.dealership.model.User;

public class OfferUI {

	
	public int showMenu(User user, Scanner scan)
	{
		boolean exit = false;
		int input = 0;
		
		//System.out.println("is employee?:" + user.isEmployee());
		//System.out.println("is employee?:" + user.isSystem());
			if(!user.isEmployee() && !user.isSystem())
			{
				System.out.println("");
				System.out.println(" Customer Offer Menu ");
				System.out.println("----------------------");
				System.out.println("1. View my offers");
				System.out.println("2. Make an offer");
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
				System.out.println(" Employee Offer Menu ");
				System.out.println("----------------------");
				System.out.println("1. View all offers");
				System.out.println("2. Make an offer");
				System.out.println("3. Accept an offer");
				System.out.println("4. Reject an offer");
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
				System.out.println("  System Offer Menu ");
				System.out.println("----------------------");
				System.out.println("1. View all offers");
				System.out.println("2. Make an offer");
				System.out.println("3. Accept an offer");
				System.out.println("4. Reject an offer");
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
