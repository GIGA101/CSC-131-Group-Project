/* Significant Contributions by:
 * Spencer Green
 * Aaron Inocelias
 */

import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class Locator {
	//private variables
	private static Scanner input = new Scanner(System.in);
	private static TreeMap<String, Integer> tableID = new TreeMap<String, Integer>();
	private static TreeMap<String, String> tableLocation = new TreeMap<String, String>();
	
	public static void main(String [] args) throws IOException {
		String username = "";
		String location = "";
		int ID = 0;
		initializeLocater();
		getUsername(username, location, ID);
	}

	public static void initializeLocater() throws IOException {
		Application.makeFileID();
		Application.makeFileLocation();
		tableID = Application.getTableID();
		tableLocation = Application.getTableLocation();
	}
		
	public static void getUsername(String username, String location, int ID) throws IOException {
		System.out.print("Enter the username associated with the device: ");
		username = input.nextLine();
		if(!(tableID.containsKey(username))) {
			System.out.println("That user does not exist: ");
			getUsername(username, location, ID);
		}else{
			getID(username, location, ID);
		}
	}
		
	public static void getID(String username, String location, int ID) throws IOException {
		System.out.print("Enter the ID associated with " + username +"'s device: ");
		ID = input.nextInt();
		if(!(tableID.get(username) == ID)) {
			System.out.println("That ID does not exist, or is not associated with that user: ");
			getID(username, location, ID);
		}else{
			getLocation(username, location, ID);
		}
	}
		
	public static void getLocation(String username, String location, int ID) throws IOException {
		System.out.println("The location of " + username + "'s device is in " + tableLocation.get(username)+ ".");
	}

}
