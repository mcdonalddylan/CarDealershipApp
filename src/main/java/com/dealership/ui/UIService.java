package com.dealership.ui;

import java.util.List;
import java.util.Scanner;

import com.dealership.model.Car;
import com.dealership.model.Offer;
import com.dealership.model.Ownership;
import com.dealership.model.Payment;
import com.dealership.model.User;
import com.dealership.repo.CarDAO;
import com.dealership.repo.OfferDAO;
import com.dealership.repo.OwnershipDAO;
import com.dealership.repo.PaymentDAO;
import com.dealership.repo.UserDAO;

public class UIService {

	static UserDAO uDAO = new UserDAO();
	static CarDAO cDAO = new CarDAO();
	static OfferDAO ofDAO = new OfferDAO();
	static OwnershipDAO owDAO = new OwnershipDAO();
	static PaymentDAO pDAO = new PaymentDAO();
	
	public boolean rememberMe(User user, Scanner scan)
	{
		System.out.print("Remember you on this machine? [y/n]: ");
		String answer = scan.next();
		
		if(answer.toLowerCase().equals("y"))
		{
			boolean bool = false;
			bool = uDAO.setRememberMe(user.getUsername(), true);
			System.out.println("");
			System.out.println("Will do!");
			System.out.println("");
			return bool;
		}
		else
		{
			boolean bool = false;
			bool = uDAO.setRememberMe(user.getUsername(), false);
			System.out.println("");
			System.out.println("Aw ok. :/");
			System.out.println("");
			return bool;
		}
		
	}
	
	public boolean addCarToLot(Scanner scan)
	{
		Car car = new Car();
		
		System.out.println("");
		System.out.print("Enter stench value: ");
		
		int input =0;
		if(scan.hasNextInt())
		{
			input = scan.nextInt();
			car.setSmellValue(input);
		}
		else
		{
			System.out.println("ERROR: Please enter a valid integer.");
			scan.nextLine();
			return false;
		}
		
		System.out.println("");
		System.out.print("Enter car name: ");
		String cName = scan.next();
		car.setCarName(cName);
		
		System.out.println("");
		System.out.print("Enter car brand: ");
		String cBrand = scan.next();
		car.setBrand(cBrand);
		
		System.out.println("");
		System.out.print("Enter car type: ");
		String cType = scan.next();
		car.setType(cType);
		
		System.out.println("");
		System.out.print("Enter car color: ");
		String cColor = scan.next();
		car.setType(cColor);
		
		System.out.println("");
		System.out.print("Enter year: ");
		
		int cYear =0;
		if(scan.hasNextInt())
		{
			cYear = scan.nextInt();
			car.setYear(cYear);
		}
		else
		{
			System.out.println("ERROR: Please enter a valid integer.");
			scan.nextLine();
			return false;
		}
		
		System.out.println("");
		System.out.print("Enter year: ");
		
		double cPrice = 0;
		if(scan.hasNextDouble())
		{
			cPrice = scan.nextDouble();
			car.setPrice(cPrice);
		}
		else
		{
			System.out.println("ERROR: Please enter a valid price value.");
			scan.nextLine();
			return false;
		}
		
		car.setOwned(false);
		
		cDAO.addCar(car);
		
		return true;
	}
	
	public boolean removeCar(Scanner scan)
	{
		int carId = 0;
		
		System.out.println("");
		System.out.print("Enter car ID value: ");
		
		if(scan.hasNextInt())
		{
			carId = scan.nextInt();
		}
		else
		{
			System.out.println("ERROR: Please enter a valid integer.");
			scan.nextLine();
			return false;
		}
		return cDAO.removeCar(carId);
	}
	
