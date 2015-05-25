package de.bkdev.transformation.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseReader {
	public static void main(String[] args) {
		Connection conn = null;
    	
    	try {
    	    conn =
    	       DriverManager.getConnection("jdbc:mysql://localhost/delikat?" +
    	                                   "user=root&password=admin");

    	    // Do something with the Connection
    	    System.out.println("Test");

    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
	}
}
