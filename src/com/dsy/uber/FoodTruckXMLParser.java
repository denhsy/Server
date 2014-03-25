/**
 * 
 */
package com.dsy.uber;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML parser takes XML file from SFData site (modified) to be used by the application
 * 
 * @author dsy
 *
 */
public class FoodTruckXMLParser {
	
	//We are only parse these few parameters we can expand to take more values in the XML as needed
	final String TRUCK_NAME = "applicant";
	final String LATITUDE = "latitude";
	final String LONGITUDE = "longitude";
	final String STATUS = "status";
	final String FOODITEMS = "fooditems";
	final String LOCATION_DESCRIPTION = "locationdescription";
	final String ADDRESS = "address";

	/**
	 * Main parse function will populate the SFFoodTrucks singleton
	 */
	public void parse(){
		try{
			SFFoodTrucks theTrucks = SFFoodTrucks.getInstance();
			//Get the DOM Builder Factory
			DocumentBuilderFactory factory = 
					DocumentBuilderFactory.newInstance();

			//Get the DOM Builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			//Load and Parse the XML document
			//document contains the complete XML as a Tree.
			Document document = 
					builder.parse(new File("sf.food.xml"));


			//1st level list of all the entries
			NodeList nodeList = document.getElementsByTagName("row");

			for (int i = 0; i < nodeList.getLength(); i++) {

				String truckName = "";
				String latitude = "";
				String longitude = "";
				String status = "";
				String foodItems = "";
				String locationDescription = "";
				String address = "";

				//2nd level children of 1st level
				//TODO: gather attributes of tags
				NodeList list = nodeList.item(i).getChildNodes();
				
				for (int j = 0; j < list.getLength(); j++)		{
					String nodeName = list.item(j).getNodeName();
					String nodeText = getText(list.item(j));
					
					//Looking for specific tags
					if (0 == nodeName.compareTo(TRUCK_NAME))
					{
						truckName = nodeText;
					}
					else if (0 == nodeName.compareTo(LATITUDE))
					{
						latitude = nodeText;
					}
					else if (0 == nodeName.compareTo(LONGITUDE))
					{
						longitude = nodeText;
					}
					else if (0 == nodeName.compareTo(STATUS))
					{
						status = nodeText;
					}
					else if (0 == nodeName.compareTo(FOODITEMS))
					{
						foodItems = nodeText;
					}
					else if (0 == nodeName.compareTo(LOCATION_DESCRIPTION))
					{
						locationDescription = nodeText;
					}
					else if (0 == nodeName.compareTo(ADDRESS))
					{
						address = nodeText;
					}
				}
				//If there is no applicant name or latitude and longitude don't add
				if (truckName.length() > 0 && latitude.length() > 0 && longitude.length() > 0){
					FoodTruck entry = new FoodTruck(truckName, status, locationDescription, foodItems, address, new Coordinate(Double.parseDouble(latitude), Double.parseDouble(longitude)));
					theTrucks.addTruck(entry);
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}


	/**
	 * Helper function to get the name values in a node
	 * 
	 * @param node
	 * @return
	 */
	public String getText(Node node) {
		StringBuffer result = new StringBuffer();
		if (node == null || ! node.hasChildNodes()) return "";

		NodeList list = node.getChildNodes();
		for (int i=0; i < list.getLength(); i++) {
			Node subnode = list.item(i);
			if (subnode.getNodeType() == Node.TEXT_NODE) {
				result.append(subnode.getNodeValue());
			}
			else if (subnode.getNodeType() == Node.CDATA_SECTION_NODE) {
				result.append(subnode.getNodeValue());
			}
			else if (subnode.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				// Recurse into the subtree for text
				// (and ignore comments)
				result.append(getText(subnode));
			}
		}
		return result.toString();
	}
}
