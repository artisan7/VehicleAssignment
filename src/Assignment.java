import java.util.Calendar;
import java.util.Vector;

public class Assignment {
	private static Vector<Assignment> assignments = new Vector<Assignment>();
	
	private Driver driver;
	private Vehicle vehicle;
	private String notes;
	private Calendar dtAssignment;
	
	// CONSTRUCTOR
	public Assignment(Driver d, Vehicle v, String n, Calendar adate) {
		driver = d;
		vehicle = v;
		notes = n;
		dtAssignment = adate;
		
		assignments.add(this);
	}
	
	// GETTERS
	public Driver getDriver() { return driver; }
	public Vehicle getVehicle() { return vehicle; }
	
	// TO ARRAY
	public String[] toArray() {
		return new String[] { driver.toString(), vehicle.toString(), DTFormatter.longDate(dtAssignment), DTFormatter.nonMilitaryTime(dtAssignment), notes };
	}
	
	// GET ALL ASSIGNMENTS
	public static Vector<Assignment> getAll() { return assignments; }
}
