import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

public class Assignment {
	private static Vector<Assignment> assignments = new Vector<Assignment>();
	
	private Driver driver;
	private Vehicle vehicle;
	private String notes;
	private Calendar assignmentDateTime;
	
	// CONSTRUCTOR
	public Assignment(Driver d, Vehicle v, String n, Calendar adate) {
		driver = d;
		vehicle = v;
		notes = n;
		assignmentDateTime = adate;
		
		assignments.add(this);
	}
	
	// GETTERS
	public Driver getDriver() { return driver; }
	public Vehicle getVehicle() { return vehicle; }
	
	// GET ASSIGNMENT DATE IN DIFFERENT FORMATS
	public String getAssignmentDateStr() {
		String month = assignmentDateTime.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int day = assignmentDateTime.get(Calendar.DAY_OF_MONTH);
		int year = assignmentDateTime.get(Calendar.YEAR);
		
		return String.format("%s %d, %d", month, day, year);
	}
	
	public String getAssignmentDateSlashFormat() {
		int month = assignmentDateTime.get(Calendar.MONTH) + 1;
		int day = assignmentDateTime.get(Calendar.DAY_OF_MONTH);
		int year = assignmentDateTime.get(Calendar.YEAR);
		
		return String.format("%d/%d/%d", month, day, year);
	}
	
	// GET ASSIGNMENT TIME IN DIFFERENT FORMATS
	public String getAssignmentTimeStr() {
		int hour = assignmentDateTime.get(Calendar.HOUR);
		int minute = assignmentDateTime.get(Calendar.MINUTE);
		int second = assignmentDateTime.get(Calendar.SECOND);
		String meridiem = assignmentDateTime.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.ENGLISH);
		
		return String.format("%02d:%02d:%02d %s", hour, minute, second, meridiem);
	}
	
	public String getAssignmentTimeMilitary() {
		int hour = assignmentDateTime.get(Calendar.HOUR_OF_DAY);
		int minute = assignmentDateTime.get(Calendar.MINUTE);
		int second = assignmentDateTime.get(Calendar.SECOND);
		
		return String.format("%d:%d:%d", hour, minute, second);
	}
	
	// TO ARRAY
	public String[] toArray() {
		return new String[] { driver.toString(), vehicle.toString(), getAssignmentDateStr(), getAssignmentTimeStr(), notes };
	}
	
	// GET ALL ASSIGNMENTS
	public static Vector<Assignment> getAll() { return assignments; }
}
