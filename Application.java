/* Significant Contributions by:
 * Kenneth Ando
 * Aaron Inocelias
 * Paul-Jason Mello
 */

import java.io.*;
import java.util.*;

public class Application {
	
		private static Scanner input = new Scanner(System.in);
		private static Scanner input2 = new Scanner(System.in);
		private static TreeMap<String, Integer> tableID = new TreeMap<String, Integer>();
		private static TreeMap<String, String> tableLocation = new TreeMap<String, String>();
		private static ArrayList<String> usernameList = new ArrayList<>();

		public static void main(String[] args) throws IOException {
			makeFileID();
			makeFileLocation();
			makeTableID();
			makeTableLocation();
			runIntro();
		}
		
		public static void runIntro() throws IOException {
			System.out.println("Welcome to UoL!");
			System.out.print("Are you looking to register a new item (register): ");
			String response = input.nextLine();
			if(response.toLowerCase().equals("register")) {
				runRegistration();
			} else {
				runFailedResponse(response);
			}
		}
		
		public static void runRegistration() throws IOException {
			System.out.print("Please create a username: ");
			String username = input.nextLine();
			if(tableID.containsKey(username)) {
				runFailedRegistrationDuplicateName();
			} else if (!username.matches("[a-zA-Z0-9]+")) {
				runFailedRegistrationAlphanumericName();		
			}else {
				System.out.print("Please enter ID of your device: ");
				while(!input.hasNextInt()) {
					System.out.println("Enter only digits for user ID please: ");
					input.next();
				}
				Integer ID = input.nextInt();
				while (count(ID) > 5) {
					System.out.println("Your ID must be less than 6 digits long. Please reenter your ID: ");
					ID = input.nextInt();
					count(ID);
				}
				
				if(tableID.containsValue(ID)) {
					runFailedRegistrationDuplicateID(username);
				}else {
					runSuccessfulRegistration(username, ID);
				}
			}
		}
		
//Success		
		
		public static void runSuccessfulReport(String username) throws IOException {
			System.out.println("The last location your device was seen in was" + tableLocation.get(username));
		}

		public static void runSuccessfulRegistrationDuplicateID(String username) throws IOException {
			System.out.print("Please enter the 5 digit ID number of your device: ");
			while(!input.hasNextInt()) {
				System.out.println("Enter only digits for user ID please: ");
				input.next();
			}
			Integer ID = input.nextInt();
			while (count(ID) > 5) {
				System.out.println("ID must be less than 6 digits long. Please reenter your ID: ");
				ID = input.nextInt();
				count(ID);
			}
			
			if(tableID.containsValue(ID)) {
				runFailedRegistrationDuplicateID(username);
			}else {
				runSuccessfulRegistration(username, ID);
			}
		}
		
		public static void runAddLocation(String username) throws IOException{
			System.out.print("Where are you currently located: ");
			String location = input2.nextLine();
	        File file = new File("C:Account-Location.txt");
        	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			tableLocation.put(username, location);
			output.println("{" + username + "=" + location + "}");
			output.close();
			currLine.close();
		}
		
		public static void runSuccessfulRegistration(String username, Integer ID) throws IOException {
	        File file = new File("C:Account-ID.txt");
        	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			tableID.put(username, ID);
			usernameList.add(username);
			output.println("{" + username + "=" + ID + "}");
			runAddLocation(username);
			output.close();
			currLine.close();
		}
		
//Failed
		public static void runFailedResponse(String response) throws IOException {
			System.out.println("I'm sorry you've entered an invalid input");
			System.out.println("If you'd like to register a new item type register: ");
			response = input.nextLine();
			if(response.toLowerCase().equals("register")) {
				runRegistration();
			}else {
				runFailedResponse(response);
			}
		}
		
		public static void runFailedRegistrationAlphanumericName() throws IOException {
			System.out.println("Your name can only contain letters and numbers, please create a new one: ");
			String username = input.nextLine();
			if(!username.matches("[a-zA-Z0-9]+")) {
				runFailedRegistrationAlphanumericName();
			}else {
				runSuccessfulRegistrationDuplicateID(username);			//no need to create a new success method.
			}															
		}
		
		public static void runFailedRegistrationDuplicateName() throws IOException {
			System.out.println("It seems your username is taken, please select a new one: ");
			String username = input.nextLine();
			if(tableID.containsKey(username)) {
				runFailedRegistrationDuplicateName();
			}else {
				runSuccessfulRegistrationDuplicateID(username);
			}
		}
		
		public static void runFailedRegistrationDuplicateID(String username) throws IOException {
			System.out.println("It seems your ID has already been activated, please re-enter your ID: ");
			Integer ID = input.nextInt();
			while(!input.hasNextInt()) {
				System.out.println("Enter only digits for user ID please: ");
				input.next();
			}
			while (count(ID) > 5) {
				System.out.println("ID must be less than 6 digits long. Please reenter your ID: ");
				ID = input.nextInt();
				count(ID);
			}
			
			if(tableID.containsValue(ID)) {
				runFailedRegistrationDuplicateID(username);
			}else {
				runSuccessfulRegistration(username, ID);
			}
		}
		
//Important Backend functionality
		public static TreeMap<String, String> getTableLocation() throws IOException {
			makeTableLocation();
			return tableLocation;
		}
		
		public static TreeMap<String, Integer> getTableID() throws IOException {
			makeTableID();
			return tableID;
		}
		
		public static ArrayList<String> getUsernameList() throws IOException {
			return usernameList;
		}
		
		public static void makeFileID() {
			try {
		        File files = new File("C:Account-ID.txt");
		        if (!files.exists()) {
		            files.createNewFile();
		        }

		        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(files, true)));
				output.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		public static void makeFileLocation() {
			try {
		        File files = new File("C:Account-Location.txt");
		        if (!files.exists()) {
		            files.createNewFile();
		        }

		        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(files, true)));
				output.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		public static void makeTableID() throws IOException {
	        File file = new File("C:Account-ID.txt");
	        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			String str = "";
			while(currLine.hasNext()) {
				String accountName = "";
				String productID = "";
				str = "" + currLine.next();
				for(int i = 0; i < str.length()-1; i++) {
					if(str.charAt(i) >= 65 && str.charAt(i) <= 122) {
						accountName = accountName + "" + str.charAt(i);
					}
					if((str.charAt(i) >= 48 && str.charAt(i) <= 57)) {
						productID = productID + "" + str.charAt(i);
					}
					if(str.charAt(i) == '}') {
						break;
					}
				}
				int ID = Integer.parseInt(productID);
				tableID.put(accountName, ID);
			}
			output.close();
			currLine.close();
		}
		
		public static void makeTableLocation() throws IOException {
	        File file = new File("C:Account-Location.txt");
	        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			Scanner currLine = new Scanner(file);
			String str = "";
			while(currLine.hasNext()) {
				String accountName = "";
				String location = "";
				str = "" + currLine.nextLine();
				for(int i = 0; i < str.indexOf('='); i++) {
					if(str.charAt(i) >= 65 && str.charAt(i) <= 122) {
						accountName = accountName + "" + str.charAt(i);
					}
				}
				for(int i = str.indexOf('=')+1; i < str.length()-1; i++) {
					if(str.charAt(i) != '}') {
						location = location + "" + str.charAt(i);
					}else {
						break;
					}
				}
				usernameList.add(accountName);
				tableLocation.put(accountName, location);
			}
			output.close();
			currLine.close();
		}
		
		public static int count(int n) throws IOException {
			if (n == 0)
				return 0;
			return 1 + count(n/10);
		}
}