	public boolean makePayment(User user, Scanner scan)
	{
		//get ownership list
		List<Ownership> oList = owDAO.getOwnedCars(user.getUsername());
		//if list.size() == 0 or null then print error message saying that they don't own any cars
		if(oList == null )
		{
			System.out.println("ERROR: You don't own any cars bubby");
		}
		//if list.size() == 1 then ask for the payment amount while displaying the minimum payment amount
		// accepted for that month. Create payment with that payment amount and display a message stating whether
		// it went through or not
		else if (oList.size() == 1)
		{
			Ownership own = oList.get(0);
			if(own.getAmountRemaining() != 0.0)
			{
				Car car = cDAO.getCar(own.getCarId());
				System.out.println("");
				System.out.println("Car: " + car.getBrand() + " - " + car.getCarName() + ", " + car.getYear());
				System.out.println("Mimnum payment allowed: $" + own.getMonthlyAmount());
			
				System.out.println("");
				System.out.print("Enter amount: $");
			
				double cAmt = 0;
				if(scan.hasNextDouble())
				{
					cAmt = scan.nextDouble();
				
					if(cAmt >= own.getMonthlyAmount())
					{
						int carId = own.getCarId();
						//make the payment
						pDAO.addPayment(user.getUsername(), carId, own.getAmountRemaining()-cAmt, cAmt);
					
						//then update the ownership with proper amount remaining
						owDAO.updateOwnerhip(own.getAmountRemaining()-cAmt, own.getOwnId());
					
						System.out.println("");
						System.out.println("Payment made!");
						System.out.println("");
					}
					else
					{
						System.out.println("ERROR: Payment must be larger than monthly amount.");
					}
				}
				else
				{
					System.out.println("ERROR: Please enter a valid price value.");
					scan.nextLine();
					return false;
				}
			}
			else
			{
				System.out.println("ERROR: Your car is already paid off bubster.");
			}
		}
		//if list.size() > 1 then ask for the id of the car they wish to pay for. After that, do the same 
		// as mentioned above
		else if(oList.size() > 1)
		{
			System.out.println("TODO: let user choose which owned car to pay for.");
		}
		return true;
	}
	
	public boolean acceptOffer(Scanner scan) {
		
		int offerId = 0;
		
		System.out.println("");
		System.out.print("Enter offer ID value to accept: ");
		
		if(scan.hasNextInt())
		{
			offerId = scan.nextInt();
			
			if(ofDAO.acceptOffer(offerId))
			{
				Offer o = ofDAO.getOffer(offerId); //offer that was accepted
				Car car = o.getCar(); //car of the accepted offer
				List<Offer> oList = ofDAO.getOffers(car.getId()); //gets list of all offers for that car
				
				//rejects all offers related to that car except for the one that was accepted
				for(Offer off : oList)
				{
					if(off.getOfferId() != o.getOfferId())
						ofDAO.rejectOffer(off.getOfferId());
				}
				
				//calculating monthly amount
				double mAmount = 0.00;
				
				mAmount = o.getOfferAmount() / 72.00;
				
				owDAO.addOwnership(o.getUser().getUsername(), car.getId(), mAmount, o.getOfferAmount());
				cDAO.setIsOwned(car.getId(), true);
			}
			else
			{
				System.out.println("ERROR: Problem accepting the offer.");
				return false;
			}
		}
		else
		{
			System.out.println("ERROR: Please enter a valid integer.");
			scan.nextLine();
			return false;
		}
		
		return true;
	}
	
	public boolean rejectOffer(Scanner scan) {
		
		int offerId = 0;
		
		System.out.println("");
		System.out.print("Enter offer ID value to reject: ");
		
		if(scan.hasNextInt())
		{
			offerId = scan.nextInt();
			
			Offer o = ofDAO.getOffer(offerId);
			if(o.isRejected() == false && o.isAccepted() == false)
			{
				if(ofDAO.rejectOffer(offerId) == false)
				{
					System.out.println("ERROR: Problem accepting the offer.");
					return false;
				}
			}
		}
		else
		{
			System.out.println("ERROR: Please enter a valid integer.");
			scan.nextLine();
			return false;
		}
		
		return true;
	}
	
