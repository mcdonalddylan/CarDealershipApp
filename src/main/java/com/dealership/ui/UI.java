
package com.dealership.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.dealership.model.*;
import com.dealership.repo.*;

/*
 * User Interface class
 */
public class UI {

	static Logger logger = Logger.getLogger(UI.class);

	static UserDAO uDAO = new UserDAO();
	static CarDAO cDAO = new CarDAO();
	static OfferDAO ofDAO = new OfferDAO();
	static OwnershipDAO owDAO = new OwnershipDAO();
	static PaymentDAO pDAO = new PaymentDAO();
	static OfferUI offerMenu = new OfferUI();
	static CarUI carMenu = new CarUI();
	static MoneyUI payMenu = new MoneyUI();
	static UIService serv = new UIService();
	
	public UI()
	{
		//scan = new Scanner(System.in);
		//serv = new Service();
	}

	public int mainMenu(Scanner scan)
	{
		boolean isInt = true;
		
		System.out.println("    /         \\     /       \\");
		System.out.println("    \\         /     \\       /");
		System.out.println("    /         \\     /       \\");
		System.out.println("        ===============");
		System.out.println("=======/_______|_______\\=========");
		System.out.println(" Welcome To Dylan\'s Smelly Cars!");
		System.out.println("=====0====================0======");
		System.out.println("Please choose an option below:");
		System.out.println("---------------------------------");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. EXIT");
		
		System.out.println("");
		System.out.print("Enter number: ");
		
		int input =0;
		if(scan.hasNextInt())
		{
			input = scan.nextInt();
			return input;
		}
		else
		{
			System.out.println("ERROR: Please enter a valid integer.");
			scan.nextLine();
			return -1;
		}
	}
		
	public User loginMenu(Scanner scan)
	{
		boolean isUser = false;
		User user = null;
		
		System.out.println("");
		System.out.print("Enter your username: ");
		String username = scan.next();

			
		//check if the username and password line-up
		isUser = uDAO.isUser(username);
		
		if(isUser)
		{
			System.out.println("");
			System.out.print("Enter your password: ");
			String pass = scan.next();
			System.out.println("");
			
			user = uDAO.getUser(username, pass);
		}
			
		
		if(user == null)
		{
			System.out.println("ERROR: Invalid credentials.");
			System.out.println("");
		}	
		
		return user;
			
	}
	
	
	
	public boolean registerMenu(Scanner scan)
	{
		System.out.println("");
		System.out.println("     Register Below");
		System.out.println("Or type \"back\" to return");
		System.out.println("-------------------------");
		System.out.println("");
		System.out.print("Register new username: ");
		String user = scan.next();
		System.out.println("");
		
		//if user is doesn't exist already, then the register process can continue
		if(!uDAO.isUser(user) && !user.toLowerCase().equals("back"))
		{
			System.out.print("Enter your new password: ");
			String pass = scan.next();
			System.out.println("");
			
			System.out.print("Enter your first name: ");
			String first = scan.next();
			System.out.println("");
			
			System.out.print("Enter your last name: ");
			String last = scan.next();
			System.out.println("");
			
			User tempUser = new User(user, pass, first, last, false, false, false);
			
			if(uDAO.addUser(tempUser))
			{
				System.out.println(first + " " + last + " added successfully!");
				System.out.println("");
			}
			else
			{
				System.out.println("ERROR: Problem creating user.");
			}
		}
		else if (user.toLowerCase().equals("back"))
		{
			System.out.println("Back to menu...");
			System.out.println("");
			
			return false;
		}
		else
		{
			System.out.println("ERROR: That username already exists.");
			return false;
		}
			
		return true;
	}
	
	public int customerMenu(Scanner scan)
	{
		System.out.println("====================================");
		System.out.println("        Stanky Customer Menu");
		System.out.println("====================================");
		System.out.println("   Please choose an option below:");
		System.out.println("------------------------------------");
		System.out.println("1. View cars on the lot");
		System.out.println("2. Offer menu");
		System.out.println("3. Payment menu");
		System.out.println("4. View cars that you own");
		System.out.println("5. LOGOUT");
		
		System.out.println("");
		System.out.print("Enter number: ");
		
		int input = 0;
		if(scan.hasNextInt())
		{
			input = scan.nextInt();
			return input;
		}
		else
		{
			System.out.println("");
			System.out.println("ERROR: Please enter a valid integer.");
			System.out.println("");
			scan.nextLine();
			return -1;
		}
	}
	
