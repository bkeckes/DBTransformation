package de.bkdev.transformation;

import de.bkdev.transformation.parser.DatabaseReader;

public class Transformator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<3){
	  		System.out.println("ERROR: You have to use the Parameter: dburl, dbname, user, password");
	  		return;
	  	}
		String dburl = args[0];
		String dbName =  args[1];
		String user = args[2];
		String pwd = args[3];
			
		DatabaseReader reader = new DatabaseReader(dburl,dbName,user,pwd);

	}

	

}
