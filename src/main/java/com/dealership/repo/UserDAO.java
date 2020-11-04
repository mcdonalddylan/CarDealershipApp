package com.dealership.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dealership.model.User;

public class UserDAO {

	Logger logger = Logger.getLogger(UserDAO.class);
	
	//Two things you always need when connecting to this database
	Connection con = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	//------------------------------------
	// User methods
	//------------------------------------
	
	public boolean addUser(User user)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "insert into users (username, pass, first_name, last_name, "
					+ "remember_me, is_employee, is_system) values "
					+ "( ?, ?, ?, ?, ?, ?, ?)"; // Were using a lot of ?'s here...
			stmt = con.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName()); 
			stmt.setBoolean(5, user.isRememberMe());
			stmt.setBoolean(6, user.isEmployee());
			stmt.setBoolean(7, user.isSystem());
			
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				logger.info("User Added Successfully!!");
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
	
	public boolean isUser(String username)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select username from users where username = ?";	// Note the ? in the query
			stmt = con.prepareStatement(sql);
			
			// This command populate the 1st '?' with the title and wildcards, between ' '
			stmt.setString(1, username);	
			
			ResultSet rs = stmt.executeQuery();
			
			//determines if there result was empty or not
			if(rs.isBeforeFirst())
			{
				logger.info("User Found!!");
				return true;
			}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		
		logger.error("Username not detected in the database");
		return false;
	}
	
	public User getUser(String username, String password)
	{
		User user = null;
		
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from users where username = ? and pass = ?";	// Note the ? in the query
			stmt = con.prepareStatement(sql);
			
			// This command populate the 1st '?' with the title and wildcards, between ' '
			stmt.setString(1, username);
			stmt.setString(2, password);	
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				while (rs.next()) {
					user = new User();

					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("pass"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setRememberMe(rs.getBoolean("remember_me"));
					user.setEmployee(rs.getBoolean("is_employee"));
					user.setSystem(rs.getBoolean("is_system"));
					
				}
				
				logger.info("username from db: " + user.getUsername());
				logger.info("password from db: " + user.getPassword());
			}
			else
			{
				logger.error("uh oh, password incorrect");
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		
		return user;
	}
	
	//OVERLOADED
	public User getUser(String username)
	{
		User user = null;
		
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select * from users where username = ?";	// Note the ? in the query
			stmt = con.prepareStatement(sql);
			
			// This command populate the 1st '?' with the title and wildcards, between ' '
			stmt.setString(1, username);	
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.isBeforeFirst())
			{
				while (rs.next()) {
					user = new User();

					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("pass"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setRememberMe(rs.getBoolean("remember_me"));
					user.setEmployee(rs.getBoolean("is_employee"));
					user.setSystem(rs.getBoolean("is_system"));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		
		return user;
	}
	
	public boolean setRememberMe(String username, boolean bool)
	{
		User user = null;
			
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "update users set remember_me = ? where username = ?";	// Note the ? in the query
			stmt = con.prepareStatement(sql);
			
			stmt.setBoolean(1, bool);
			stmt.setString(2, username);	
			
			//checks if the update was executed or not
			if (stmt.executeUpdate() != 0)
			{
				logger.info("User\'s remember me boolean was updated successfully!");
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
