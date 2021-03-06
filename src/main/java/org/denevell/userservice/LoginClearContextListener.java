package org.denevell.userservice;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoginClearContextListener implements ServletContextListener{
	public static EntityManagerFactory sEntityManager;

  @Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
		//LoginAuthKeysSingleton instance = LoginAuthKeysSingleton.getInstance();
		//instance.kill();
		//unload drivers
		sEntityManager.close();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("deregistering jdbc driver: " + driver);
            } catch (SQLException e) {
                System.out.println("Error deregistering driver " +  driver + " : " + e);
            }
        }		
	}
 
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	  sEntityManager = Persistence.createEntityManagerFactory("PERSISTENCE_UNIT_NAME");		
		System.out.println("ServletContextListener started");	
	}
}