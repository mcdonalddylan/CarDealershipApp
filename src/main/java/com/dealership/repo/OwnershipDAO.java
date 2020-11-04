package com.dealership.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dealership.model.Ownership;

public class OwnershipDAO {

	Logger logger = Logger.getLogger(OwnershipDAO.class);
	
	//Two things you always need when connecting to this database
	Connection con = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	//------------------------------------
	// Ownership methods
	//------------------------------------
	
	public boolean addOwnership(String username, int carId, double mAmount, double offer_amount)
	{
		LocalDate date = LocalDate.now();
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "insert into ownerships (username, car_id, purchase_date, monthly_amount, offer_amount, amount_remaining) values "
					+ "( ?, ?, ?, ?, ?, ? )"; // Were using a lot of ?'s here...
			stmt = con.prepareStatement(sql);
			
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, username);
			stmt.setInt(2, carId);
			stmt.setDate(3, java.sql.Date.valueOf(date));
			stmt.setDouble(4, mAmount);
			stmt.setDouble(5, offer_amount);
			stmt.setDouble(6, offer_amount);
			
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				//System.out.println("Offer Added Successfully!!");
				return true;
			}
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
	}
	
	public boolean updateOwnerhip(double amountRemaining, int ownershipId)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "update ownerships set amount_remaining = ? where own_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setDouble(1, amountRemaining);
			stmt.setInt(2, ownershipId);
			
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				logger.info("Ownership Updated Successfully!!");
				return true;
			}
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
	}
	
	public List<Ownership> getOwnedCars(String username)
	{
		List<Ownership> ownList = new ArrayList<>();
		
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from get_ownerships(?)";		
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			//if there exits at least one record...
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Ownership own = new Ownership();
				
					own.setOwnId(rs.getInt("own_id"));
					own.setUsername(rs.getString("username"));
					own.setCarId(rs.getInt("car_id"));
					own.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
					own.setMonthlyAmount(rs.getDouble("monthly_amount"));
					own.setAmountRemaining(rs.getDouble("amount_remaining"));
					own.setOfferAmount(rs.getDouble("offer_amount"));
					ownList.add(own);
				}
			}
			else
				ownList = null;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ownList = null;
				return ownList;
			}
		
		return ownList;
	}
}
