/**
 * 
 */
package com.dsy.uber;


/**
 * Server port is 8080
 * @author dsy
 *
 */
public class Server {

	public static final int NUM_OF_LISTINGS = 20;
	public static final int SERVER_PORT = 8080;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//SFFoodTrucks theTrucks = SFFoodTrucks.getInstance();
		
		//parse all the food truck data from the XML file into the SFFoodTrucks singleton
		FoodTruckXMLParser parser = new FoodTruckXMLParser();
		parser.parse();
		
		//Kick off web server
		new ServerThread(SERVER_PORT);
	}
	//package webs;


	
}


