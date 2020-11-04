package com.dealership.debug;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class Log4j {

	private Logger log = Logger.getLogger(Log4j.class);
	
	public Log4j()
	{
		//BasicConfigurator.configure();
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
