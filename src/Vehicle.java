import java.util.Vector;

public class Vehicle {
	static private Vector<Vehicle> vehicles = new Vector<Vehicle>();
	static private Vector<String> makes = new Vector<String>();
	
	private String plateNumber;
	private String make;
	private String model;
	private int year;
	
	//public Vehicle()
	public Vehicle(String plateNum, String mk, String mdl, int y) {
		plateNumber = plateNum;
		make = mk;
		model = mdl;
		year = y;
		
		if (!makes.contains(mk))
			makes.add(mk);
		vehicles.add(this);
	}
	
	public String[] toArray() {
		return new String[] { plateNumber, make, model, Integer.toString(year) };
	}
	
	static public Vector<String> getMakeList() {
		makes.sort(null);
		return makes;
	}
	
	static void addMake(String m) {
		makes.add(m);
		makes.sort(null);
	}
	
	static Vector<Vehicle> getAll() { return vehicles; }
}
