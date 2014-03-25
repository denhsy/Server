/**
 * 
 */
package com.dsy.uber;

/**
 * Coordinate class will convert a Lat, Lon value to X,Y pixel area defined as 640 x 640 for this example
 * 
 * @author dsy
 *
 */
public class Coordinate {
	static final int mapWidth = 640;
	static final int mapHeight = 640;
	
	//AVG earth radius, this value should be adjusted for latitude of interest
	final double EARTH_RADIUS_IN_KM = 6371; // km
	final double CENTER_LATITUDE = 37.75855938;
	final double CENTER_LONGITUDE = -122.4316869;
	//origin coordinates used in web canvas
	final double UPPER_LEFT_LATITUDE = 37.80189105;
	final double UPPER_LEFT_LONGITUDE = -122.4868003;
	
	//Based on 640x640 image
	final double DEGREE_LATITUDE_PER_PIXEL = 0.000135411;
	final double DEGREE_LONGITUDE_PER_PIXEL = 0.000172229;
	
	//Latitude and Longitude converted to our view space
	private double pixelOffsetX;
	private double pixelOffsetY;
	
	private double latitude;
	private double longitude;

	public Coordinate()
	{
		latitude = CENTER_LATITUDE;
		longitude = CENTER_LONGITUDE;
		
		pixelOffsetX = 0;
		pixelOffsetY = 0;
	}
	/**
	 * Coordinates created using pixels
	 * 
	 * @param pixelX
	 * @param pixelY
	 */
	public Coordinate(int pixelX, int pixelY)
	{
		latitude = UPPER_LEFT_LATITUDE + pixelY*DEGREE_LATITUDE_PER_PIXEL;
		longitude = UPPER_LEFT_LONGITUDE + pixelX*DEGREE_LONGITUDE_PER_PIXEL;
		
		pixelOffsetX = pixelX;
		pixelOffsetY = pixelY;
	}
	/**
	 * Coordinates created using decimal degrees
	 * @param lat
	 * @param lon
	 */
	public Coordinate(double lat, double lon){
		latitude = lat;
		longitude = lon;
		
		pixelOffsetY = ((UPPER_LEFT_LATITUDE - latitude)/DEGREE_LATITUDE_PER_PIXEL);
		pixelOffsetX = ((longitude - UPPER_LEFT_LONGITUDE)/DEGREE_LONGITUDE_PER_PIXEL);
	}

	/**
	 * Calculated distance in the grid space of pixels used as a good approximation for actual distance
	 * 
	 * @param c1
	 * @return
	 */
	public double distanceGross(Coordinate c1){
		double retval = 0;
		
		retval = Math.sqrt(
				(this.pixelOffsetX - c1.pixelOffsetX) * (this.pixelOffsetX - c1.pixelOffsetX) + 
				(this.pixelOffsetY - c1.pixelOffsetY) * (this.pixelOffsetY - c1.pixelOffsetY));
		
		return retval;
	}
	/**
	 * Highly accurate distance between two points on a sphere (EARTH)
	 * 
	 * @param c1
	 * @return
	 */
	public double distance(Coordinate c1){
		double retval = 0;
		double deltaLatitudeRadians = deg2rad(c1.latitude-this.latitude);
		double deltaLongitudeRadians = deg2rad(c1.longitude-this.longitude);
		double Latitude1Radians = deg2rad(this.latitude);
		double Latitude2Radians = deg2rad(c1.latitude);


		/*Haversine formula:
		 * 	 a = sin²(Δφ/2) + cos(φ1).cos(φ2).sin²(Δλ/2)
		 *   c = 2.atan2(√a, √(1−a))
		 *   d = R.c
		 */
		double a = Math.sin(deltaLatitudeRadians/2) * Math.sin(deltaLatitudeRadians/2) +
				Math.sin(deltaLongitudeRadians/2) * Math.sin(deltaLongitudeRadians/2) * 
				Math.cos(Latitude1Radians) * Math.cos(Latitude2Radians); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 

		double d = EARTH_RADIUS_IN_KM * c;

		retval = d;

		return retval;


	}
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	public String toString(){
		String retval = "";
		
		retval += latitude + " ";
		retval += longitude + " ";
		retval += pixelOffsetX + " ";
		retval += pixelOffsetY + " ";
		
		return retval;
	}
	//GETTERS and SETTERS
	public double getPixelOffsetX() {
		return pixelOffsetX;
	}
	public void setPixelOffsetX(double pixelOffsetX) {
		this.pixelOffsetX = pixelOffsetX;
	}
	public double getPixelOffsetY() {
		return pixelOffsetY;
	}
	public void setPixelOffsetY(double pixelOffsetY) {
		this.pixelOffsetY = pixelOffsetY;
	}
	
}
