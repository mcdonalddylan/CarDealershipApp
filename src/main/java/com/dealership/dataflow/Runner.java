package com.dealership.dataflow;

import org.apache.log4j.Logger;

import java.util.Scanner;

import com.dealership.model.*;
import com.dealership.ui.UI;
import com.dealership.ui.UIService;
/*
 * 
 * +++++++++++++++++++++++++++++++++++++++++++
 *   Runner: class which runs the program :O
 * +++++++++++++++++++++++++++++++++++++++++++
 * 
 */
public class Runner {

	static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args)
	{
		//disables pgsql's warnings at the start of the program
		System.err.close();
		System.setErr(System.out);
		
		boolean quit = false;
		boolean rememberMe = true;
		
		UI ui = new UI();
		UIService uiServ = new UIService();
		Service serv = new Service();
		
		Scanner scan = new Scanner(System.in);
		
		User user;
		
		//checks if the user passed in a username and password before running
		/*
		if(args.length > 0)
		{
			if (args[0] == "system")
			{
				if(args[1] == "googis")
				{
					//send user to system menu
				}
				else
				{
					System.out.println("ERROR: Invalid system password.");
					logger.error("Invalid system password");
				}
			}
		}
		*/
		
		//++++++++
		//Main Menu
		//++++++++
		do
		{
			//checks the file for a user that that may have asked to be remembered
			if(rememberMe)
				user = serv.readUserFile();
			else
				user = null;
			
			//if nothing came back or the user didn't want to be remembered, the program goes on
			int inputMain = 0;
			
			//System.out.println("does user want you to remember them?: " + user.isRememberMe());
			
			if(user == null || user.isRememberMe() == false)
			{
				inputMain = ui.mainMenu(scan);
				rememberMe = false;
			}
			else
			{
				System.out.println("");
				System.out.println("Welcome back " + user.getFirstName() + "!");
				System.out.println("");
				
				inputMain = 1;
				rememberMe = true;
			}
			
			if (inputMain > 0 && inputMain < 4)
			{
				if (inputMain == 1)
				{
					//=====
					//Login Menu
					//=====
					if(rememberMe == false)
					{
						user = ui.loginMenu(scan);
						
						if(user != null)
							uiServ.rememberMe(user, scan);
					}
					
					//---------
					//Customer Menu
					//--------
					if(user != null && user.isEmployee() == false && user.isSystem() == false)
					{
						boolean backToMain = false;
						
						serv.writeUserFile(user);
						logger.info(user.getFirstName() + " " + user.getLastName() + " just logged in");
						
						do
						{
							int inputCust = ui.customerMenu(scan);
							
							//viewing cars
							if(inputCust == 1)
							{
								uiServ.viewCarsOnLot();
							}
							//open offer menu
							else if (inputCust == 2)
							{
								ui.offerMenu(user, scan);
							}
							//view remaining payments
							else if (inputCust == 3)
							{
								ui.payMenu(user, scan);
							}
							//view owned cars
							else if (inputCust == 4)
							{
								uiServ.viewMyOwnedCars(user.getUsername());
							}
							//go back to main menu
							else if (inputCust == 5)
							{
								backToMain = true;
								rememberMe = false;
							}
							else if (inputCust == -1)
							{
								//do nothing
							}
							else
							{
								//System.out.println("ERROR: Please type a number between 1 and 5.");
								logger.error("incorrect input in the customer menu");
							}	
							
						}while(backToMain == false);
						
					}
					//--------
					//Employee Menu
					//--------
					else if (user != null && user.isEmployee() == true && user.isSystem() == false)
					{
						boolean backToMain = false;
						
						serv.writeUserFile(user);
						logger.info(user.getFirstName() + " " + user.getLastName() + " just logged in");
						
						do
						{
							int inputEm = ui.employeeMenu(scan);
							
							//car menu
							if(inputEm == 1)
							{
								ui.carMenu(user,scan);
							}
							//offer menu
							else if (inputEm == 2)
							{
								ui.offerMenu(user,scan);
							}
							//pay menu
							else if (inputEm == 3)
							{
								ui.payMenu(user, scan);
							}
							//open offer menu
							else if (inputEm == 4)
							{
								backToMain = true;
								rememberMe = false;
							}
							else if (inputEm == -1)
							{
								//do nothing
							}
							else
							{
								//System.out.println("ERROR: Please type a number between 1 and 4.");
								logger.error("incorrect input in the employee menu");
							}
								
						}while(backToMain == false);
					}
					//--------
					//System Menu
					//--------
					else if (user != null && user.isEmployee() == false && user.isSystem() == true)
					{
						boolean backToMain = false;
						
						do
						{
							int inputSys = ui.systemMenu(scan);
							
							//say funny thing
							if(inputSys == 1)
							{
								//may or may not add this
							}
							//conquer the world
							else if (inputSys == 2)
							{
								//may or may not add this
							}
							//go back to main menu
							else if (inputSys == 3)
								backToMain =true;
							else if (inputSys == -1)
							{
								//do nothing
							}
							else
							{
								//System.out.println("ERROR: Please type a number between 1 and 3.");
								logger.error("incorrect input in the system menu");
							}
									
						}while(backToMain == false);
						
					}
					
				}
				//======
				//Register New Account Menu
				//======
				else if (inputMain == 2)
				{
					//register
					ui.registerMenu(scan);
				}
				//======
				//Exit Command
				//======
				else if (inputMain == 3)
				{
					quit = true;
				}
			}
			else
			{
				//System.out.println("ERROR: Please type a number between 1 and 3.");
				logger.error("incorrect input in the main menu");
			}
		}while(quit == false);
		
		//closes the scanner before the program ends
		closeScan(scan);
		
		System.out.println("");
		System.out.println("Smell ya later.");
		
	}
	
	private static void closeScan(Scanner scan)
	{
		scan.close();
	}
}
