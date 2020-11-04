package com.dealership.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dealership.model.Car;

public class CarDAO {

	static Logger logger = Logger.getLogger(CarDAO.class);
	
	//Two things you always need when connecting to this database
	Connection con = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	//------------------------------------
	// Car methods
	//------------------------------------
	
	public boolean addCar(Car car)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "insert into users (car_name, brand, car_type, color,"
					+ "smell, car_year, price, is_owned) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?)"; // Were using a lot of ?'s here...
			stmt = con.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, car.getCarName());
			stmt.setString(2, car.getBrand());
			stmt.setString(3, car.getType());
			stmt.setString(4, car.getColor()); 
			stmt.setInt(5, car.getSmellValue());
			stmt.setInt(6, car.getYear());
			stmt.setDouble(7, car.getPrice());
			stmt.setBoolean(8, car.isOwned());
			
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				System.out.println("Car Added Successfully!!");
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
	
	public boolean removeCar(int carId)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "delete from cars where car_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1,carId);
			
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				System.out.println("Car Removed Successfully!!");
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
	
	public boolean isCarId(int input)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "select car_id from cars where car_id = ?";	// Note the ? in the query
			stmt = con.prepareStatement(sql);
			
			// This command populate the 1st '?' with the title and wildcards, between ' '
			stmt.setInt(1, input);	
			
			ResultSet rs = stmt.executeQuery();
			
			//determines if there result was empty or not
			if(rs.isBeforeFirst())
			{
				logger.info("Car was found in database");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DealershipDAO.getInstance().closeResources();
		}
		
		System.out.println("Car not detected in the database");
		return false;
	}
	
	//displays cars according to their smell level
	public List<Car> getCars()
	{
		List<Car> carList = new ArrayList<>();
		try {
		con = DealershipDAO.getInstance().getConnection();
		String sql = "select * from cars order by smell desc";		
		stmt = con.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		
		//goes through every car in the db
		while(rs.next())
		{
			Car car = new Car();
			
			car.setId(rs.getInt("car_id"));
			car.setCarName(rs.getString("car_name"));
			car.setBrand(rs.getString("brand"));
			car.setType(rs.getString("car_type"));
			car.setColor(rs.getString("color"));
			car.setOwned(rs.getBoolean("is_owned"));
			car.setPrice(rs.getDouble("price"));
			car.setSmellValue(rs.getInt("smell"));
			car.setYear(rs.getInt("car_year"));
			
			carList.add(car);
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			carList = null;
			return carList;
		}	
		
		return carList;
	}
	
	public Car getCar(int carId)
	{
		Car car = new Car();
		try {
		con = DealershipDAO.getInstance().getConnection();
		String sql = "select * from cars where car_id = ?";		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, carId);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next())
		{
			car.setId(rs.getInt("car_id"));
			car.setCarName(rs.getString("car_name"));
			car.setBrand(rs.getString("brand"));
			car.setType(rs.getString("car_type"));
			car.setColor(rs.getString("color"));
			car.setOwned(rs.getBoolean("is_owned"));
			car.setPrice(rs.getDouble("price"));
			car.setSmellValue(rs.getInt("smell"));
			car.setYear(rs.getInt("car_year"));
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			car = null;
			return car;
		}	
		
		return car;
	}
	
	public boolean setIsOwned(int carId, boolean bool)
	{
		try {
			con = DealershipDAO.getInstance().getConnection();
			String sql = "update cars set is_owned = ? where car_id = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setBoolean(1, bool);
			stmt.setInt(2, carId);
			
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
			{
				logger.info("Car Updated Successfully!!");
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
