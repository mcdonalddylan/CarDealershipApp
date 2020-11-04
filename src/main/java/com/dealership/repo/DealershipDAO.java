package com.dealership.repo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dealership.model.*;

/*
 * Data Access Object Implementation
 * 
 * 
 * getConnection()
 * closeResources()
 */
@SuppressWarnings(value = { "all" })
public class DealershipDAO
{	
	//singleton stuff
	private static DealershipDAO instance = null;
	
	private DealershipDAO()
	{
		super();
	}
	
	public static DealershipDAO getInstance()
	{
		if (instance == null)
		{
			instance = new DealershipDAO();
		}
		
		return instance;
	}
	
	static Logger logger = Logger.getLogger(DealershipDAO.class);
	
	//variables needed for url
	private String url;
	private String username;
	private String password;
	
	//Two things you always need when connecting to this database
	Connection con = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection

	//----------------------------------------------------------------------------------------
	// Basic DAO methods
	//----------------------------------------------------------------------------------------
	public Connection getConnection() 
	{
		Connection con = null;
	    try
	    {
	    	//file access stuff
	    	FileInputStream file = new FileInputStream("src/main/resources/connection.properties");
	    	Properties prop = new Properties();
	    	
	    	prop.load(file);
	    	
	    	username = prop.getProperty("username");
	    	password = prop.getProperty("password");
	    	url = prop.getProperty("url");
	    	
	    	Class.forName("org.postgresql.Driver");
	    	//con = DriverManager.getConnection(System.getenv("url"), System.getenv("username"),
	    			//System.getenv("password"));
	    	//con = DriverManager.getConnection("jdbc:postgresql://project0.c59oarwg7xwr.us-east-2.rds.amazonaws.com/postgres",
	    			//"postgres", "Booberton");
	    	con = DriverManager.getConnection(url, username, password);
	    	
	    	logger.info("Connection successful" + con);
	    	return con;
	    }
	    catch (ClassNotFoundException e)
	    { 
	    	e.printStackTrace();
	    	con = null;
	    }
	    catch (SQLException e)
	    { 
	    	e.printStackTrace();
	    	con = null;
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
			con = null;
			logger.error("connection.properties file not found");
		} catch (IOException e) {
			e.printStackTrace();
			con = null;
			logger.error("connection.properties file not found");
		}
	    return con;
	}
	
	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	public void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			logger.error("Could not close statement");
			e.printStackTrace();
		}
		
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			logger.error("Could not close statement");
			e.printStackTrace();
		}
	}
}
