package de.bkdev.transformation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {
	
	public static String relationalDatabaseType;
	public static String relationalDatabasePath;
	public static String relationalDatabaseUser;
	public static String relationalDatabasePassword;
	public static String graphDatabasePath;
	public static String relationalDatabaseName;
	
	
	public static String getRelationalDatabaseName() {
		return relationalDatabaseName;
	}
	public static String getRelationalDatabaseType() {
		return relationalDatabaseType;
	}
	public static String getRelationalDatabasePath() {
		return relationalDatabasePath;
	}
	public static String getRelationalDatabaseUser() {
		return relationalDatabaseUser;
	}
	public static String getRelationalDatabasePassword() {
		return relationalDatabasePassword;
	}
	public static String getGraphDatabasePath() {
		return graphDatabasePath;
	}
	public static void main(String args[]){
		readFile("config.properties");
	}
	public static void readFile(String fileName){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config.properties");
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out
			relationalDatabaseType = prop.getProperty("relationalDatabaseType");
			relationalDatabasePath = prop.getProperty("relationalDatabasePath");
			relationalDatabaseUser = prop.getProperty("relationalDatabaseUser");
			relationalDatabasePassword = prop.getProperty("relationalDatabasePassword");
			relationalDatabaseName = prop.getProperty("relationalDatabaseName");
			graphDatabasePath = prop.getProperty("graphDatabasePath");
			
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
