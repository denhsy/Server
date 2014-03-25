package com.dsy.uber;
/**
 * Food truck class defines fields availabe in XML (not all values are used)
 * @author dsy
 *
 */
public class FoodTruck{

	//Our own id value not tied to the values in the XML document
	private static int g_truck_id = 0;
	
	private int truckID;
	private String truckName;
	private String status;
	private String locationDescription;
	private String foodItems;
	private String schedulePDF;
	private String address;
	private String permit;
	private String truckType;
	private Coordinate location;
	
	private double distance;			//value used to determine closesness
	/**
	 * 
	 * Ctor with values we care about populated. This will need to be updated when more values
	 * are considered
	 * @param truckName
	 * @param status
	 * @param locationDescription
	 * @param foodItems
	 * @param schedulePDF
	 * @param address
	 * @param permit
	 * @param truckType
	 * @param location
	 */
	public FoodTruck(String truckName, String status,
			String locationDescription, String foodItems,
			String address, Coordinate location) {
		this.truckName = truckName;
		this.status = status;
		this.locationDescription = locationDescription;
		this.foodItems = foodItems;
	
		this.address = address;

		this.location = location;
		
		this.distance = 0;
		truckID = g_truck_id;
		g_truck_id++;
	}
	/* (non-Javadoc)
	 * This is the string that will appear to the user
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		String retval = "";
		retval += truckName + "***";
//		retval += status + " ";
		retval += locationDescription;
		return retval;
	}
	//GETTERS and SETTERS
	public Coordinate getLocation() {
		return location;
	}
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getTruckID() {
		return truckID;
	}
	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}
	
	
	
}
