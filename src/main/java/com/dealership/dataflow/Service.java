package com.dealership.dataflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.dealership.model.*;
import com.dealership.repo.UserDAO;

public class Service {

	private String username;
	private String password;
	private String url;
	private UserDAO dao;
	static Logger logger = Logger.getLogger(Service.class);
	
	static File file;
	
	public Service()
	{
		dao = new UserDAO();
		file = new File("lastUser.txt");
	}
	
	//---------------------------
	// Remember Me Functionality
	//---------------------------
	
	//places user data into the lastName.txt file
	public boolean writeUserFile(User user)
	{
		
		try
		{	
			if(!file.exists())
			{
				file.createNewFile();
				logger.info("created lastName.txt file");
			}
			else
				logger.info("lastName.txt file found!");
			
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			bw.write(user.getUsername() + "\n");
			bw.write(user.getPassword() + "\n");
			
			bw.close();
			
			logger.info("user " + user.getUsername() + " passed into the lastUser.txt file");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	//extracts user data from the lastUser.txt file
	public User readUserFile()
	{
		User inputUser = null;
		
		try
		{
			if(file.exists())
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
			
				String username = br.readLine();
				
				if(username != null)
				{
					String password = br.readLine();
				
					logger.info("this username was extracted from the file: [" + username + "]");
					logger.info("this password was extracted from the file: [" + password + "]");
					
					br.close();
				
					inputUser = dao.getUser(username, password);
				
					if(inputUser != null)
					{
						logger.error("user from input file is not an active user");
					}
					else
					{
						logger.info("valid user gathered from lastUser.txt file");
					}
				}
				else
					logger.error("lastName.txt file is completely empty");
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("error getting user from file: FileNotFound");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("error getting user from file: IO");
		}
		
		return inputUser;
	}
}
