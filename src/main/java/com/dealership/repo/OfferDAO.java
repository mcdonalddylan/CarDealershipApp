package com.dealership.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dealership.model.Offer;

public class OfferDAO {

	Logger logger = Logger.getLogger(OfferDAO.class);
	
	//Two things you always need when connecting to this database
	Connection con = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	UserDAO uDAO = new UserDAO();
	CarDAO cDAO = new CarDAO();
	
	//------------------------------------
	// Offer methods
	//------------------------------------
	
	public boolean addOffer(String username, int carId, double amount)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "insert into offers (username, car_id, offer_amount) values "
					+ "( ?, ?, ? )"; // Were using a lot of ?'s here...
			stmt = con.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, username);
			stmt.setInt(2, carId);
			stmt.setDouble(3, amount);
			
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
	
	public boolean removeOffer(int offerId)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "delete from offers where offer_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, offerId);
			
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				System.out.println("Offer Removed Successfully!!");
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
	
	public List<Offer> getOffers()
	{
		List<Offer> oList = new ArrayList<>();
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from offers order by offer_id desc";
			stmt = con.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Offer offer = new Offer();
				
				offer.setOfferId(rs.getInt("offer_id"));
				offer.setCar(cDAO.getCar((rs.getInt("car_id"))));
				offer.setUser(uDAO.getUser((rs.getString("username"))));
				offer.setOfferAmount(rs.getDouble("offer_amount"));
				offer.setAccepted(rs.getBoolean("accepted"));
				offer.setRejected(rs.getBoolean("rejected"));
				
				oList.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			oList = null;
			return oList;
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		return oList;
	}
	
	//Overloaded where it only gets offers of a certain car
	public List<Offer> getOffers(int carId)
	{
		List<Offer> oList = new ArrayList<>();
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from offers where car_id = ? order by offer_id desc";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, carId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Offer offer = new Offer();
				
				offer.setOfferId(rs.getInt("offer_id"));
				offer.setCar(cDAO.getCar((rs.getInt("car_id"))));
				offer.setUser(uDAO.getUser((rs.getString("username"))));
				offer.setOfferAmount(rs.getDouble("offer_amount"));
				offer.setAccepted(rs.getBoolean("accepted"));
				offer.setRejected(rs.getBoolean("rejected"));
				
				oList.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			oList = null;
			return oList;
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		return oList;
	}
	
	//Overloaded where it only gets offers of a certain user
	public List<Offer> getOffers(String username)
	{
		List<Offer> oList = new ArrayList<>();
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from offers where username = ? order by offer_id desc";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Offer offer = new Offer();
				
				offer.setOfferId(rs.getInt("offer_id"));
				offer.setCar(cDAO.getCar((rs.getInt("car_id"))));
				offer.setUser(uDAO.getUser((rs.getString("username"))));
				offer.setOfferAmount(rs.getDouble("offer_amount"));
				offer.setAccepted(rs.getBoolean("accepted"));
				offer.setRejected(rs.getBoolean("rejected"));
				
				oList.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			oList = null;
			return oList;
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		return oList;
	}
	
	public Offer getOffer(int offerId)
	{
		Offer offer = new Offer();
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from offers where offer_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, offerId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				offer.setOfferId(rs.getInt("offer_id"));
				offer.setCar(cDAO.getCar((rs.getInt("car_id"))));
				offer.setUser(uDAO.getUser((rs.getString("username"))));
				offer.setOfferAmount(rs.getDouble("offer_amount"));
				offer.setAccepted(rs.getBoolean("accepted"));
				offer.setRejected(rs.getBoolean("rejected"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			offer = null;
			return offer;
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		return offer;
	}
	
	public boolean acceptOffer(int offerId)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "update offers set accepted = true where offer_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, offerId);
			
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				System.out.println("Offer Accepted Successfully!!");
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
	
	public boolean rejectOffer(int offerId)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "update offers set rejected = true where offer_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, offerId);
			
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				logger.info("Offer Rejected Successfully!!");
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
}
