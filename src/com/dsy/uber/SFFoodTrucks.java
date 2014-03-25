package com.dsy.uber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Singleton object holding all the parsed Food Trucks in San Francisco that meet our criteria
 * 
 * There were two approaches we were going to pursue:
 * 1) 100 % pre-cache of all list for every possible spot clicked on the map (640*640) lists, we looked at
 * approaches to minimize the memory usage. This strategy allows for the fewest number of calculations
 * but at the cost of much more memory usage.
 * 2) Calculate list for each click made by the user. This is the current method and it allows for the least
 * amount of memory usage but requires more computation time.
 * 3) Hybrid approach, with testing and modeling a best practical usage could be found.
 * 
 * @author dsy
 *
 */
public class SFFoodTrucks {
	//Master list of all the valid food trucks.
	private ArrayList<FoodTruck> allTrucks = new ArrayList<FoodTruck>();

	//remanent of old strategy to have all the answers ready for any possible query
	private ArrayList<FoodTruck> [][] answerKeyList;
	//Singleton
	private static SFFoodTrucks instance = null;
	
	protected SFFoodTrucks() {
		
	}
	public static SFFoodTrucks getInstance() {
		if(instance == null) {
			instance = new SFFoodTrucks();
		}
		return instance;
	}
	/**
	 * Add a FoodTruck entry more conviently
	 * @param foodTruck
	 */
	public void addTruck(FoodTruck foodTruck){
		allTrucks.add(foodTruck);
	}
	public ArrayList<FoodTruck> getAllTrucks() {
		return allTrucks;
	}
	public void setAllTrucks(ArrayList<FoodTruck> allTrucks) {
		this.allTrucks = allTrucks;
	}

	/**
	 * Pre-Cache strategy
	 */
	public void fillAnswerKey(){
		//System.out.println("Start");
		answerKeyList = new ArrayList[Coordinate.mapWidth][Coordinate.mapHeight];
		for (int i = 0 ; i < Coordinate.mapWidth; i++){
			for (int j = 0 ; j < Coordinate.mapHeight; j++){
				Coordinate ref = new Coordinate(i,j);

				for (FoodTruck truck: allTrucks){
					truck.setDistance(ref.distanceGross(truck.getLocation()));
				}

				Collections.sort(allTrucks, new Comparator<FoodTruck>(){
					public int compare(FoodTruck f1,FoodTruck f2){
						//return 0;
						return (new Double(f1.getDistance()).compareTo(new Double(f2.getDistance())));
					}}
						);

				answerKeyList[i][j] = new ArrayList<FoodTruck>(allTrucks);
			}
			//System.out.println(i);
		}
		//System.out.println("done filling answer key");
	}
	/**
	 *  On the fly calculations
	 * @param pixelX
	 * @param pixelY
	 * @return
	 */
	public ArrayList<FoodTruck> getList(int pixelX, int pixelY)
	{
		ArrayList<FoodTruck> retval = new ArrayList<FoodTruck>(allTrucks);

		Coordinate ref = new Coordinate(pixelX,pixelY);

		for (FoodTruck truck: retval){
			truck.setDistance(ref.distanceGross(truck.getLocation()));
		}

		Collections.sort(retval, new Comparator<FoodTruck>(){
			public int compare(FoodTruck f1,FoodTruck f2){
				return (new Double(f1.getDistance()).compareTo(new Double(f2.getDistance())));
			}});

		System.out.println("done filling answer key");
		return retval;
	}

}
