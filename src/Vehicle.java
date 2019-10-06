import java.util.Vector;

public class Vehicle {
	static private Vector<Vehicle> vehicles = new Vector<Vehicle>();
	static private Vector<String> makes = new Vector<String>();
	
	private String plateNumber;
	private String make;
	private String model;
	private int year;
	
	//public Vehicle()
	public Vehicle(String plateNo, String mk, String mdl, int y) {
		plateNumber = plateNo;
		make = mk;
		model = mdl;
		year = y;
		
		if (!makes.contains(mk))
			makes.add(mk);
		vehicles.add(this);
	}
	
	// TO STRING
	public String toString() {
		return String.format("%s %s %d (%s)", make, model, year, plateNumber);
	}
	
	// TO ARRAY
	public String[] toArray() {
		return new String[] { plateNumber, make, model, Integer.toString(year) };
	}
	
	// GETTERS
	public String getPlateNumber() { return plateNumber; }
	
	// GET LIST OF MAKES
	public static Vector<String> getMakeList() {
		makes.sort(null);
		return makes;
	}
	
	// ADD NEW MAKE
	public static void addMake(String m) {
		makes.add(m);
		makes.sort(null);
	}
	
	// VEHICLE SEARCH
	public static Vehicle search(String plateNo) {
		for (Vehicle v : vehicles)
			if (v.plateNumber.equals(plateNo))
				return v;
		return null;
	}
}