	public int employeeMenu(Scanner scan)
	{
		System.out.println("=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		System.out.println("       Stanky Employee Menu");
		System.out.println("=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		System.out.println("  Please choose an option below:");
		System.out.println("-----------------------------------");
		System.out.println("1. Car menu");
		System.out.println("2. Offer menu");
		System.out.println("3. Payment menu");
		System.out.println("4. LOGOUT");
		
		System.out.println("");
		System.out.print("Enter number: ");
		
		int input =0;
		if(scan.hasNextInt())
		{
			input = scan.nextInt();
			return input;
		}
		else
		{
			System.out.println("");
			System.out.println("ERROR: Please enter a valid integer.");
			System.out.println("");
			scan.nextLine();
			return -1;
		}
	}
	
	public void offerMenu(User user, Scanner scan)
	{
		int input = 0;
		boolean exit = false;
		do
		{
			input = offerMenu.showMenu(user,scan);
			
			//System.out.println("input: " + input);
			//customer view offer
			if(!user.isEmployee() && !user.isSystem() && input == 1)
			{
				serv.viewMyOffers(user.getUsername());
			}
			//customer make offer
			else if(!user.isEmployee() && !user.isSystem() && input == 2)
			{
				serv.makeOffer(user.getUsername(), scan);
			}
			//customer exit
			else if(!user.isEmployee() && !user.isSystem() && input == 3)
			{
				exit = true;
			}
			else if(!user.isEmployee() && !user.isSystem() && input == -1)
			{
				//do nothing
			}
			//employee view all offers
			else if(user.isEmployee() && input == 1)
			{
				serv.viewAllOffers();
			}
			//employee make offer
			else if(user.isEmployee() && input == 2)
			{
				serv.makeOffer(user.getUsername(),scan);
			}
			//employee accept offer
			else if(user.isEmployee() && input == 3)
			{
				serv.acceptOffer(scan);
			}
			//employee reject offer
			else if(user.isEmployee() && input == 4)
			{
				serv.rejectOffer(scan);
			}
			//employee back
			else if(user.isEmployee() && input == 5)
			{
				exit = true;
			}
			else if(user.isEmployee() && input == -1)
			{
				//do nothing
			}
			else
			{
				System.out.println("");
				System.out.println("ERROR: Please enter a valid integer.");
				System.out.println("");
			}
			
		}while(!exit);
		
		scan.nextLine();
	}
	
	public void carMenu(User user,Scanner scan)
	{
		int input = 0;
		boolean exit = false;
		do
		{
			input = carMenu.showMenu(user,scan);
			
			//System.out.println("input: " + input);
			//employee view all cars
			if(user.isEmployee() && input == 1)
			{
				serv.viewAllCars();
			}
			//employee add car
			else if(user.isEmployee() && input == 2)
			{
				serv.addCarToLot(scan);
			}
			//employee remove car
			else if(user.isEmployee() && input == 3)
			{
				serv.removeCar(scan);
			}
			//employee view owned cars
			else if(user.isEmployee() && input == 4)
			{
				serv.viewMyOwnedCars(user.getUsername());
			}
			//employee exit
			else if(user.isEmployee() && input == 5)
			{
				exit = true;
			}
			else if(user.isEmployee() && input == -1)
			{
				//do nothing
			}
			//system view all cars
			else if(user.isSystem() && input == 1)
			{
				serv.viewAllCars();
			}
			//system add car
			else if(user.isSystem() && input == 2)
			{
				serv.addCarToLot(scan);
			}
			//system remove car
			else if(user.isSystem() && input == 3)
			{
				serv.removeCar(scan);
			}
			//system view owned cars
			else if(user.isSystem() && input == 4)
			{
				serv.viewMyOwnedCars(user.getUsername());
			}
			//system exit
			else if(user.isSystem() && input == 5)
			{
				exit = true;
			}
			else if(user.isSystem() && input == -1)
			{
				//do nothing
			}
			else
			{
				System.out.println("");
				System.out.println("ERROR: Please enter a valid integer.");
				System.out.println("");
			}
			
		}while(!exit);
		
		scan.nextLine();
	}
	
	public void payMenu(User user, Scanner scan)
	{
		int input = 0;
		boolean exit = false;
		do
		{
			input = payMenu.showMenu(user,scan);
			
			//System.out.println("input: " + input);
			//customer view my payments
			if(!user.isEmployee() && !user.isSystem() && input == 1)
			{
				serv.viewMyPayments(user.getUsername());
			}
			//customer make payment
			else if(!user.isEmployee() && !user.isSystem() && input == 2)
			{
				serv.makePayment(user,scan);
			}
			//customer exit
			else if(!user.isEmployee() && !user.isSystem() && input == 3)
			{
				exit = true;
			}
			else if(!user.isEmployee() && !user.isSystem() && input == -1)
			{
				//do nothing
			}
			//employee view ALL payments
			else if(user.isEmployee() && input == 1)
			{
				serv.viewAllPayments();
			}
			//employee view my payments
			else if(user.isEmployee() && input == 2)
			{
				serv.viewMyPayments(user.getUsername());
			}
			//employee make payment
			else if(user.isEmployee() && input == 3)
			{
				serv.makePayment(user,scan);
			}
			//employee exit
			else if(user.isEmployee() && input == 4)
			{
				exit = true;
			}
			else if(user.isEmployee() && input == -1)
			{
				//do nothing
			}
			else
			{
				System.out.println("");
				System.out.println("ERROR: Please enter a valid integer.");
				System.out.println("");
			}
			
		}while(!exit);
		
		scan.nextLine();
	}
	
	public int systemMenu(Scanner scan)
	{
		System.out.println("==================================");
		System.out.println("           System Menu");
		System.out.println("==================================");
		System.out.println("      Choose an option below:");
		System.out.println("--------------------------------");
		System.out.println("1. Say a funny thing");
		System.out.println("2. Conquer the world");
		System.out.println("3. LOGOUT");
		
		System.out.println("");
		System.out.print("Enter number: ");
		try {
			int input = scan.nextInt();
			return input;
		}catch(NumberFormatException e){
			e.printStackTrace();
			scan.nextLine();
			return -1;
		}catch(InputMismatchException e)
		{
			e.printStackTrace();
			System.out.println("");
			System.out.println("ERROR: Please enter a valid integer.");
			System.out.println("");
			scan.nextLine();
			return -1;
		}
	}

}
