/* Significant Contributions by:
 * Rhys Galloway
 * Aaron Inocelias
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Finder {
	//private variables
	private static Scanner input = new Scanner(System.in);
	private static Scanner input2 = new Scanner(System.in);
	private static TreeMap<String, Integer> tableID = new TreeMap<String, Integer>();
	private static TreeMap<String, String> tableLocation = new TreeMap<String, String>();
	private static ArrayList<String> usernameList = new ArrayList<>();
	
	public static void main(String [] args) throws IOException {
		String username = "";
		String location = "";
		int ID = 0;
		initializeFinder();
		getUsername(username, location, ID);
	}
	
	public static void initializeFinder() throws IOException {
		Application.makeFileID();
		Application.makeFileLocation();
		tableID = Application.getTableID();
		tableLocation = Application.getTableLocation();
		usernameList = Application.getUsernameList();
	}
	//Finder Code
	public static void getUsername(String username, String location, int ID) throws IOException {
		System.out.print("Enter the username associated with the device you want to update: ");
		username = input.nextLine();
		if(!(tableID.containsKey(username))) {
			System.out.println("That user does not exist: ");
			getUsername(username, location, ID);
		}else {
			getID(username, location, ID);
		}
	}
	
	public static void getID(String username, String location, int ID) throws IOException {
		System.out.print("Enter the ID associated with " + username +"'s device: ");
		ID = input.nextInt();
		if(!(tableID.get(username) == ID)) {
			System.out.println("That ID does not exist, or is not associated with that user: ");
			getID(username, location, ID);
		}else {
			updateLocation(username, location, ID);
		}
	}
	
	public static void updateLocation(String username, String location, int ID) throws IOException {
		System.out.print("Enter the updated location the current item is at: ");
		location = input2.nextLine();
		String oldLocation = tableLocation.get(username);
		tableLocation.replace(username, location);
		updateAccountLocationFile(username, location, oldLocation);
	}
	
	public static void updateAccountLocationFile(String username, String location, String oldLocation) throws IOException {
		File file = new File("C:Account-Location.txt");
		file.delete();
		Application.makeFileLocation();
		file = new File("C:Account-Location.txt");
    	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
    	for(int i = 0; i < tableLocation.size(); i++) {
    		output.println("{" + usernameList.get(i) + "=" + tableLocation.get(usernameList.get(i)) + "}");
    	}
		
		output.close();
	}
		
}