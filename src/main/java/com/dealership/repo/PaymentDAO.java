package com.dealership.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dealership.model.Payment;

public class PaymentDAO {

	Logger logger = Logger.getLogger(PaymentDAO.class);
	
	//Two things you always need when connecting to this database
	Connection con = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	//------------------------------------
	// Payment methods
	//------------------------------------
	
	public boolean addPayment(String username, int carId, double amount, double pay)
	{
		LocalDate date = LocalDate.now();
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "insert into payments (username, car_id, amount_remaining, payment, payment_date) values "
					+ "( ?, ?, ?, ?, ?)"; // Were using a lot of ?'s here...
			stmt = con.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, username);
			stmt.setInt(2, carId);
			stmt.setDouble(3, amount);
			stmt.setDouble(4, pay);
			stmt.setDate(5, java.sql.Date.valueOf(date));
			
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
	
	public List<Payment> getPayments(String username)
	{
		List<Payment> paymentList = new ArrayList<>();
		
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from payments where username = ? order by payment_date asc";		
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			//if there exists at least one payment record...
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Payment pay = new Payment();
				
					pay.setPayId(rs.getInt("payment_id"));
					pay.setUsername(rs.getString("username"));
					pay.setCarId(rs.getInt("car_id"));
					pay.setAmntRemain(rs.getDouble("amount_remaining"));
					pay.setPayment(rs.getDouble("payment"));
					pay.setPayDate(rs.getDate("payment_date").toLocalDate());
				
					paymentList.add(pay);
				}
			}
			else
				paymentList = null;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				paymentList = null;
				return paymentList;
			}	
		
		return paymentList;
	}
	
	//overloaded so that the employee can get all available payments
	public List<Payment> getPayments()
	{
		List<Payment> paymentList = new ArrayList<>();
		
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from payments order by payment_date asc";		
			stmt = con.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			//if there exits at least one record...
			if(rs.isBeforeFirst())
			{
				while(rs.next())
				{
					Payment pay = new Payment();
				
					pay.setPayId(rs.getInt("payment_id"));
					pay.setUsername(rs.getString("username"));
					pay.setCarId(rs.getInt("car_id"));
					pay.setAmntRemain(rs.getDouble("amount_remaining"));
					pay.setPayment(rs.getDouble("payment"));
					pay.setPayDate(rs.getDate("payment_date").toLocalDate());
				
					paymentList.add(pay);
				}
			}
			else
				paymentList = null;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				paymentList = null;
				return paymentList;
			}
		
		return paymentList;
	}
}