	public void viewMyOwnedCars(String username)
	{
		List<Ownership> ownList = null;
		
		ownList = owDAO.getOwnedCars(username);
		
		if(ownList != null)
		{
			System.out.println("");
			System.out.println("Here are your cars bub:");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			
			for(Ownership o : ownList)
			{
				Car c = cDAO.getCar(o.getCarId());
					System.out.println("ID: " + o.getOwnId());
					System.out.println("Car: " + c.getBrand() + " - " + c.getCarName() + ", " + c.getYear());
					System.out.println("Ownership Date: " + o.getPurchaseDate());
					System.out.println("Initial Offer Amount: $" + o.getOfferAmount());
					System.out.println("Amount Remaining: $" + o.getAmountRemaining());
					System.out.println("Monthly Amount Due: $" + o.getMonthlyAmount());
					System.out.println("");
			}
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		else
		{
			System.out.println("");
			System.out.println("ERROR: You don't own any cars from us bub.");
			System.out.println("");
		}
	}
	
	public void viewCarsOnLot()
	{
		List<Car> carList = null;
		
		carList = cDAO.getCars();
		
		System.out.println("");
		System.out.println("Here are the cars on our lot sorted by stench:");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("");
		
		for(Car c : carList)
		{
			//so long as the car isn't owned currently it will be shown
			if(!c.isOwned())
			{
				System.out.println("ID: " + c.getId());
				System.out.print("Stench Value: ");
				for(int i = 0; i < c.getSmellValue(); i++)
					System.out.print("[]");
				System.out.print("\n");
				System.out.println("Car: " + c.getBrand() + " - " + c.getCarName() + ", " + c.getYear());
				System.out.println("Type: " + c.getType());
				System.out.println("Color: " + c.getColor());
				System.out.println("Price: $" + c.getPrice());
				System.out.println("");
			}
		}
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("");
	}
	
	public void viewMyPayments(String username)
	{
		List<Payment> payList = null;
		
		payList = pDAO.getPayments(username);
		
		//as long as payments exist
		if(payList != null)
		{
			System.out.println("");
			System.out.println("Here are all your payments on record:");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			
			for(Payment p : payList)
			{
				Car c = cDAO.getCar(p.getCarId());
				
				System.out.println("Car: " + c.getBrand() + " - " + c.getCarName() + ", " + c.getYear());
				System.out.println("Payment Amount: $" + p.getPayment());
				System.out.println("Total Amount Remaining: $" + p.getAmntRemain());
				System.out.println("Date: " + p.getPayDate());
				System.out.println("");
			}
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		else
		{
			System.out.println("");
			System.out.println("ERROR: You haven't made any payments bucko.");
			System.out.println("");
		}
	}
	
	public void viewAllPayments()
	{
		List<Payment> payList = null;
		
		payList = pDAO.getPayments();
		
		//as long as payments exist
		if(payList != null)
		{
			System.out.println("");
			System.out.println("Here are ALL payments on record:");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			
			for(Payment p : payList)
			{
				Car c = cDAO.getCar(p.getCarId());
				
				System.out.println("Payment ID: " + p.getPayId());
				System.out.println("User: " + p.getUsername());
				System.out.println("Car: " + c.getBrand() + " - " + c.getCarName() + ", " + c.getYear());
				System.out.println("Payment Amount: $" + p.getPayment());
				System.out.println("Total Amount Remaining: $" + p.getAmntRemain());
				System.out.println("Date: " + p.getPayDate());
				System.out.println("");
			}
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		else
		{
			System.out.println("ERROR: No payments on record at all.");
		}
	}
	
	public void viewMyOffers(String username)
	{
		List<Offer> oList = null;
		
		oList = ofDAO.getOffers(username);
		
		//as long as offers exist
		if(oList.size() > 0)
		{
			System.out.println("");
			System.out.println("My offers:");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			
			for(Offer o : oList)
			{
				User user = uDAO.getUser(username);
				String fullName = user.getFirstName() + " " + user.getLastName();
				
				Car car = cDAO.getCar(o.getCar().getId());
				
				System.out.println("Offer ID: " + o.getOfferId());
				System.out.println("Car: " + car.getBrand() + " - " +car.getCarName() + ", " + car.getYear());
				System.out.println("Listed Price: $" + car.getPrice());
				System.out.println("Offer Amount: $" + o.getOfferAmount());
				if(o.isAccepted() == false && o.isRejected() == false)
					System.out.println("IS PENDING");
				else if(o.isAccepted() == true && o.isRejected() == false)
					System.out.println("ACCEPTED");
				else if(o.isAccepted() == false && o.isRejected() == true)
					System.out.println("REJECTED");
				System.out.println("");
			}
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		else
		{
			System.out.println("ERROR: You haven't made any offers yet bub.");
		}
	}
	
	public void viewAllOffers()
	{
		List<Offer> oList = null;
		
		oList = ofDAO.getOffers();
		
		//as long as payments exist
		if(oList.size() > 0)
		{
			System.out.println("");
			System.out.println("Here are ALL offers currently on record:");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			
			for(Offer o : oList)
			{
				User user = uDAO.getUser(o.getUser().getUsername());
				String fullName = user.getFirstName() + " " + user.getLastName();
				
				Car car = cDAO.getCar(o.getCar().getId());
				
				System.out.println("Offer ID: " + o.getOfferId());
				System.out.println("User: " + fullName);
				System.out.println("Car: " + car.getBrand() + " - " + car.getCarName() + ", " + car.getYear());
				System.out.println("Listed Price: $" + car.getPrice());
				System.out.println("Offer Amount: $" + o.getOfferAmount());
				if(o.isAccepted() == false && o.isRejected() == false)
					System.out.println("IS PENDING");
				else if(o.isAccepted() == true && o.isRejected() == false)
					System.out.println("ACCEPTED");
				else if(o.isAccepted() == false && o.isRejected() == true)
					System.out.println("REJECTED");
				System.out.println("");
			}
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
		}
		else
		{
			System.out.println("ERROR: No offers on record at all.");
		}
	}
	
	public void viewAllCars()
	{
		List<Car> carList = null;
		
		carList = cDAO.getCars();
		
		System.out.println("");
		System.out.println("All of the cars currently in the database:");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("");
		
		for(Car c : carList)
		{
				System.out.println("ID: " + c.getId());
				System.out.print("Stench Value: ");
				for(int i = 0; i < c.getSmellValue(); i++)
					System.out.print("[]");
				System.out.print("\n");
				System.out.println("Car: " + c.getBrand() + " - " + c.getCarName() + ", " + c.getYear());
				System.out.println("Type: " + c.getType());
				System.out.println("Color: " + c.getColor());
				System.out.println("Price: $" + c.getPrice());
				if(c.isOwned())
					System.out.println("OWNED");
				else
					System.out.println("ON THE LOT");
				System.out.println("");
		}
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("");
	}
	
	public boolean makeOffer(String username, Scanner scan)
	{
		System.out.println("Making an offer huh?");
		System.out.println("");
		System.out.println("We only provide 6 year");
		System.out.println("payment plans, 0% interest.");
		System.out.println("---------------------");
		System.out.println("");
		System.out.println("Enter the ID of the car: ");
		
		int carId = 0;
		//checks if input is an int
		if(scan.hasNextInt())
		{
			carId = scan.nextInt();
			
			//if the int given belongs to a car in the db....
			if(cDAO.isCarId(carId))
			{
				System.out.println("");
				System.out.println("What is your offer amount?: $");
				
				double price = 0;
				if (scan.hasNextDouble())
				{
					price = scan.nextDouble();
					
					ofDAO.addOffer(username, carId, price);
				}
				else
				{
					System.out.println("ERROR: Please enter a valid money value");
					return false;
				}
			}
			else
			{
				System.out.println("ERROR: Not a valid car id.");
				return false;
			}
			System.out.println("");
			System.out.println("Congratz! Offer made.");
			
		}else
		{
			System.out.println("");
			System.out.println("ERROR: Please enter a valid integer.");
			System.out.println("");
			scan.nextLine();
			return false;
		}

		return true;
	}	
}
