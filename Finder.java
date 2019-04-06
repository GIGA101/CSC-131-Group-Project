    
import java.io.IOException;
import java.util.TreeMap;

public class Finder {
	
	private static TreeMap<String, Integer> tableID = new TreeMap<String, Integer>();
	private static TreeMap<String, String> tableLocation = new TreeMap<String, String>();
	
	public static void main(String [] args) throws IOException {
		initializeFinder();
	}
	
	public static void initializeFinder() throws IOException {
		tableID = Application.getTableID();
		tableLocation = Application.getTableLocation();
		System.out.println(tableLocation);
		System.out.println(tableID);
	}
	
}